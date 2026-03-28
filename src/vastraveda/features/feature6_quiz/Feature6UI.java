package vastraveda.features.feature6_quiz;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║  FEATURE 6 — Clothing Quiz                                         ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)                         ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📋 WHAT TO BUILD:                                              ║
 * ║  • 8 multiple-choice questions, 4 options each — hardcode in Feature6Service.║
 * ║  • Visual feedback: correct answer turns green, wrong turns red after click.║
 * ║  • Track and display score. Show result screen after last question.║
 * ║  • Restart button lets user replay. Disable options after answering.║
 * ║  • See README.md in this folder for all 8 questions and answer keys.║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  📁 ONLY MODIFY THESE FILES IN THIS FOLDER:                     ║
 * ║     Feature6UI.java       ← Your Swing UI code here              ║
 * ║     Feature6Service.java  ← Your data/logic here                ║
 * ║     README.md               ← Full spec + layout diagram        ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class Feature6UI extends BaseUI implements Feature {

    private final Feature6Service service = new Feature6Service();

    public Feature6UI() {
        super("Clothing Quiz");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("❓  Clothing Quiz", "Test your knowledge about Indian traditional clothing."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel placeholder = new JLabel("<html><center>" +
            "<span style='font-size:36px'>❓</span><br><br>" +
            "<b style='font-size:16px'>Clothing Quiz</b><br><br>" +
            "<span style='color:gray'>Test your knowledge about Indian traditional clothing.</span><br><br>" +
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
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Clothing Quiz", JOptionPane.PLAIN_MESSAGE);
    }
}
