package vastraveda.features.feature4_calendar;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  FEATURE 4 — Festival Calendar                                     ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)                         ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📋 WHAT TO BUILD:                                              ║
 * ║  • Use JTabbedPane for 4 seasons (Spring/Summer/Autumn/Winter).║
 * ║  • Left tab → festival cards with name, month, region, short tip.║
 * ║  • Use FilterUtils.filterByOccasion("Festival") to pull ClothingItems.║
 * ║  • Hardcode festival data in Feature4Service.java.           ║
 * ║  • See README.md in this folder for full festival list + layout.║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📁 ONLY MODIFY THESE FILES IN THIS FOLDER:                     ║
 * ║     Feature4UI.java       ← Your Swing UI code here              ║
 * ║     Feature4Service.java  ← Your data/logic here                ║
 * ║     README.md               ← Full spec + layout diagram        ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class Feature4UI extends BaseUI implements Feature {

    private final Feature4Service service = new Feature4Service();

    public Feature4UI() {
        super("Festival Calendar");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🗓  Festival Calendar", "Discover clothing worn during Indian festivals across the year."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel placeholder = new JLabel("<html><center>" +
            "<span style='font-size:36px'>🗓</span><br><br>" +
            "<b style='font-size:16px'>Festival Calendar</b><br><br>" +
            "<span style='color:gray'>Discover clothing worn during Indian festivals across the year.</span><br><br>" +
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
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Festival Calendar", JOptionPane.PLAIN_MESSAGE);
    }
}
