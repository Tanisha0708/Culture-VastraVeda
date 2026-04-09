package vastraveda.features.feature11_care;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Feature11UI extends BaseUI implements Feature {

    private final Feature11Service service = new Feature11Service();
    private JPanel garmentListPanel;
    private JPanel carePanel;

    public Feature11UI() {
        super("Care & Maintenance");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🧺 Care & Maintenance", "Preserve your traditional wear"), BorderLayout.NORTH);

        // Top: fabric selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        topPanel.setBackground(COLOR_BG);

        JLabel selectLabel = new JLabel("Select Fabric:");
        selectLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        selectLabel.setForeground(COLOR_TEXT);

        List<String> fabrics = service.getAllFabrics();
        String[] fabricArray = new String[fabrics.size() + 1];
        fabricArray[0] = "All Fabrics";
        for (int i = 0; i < fabrics.size(); i++) fabricArray[i + 1] = fabrics.get(i);

        JComboBox<String> fabricCombo = createComboBox(fabricArray);
        fabricCombo.setPreferredSize(new Dimension(200, 30));
        topPanel.add(selectLabel);
        topPanel.add(fabricCombo);

        // Split pane
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(300);
        split.setBackground(COLOR_BG);

        // Left: garment list
        garmentListPanel = new JPanel();
        garmentListPanel.setLayout(new BoxLayout(garmentListPanel, BoxLayout.Y_AXIS));
        garmentListPanel.setBackground(COLOR_BG);
        garmentListPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane leftScroll = new JScrollPane(garmentListPanel);
        leftScroll.getViewport().setBackground(COLOR_BG);
        leftScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            "Garments", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), COLOR_PRIMARY));

        // Right: care tips
        carePanel = new JPanel();
        carePanel.setLayout(new BoxLayout(carePanel, BoxLayout.Y_AXIS));
        carePanel.setBackground(COLOR_BG);
        carePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane rightScroll = new JScrollPane(carePanel);
        rightScroll.getViewport().setBackground(COLOR_BG);
        rightScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            "Care Instructions", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), COLOR_PRIMARY));
        rightScroll.getVerticalScrollBar().setUnitIncrement(16);

        split.setLeftComponent(leftScroll);
        split.setRightComponent(rightScroll);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(COLOR_BG);
        centerWrapper.add(topPanel, BorderLayout.NORTH);
        centerWrapper.add(split, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        fabricCombo.addActionListener(e -> updateUI((String) fabricCombo.getSelectedItem()));
        updateUI("All Fabrics");
    }

    private void updateUI(String fabric) {
        // Update garment list
        garmentListPanel.removeAll();
        List<ClothingItem> items = service.getItemsByFabric(fabric);
        for (ClothingItem item : items) {
            JPanel row = new JPanel(new BorderLayout(8, 0));
            row.setBackground(COLOR_CARD);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

            JLabel icon = new JLabel(item.getImageIcon());
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

            JPanel text = new JPanel(new BorderLayout());
            text.setBackground(COLOR_CARD);
            JLabel name = new JLabel(item.getName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 12));
            name.setForeground(COLOR_TEXT);
            JLabel fab = new JLabel(item.getFabricType());
            fab.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            fab.setForeground(COLOR_PRIMARY);
            text.add(name, BorderLayout.NORTH);
            text.add(fab, BorderLayout.SOUTH);

            row.add(icon, BorderLayout.WEST);
            row.add(text, BorderLayout.CENTER);
            garmentListPanel.add(row);
            garmentListPanel.add(Box.createVerticalStrut(4));
        }
        garmentListPanel.revalidate();
        garmentListPanel.repaint();

        // Update care tips
        carePanel.removeAll();
        Map<String, String[]> allTips = service.getCareTips();

        if ("All Fabrics".equals(fabric)) {
            allTips.forEach((fab, tips) -> {
                carePanel.add(buildFabricSection(fab, tips));
                carePanel.add(Box.createVerticalStrut(12));
            });
        } else {
            String[] tips = allTips.get(fabric);
            if (tips != null) {
                carePanel.add(buildFabricSection(fabric, tips));
            } else {
                JLabel def = new JLabel("<html>" + service.getDefaultTip() + "</html>");
                def.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                def.setForeground(COLOR_TEXT);
                carePanel.add(def);
            }
        }
        carePanel.add(Box.createVerticalGlue());
        carePanel.revalidate();
        carePanel.repaint();
    }

    private JPanel buildFabricSection(String fabric, String[] tips) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(new Color(255, 248, 235));
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel title = new JLabel("✦ " + fabric);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(COLOR_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(title);
        section.add(Box.createVerticalStrut(8));

        for (String tip : tips) {
            JLabel tipLabel = new JLabel("  • " + tip);
            tipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tipLabel.setForeground(COLOR_TEXT);
            tipLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            section.add(tipLabel);
            section.add(Box.createVerticalStrut(3));
        }
        return section;
    }
}