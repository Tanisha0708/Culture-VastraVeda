package vastraveda.features.feature8_compare;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  FEATURE 8 — Compare Outfits                                       ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)                         ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📋 WHAT TO BUILD:                                              ║
 * ║  • Two JComboBox dropdowns populated from DataStore.getAllItems().║
 * ║  • Compare button renders a table: attribute | item A value | item B value.║
 * ║  • Matching values highlighted green, differing values highlighted amber.║
 * ║  • Build rows in Feature8Service.buildComparisonRows(itemA, itemB).║
 * ║  • See README.md in this folder for attribute list and layout diagram.║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📁 ONLY MODIFY THESE FILES IN THIS FOLDER:                     ║
 * ║     Feature8UI.java       ← Your Swing UI code here              ║
 * ║     Feature8Service.java  ← Your data/logic here                ║
 * ║     README.md               ← Full spec + layout diagram        ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class Feature8UI extends BaseUI implements Feature {

    private final Feature8Service service = new Feature8Service();

    public Feature8UI() {
        super("Compare Outfits");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("⚖️  Compare Outfits", "Side-by-side comparison of traditional garments."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel placeholder = new JLabel("<html><center>" +
            "<span style='font-size:36px'>⚖️</span><br><br>" +
            "<b style='font-size:16px'>Compare Outfits</b><br><br>" +
            "<span style='color:gray'>Side-by-side comparison of traditional garments.</span><br><br>" +
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
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Compare Outfits", JOptionPane.PLAIN_MESSAGE);
    }
}
