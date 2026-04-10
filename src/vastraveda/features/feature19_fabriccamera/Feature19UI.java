package vastraveda.features.feature19_fabriccamera;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Feature19UI extends BaseUI implements Feature {

    private final Feature19Service service = new Feature19Service();
    private JLabel preview;
    private JLabel resultLabel;
    private BufferedImage lastImage;

    public Feature19UI() {
        super("Fabric Identifier");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📷 Camera-Based Fabric Identifier", "Capture or load an image — mock classification (no real ML)"), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBackground(COLOR_BG);
        main.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        buttons.setOpaque(false);
        JButton load = createStyledButton("Load image…", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        load.addActionListener(e -> loadImage());
        buttons.add(load);
        JButton mock = createStyledButton("Simulate capture (preset)", COLOR_BORDER, COLOR_TEXT);
        mock.addActionListener(e -> showPresetMenu());
        buttons.add(mock);

        preview = new JLabel("No image", SwingConstants.CENTER);
        preview.setPreferredSize(new Dimension(400, 260));
        preview.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        preview.setBackground(COLOR_CARD);
        preview.setOpaque(true);

        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(FONT_BODY);
        resultLabel.setForeground(COLOR_TEXT);

        JPanel south = new JPanel(new BorderLayout(8, 8));
        south.setOpaque(false);
        south.add(resultLabel, BorderLayout.CENTER);
        JTextArea note = createTextArea(
            "Note: True camera access depends on OS/drivers. This feature uses file load + mock rules. "
                + "For production, integrate OpenCV or a cloud vision API.");
        note.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        south.add(note, BorderLayout.SOUTH);

        main.add(buttons, BorderLayout.NORTH);
        main.add(preview, BorderLayout.CENTER);
        main.add(south, BorderLayout.SOUTH);
        add(main, BorderLayout.CENTER);
    }

    private void loadImage() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif", "bmp"));
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = fc.getSelectedFile();
        try {
            lastImage = ImageIO.read(f);
            if (lastImage == null) {
                resultLabel.setText("Could not read image.");
                return;
            }
            Image scaled = lastImage.getScaledInstance(380, 240, Image.SCALE_SMOOTH);
            preview.setIcon(new ImageIcon(scaled));
            preview.setText(null);
            String fabric = service.classifyMock(f.getName(), lastImage);
            resultLabel.setText("Likely fabric (mock): " + fabric);
        } catch (Exception ex) {
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }

    private void showPresetMenu() {
        String[] opts = {"Cotton", "Silk", "Wool"};
        String s = (String) JOptionPane.showInputDialog(this, "Pick a mock fabric preset:", "Simulate",
            JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
        if (s != null) {
            resultLabel.setText("Likely fabric (mock): " + service.classifyPreset(s));
            preview.setIcon(null);
            preview.setText("Simulated capture — no file");
        }
    }
}
