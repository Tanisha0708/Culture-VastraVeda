package vastraveda.features.feature12_glossary;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  FEATURE 12 — Glossary                                              ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)                         ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📋 WHAT TO BUILD:                                              ║
 * ║  • A-Z alphabet bar (row of JButtons) + search JTextField at the top.║
 * ║  • JSplitPane: left=JList of sorted terms, right=definition + example panel.║
 * ║  • At least 25 terms hardcoded in Feature12Service using TreeMap (auto-sorted).║
 * ║  • Letter buttons filter list; search field does live filtering.║
 * ║  • See README.md in this folder for the full 25-term glossary list.║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📁 ONLY MODIFY THESE FILES IN THIS FOLDER:                     ║
 * ║     Feature12UI.java       ← Your Swing UI code here              ║
 * ║     Feature12Service.java  ← Your data/logic here                ║
 * ║     README.md               ← Full spec + layout diagram        ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class Feature12UI extends BaseUI implements Feature {

    private final Feature12Service service = new Feature12Service();

    public Feature12UI() {
        super("Glossary");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📚  Glossary", "A dictionary of terms related to Indian textiles and clothing."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel placeholder = new JLabel("<html><center>" +
            "<span style='font-size:36px'>📚</span><br><br>" +
            "<b style='font-size:16px'>Glossary</b><br><br>" +
            "<span style='color:gray'>A dictionary of terms related to Indian textiles and clothing.</span><br><br>" +
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
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Glossary", JOptionPane.PLAIN_MESSAGE);
    }
}
