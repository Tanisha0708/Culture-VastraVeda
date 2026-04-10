package vastraveda.features.feature11_care;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Feature11UI extends BaseUI implements Feature {

    private final Feature11Service service = new Feature11Service();
    private JTextArea preview;

    public Feature11UI() {
        super("Dataset Export");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📥 Open Dataset Export", "Download cultural clothing data as JSON or CSV"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        top.setBackground(new Color(245, 235, 215));
        JButton jsonBtn = createStyledButton("Download JSON", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        jsonBtn.addActionListener(e -> saveFile("vastraveda-dataset.json", service.buildJsonDataset()));
        JButton csvBtn = createStyledButton("Download CSV", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        csvBtn.addActionListener(e -> saveFile("vastraveda-dataset.csv", service.buildCsvDataset()));
        JButton metaBtn = createStyledButton("Metadata only (JSON)", COLOR_BORDER, COLOR_TEXT);
        metaBtn.addActionListener(e -> saveFile("vastraveda-metadata.json", service.getMetadataJson()));
        top.add(jsonBtn);
        top.add(csvBtn);
        top.add(metaBtn);
        add(top, BorderLayout.NORTH);

        preview = new JTextArea(service.getMetadataJson() + "\n\n… preview: first records in JSON export are included in full file on download.");
        preview.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        preview.setEditable(false);
        preview.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(new JScrollPane(preview), BorderLayout.CENTER);

        JLabel foot = new JLabel("  Metadata: " + service.getMetadataJson());
        foot.setFont(FONT_SMALL);
        foot.setForeground(COLOR_PRIMARY);
        foot.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        add(foot, BorderLayout.SOUTH);
    }

    private void saveFile(String defaultName, String content) {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(defaultName));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = fc.getSelectedFile();
        try (FileWriter w = new FileWriter(f, StandardCharsets.UTF_8)) {
            w.write(content);
            showInfo("Saved", "Wrote " + f.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Export failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
