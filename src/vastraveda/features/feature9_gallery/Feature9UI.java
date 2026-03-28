package vastraveda.features.feature9_gallery;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  FEATURE 9 — Visual Gallery                                        ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)                         ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📋 WHAT TO BUILD:                                              ║
 * ║  • GridLayout(0,3) of item cards — each card: emoji(42pt), name, region, tags.║
 * ║  • Toggle button to switch between Grid view and List view.  ║
 * ║  • Live search via DocumentListener and gender filter dropdown.║
 * ║  • Click a card to open detail dialog. Hover darkens card background.║
 * ║  • See README.md in this folder for card design and layout.  ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📁 ONLY MODIFY THESE FILES IN THIS FOLDER:                     ║
 * ║     Feature9UI.java       ← Your Swing UI code here              ║
 * ║     Feature9Service.java  ← Your data/logic here                ║
 * ║     README.md               ← Full spec + layout diagram        ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class Feature9UI extends BaseUI implements Feature {

    private final Feature9Service service = new Feature9Service();

    public Feature9UI() {
        super("Visual Gallery");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🖼  Visual Gallery", "Browse a visual gallery of Indian clothing styles."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel placeholder = new JLabel("<html><center>" +
            "<span style='font-size:36px'>🖼</span><br><br>" +
            "<b style='font-size:16px'>Visual Gallery</b><br><br>" +
            "<span style='color:gray'>Browse a visual gallery of Indian clothing styles.</span><br><br>" +
            "<span style='color:#8B4513'>" + DataStore.getAllItems().size() + " items in the data store</span>" +
            "</center></html>", JLabel.CENTER);
        placeholder.setFont(FONT_BODY);

        JButton exploreBtn = createStyledButton("Explore " + DataStore.getAllItems().size() + " Items", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        exploreBtn.addActionListener(e -> showItemList());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(exploreBtn);

        content.add(placeholder, BorderLayout.CENTER);
        content.add(btnRow, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);
    }

    private void showItemList() {
        List<ClothingItem> items = DataStore.getAllItems();
        StringBuilder sb = new StringBuilder("All Clothing Items:\n\n");
        for (ClothingItem item : items) {
            sb.append(item.getImageIcon()).append(" ").append(item.getName())
              .append(" — ").append(item.getRegion()).append("\n");
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setFont(FONT_BODY);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Visual Gallery", JOptionPane.PLAIN_MESSAGE);
    }
}
