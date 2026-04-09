package vastraveda.features.feature14_states;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Feature14UI extends BaseUI implements Feature {

    private final Feature14Service service = new Feature14Service();
    private JPanel detailPanel;

    public Feature14UI() {
        super("State Profiles");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🏛 State Profiles", "Clothing of every Indian state"), BorderLayout.NORTH);

        // Left: state list
        List<String> states = service.getAllStates();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String s : states) listModel.addElement(s);

        JList<String> stateList = new JList<>(listModel);
        stateList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        stateList.setForeground(COLOR_TEXT);
        stateList.setBackground(COLOR_BG);
        stateList.setSelectionBackground(COLOR_PRIMARY);
        stateList.setSelectionForeground(COLOR_TEXT_LIGHT);
        stateList.setFixedCellHeight(40);
        stateList.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        // Custom cell renderer to show emoji + state name
        stateList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String state = (String) value;
                setText(service.getStateEmoji(state) + "  " + state);
                setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                if (isSelected) {
                    setBackground(COLOR_PRIMARY);
                    setForeground(COLOR_TEXT_LIGHT);
                } else {
                    setBackground(COLOR_BG);
                    setForeground(COLOR_TEXT);
                }
                return this;
            }
        });

        JScrollPane leftScroll = new JScrollPane(stateList);
        leftScroll.setPreferredSize(new Dimension(220, 0));
        leftScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                "States / Regions",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), COLOR_PRIMARY));

        // Right: detail panel
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(COLOR_BG);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel placeholder = new JLabel("← Select a state to see its clothing profile");
        placeholder.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        placeholder.setForeground(COLOR_TEXT);
        placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(Box.createVerticalGlue());
        detailPanel.add(placeholder);
        detailPanel.add(Box.createVerticalGlue());

        JScrollPane rightScroll = new JScrollPane(detailPanel);
        rightScroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        rightScroll.getViewport().setBackground(COLOR_BG);
        rightScroll.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightScroll);
        split.setDividerLocation(240);
        split.setBackground(COLOR_BG);
        add(split, BorderLayout.CENTER);

        stateList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && stateList.getSelectedValue() != null) {
                showStateProfile(stateList.getSelectedValue());
            }
        });

        if (!states.isEmpty()) stateList.setSelectedIndex(0);
    }

    private void showStateProfile(String state) {
        detailPanel.removeAll();

        // State heading
        JLabel heading = new JLabel(service.getStateEmoji(state) + "  " + state);
        heading.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        heading.setForeground(COLOR_PRIMARY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(heading);
        detailPanel.add(Box.createVerticalStrut(12));

        // Fact card
        JPanel factCard = new JPanel(new BorderLayout());
        factCard.setBackground(new Color(255, 248, 235));
        factCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        factCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        factCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel factText = new JLabel("<html><body style='width:400px'>"
                + service.getStateFact(state) + "</body></html>");
        factText.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        factText.setForeground(COLOR_TEXT);
        factCard.add(factText, BorderLayout.CENTER);
        detailPanel.add(factCard);
        detailPanel.add(Box.createVerticalStrut(16));

        // Garments heading
        JLabel garmentsHeading = new JLabel("Traditional Garments");
        garmentsHeading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        garmentsHeading.setForeground(COLOR_TEXT);
        garmentsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(garmentsHeading);
        detailPanel.add(Box.createVerticalStrut(8));

        List<ClothingItem> items = service.getGarmentsByState(state);

        if (items.isEmpty()) {
            JPanel emptyCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
            emptyCard.setBackground(COLOR_CARD);
            emptyCard.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
            emptyCard.setAlignmentX(Component.LEFT_ALIGNMENT);
            emptyCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            JLabel none = new JLabel("No garments catalogued for this region yet.");
            none.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            none.setForeground(COLOR_TEXT);
            emptyCard.add(none);
            detailPanel.add(emptyCard);
        } else {
            for (ClothingItem item : items) {
                detailPanel.add(buildGarmentCard(item));
                detailPanel.add(Box.createVerticalStrut(8));
            }
        }

        detailPanel.add(Box.createVerticalGlue());
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private JPanel buildGarmentCard(ClothingItem item) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Icon
        JLabel icon = new JLabel(item.getImageIcon());
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setPreferredSize(new Dimension(44, 44));

        // Details
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 3));
        textPanel.setBackground(COLOR_CARD);

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setForeground(COLOR_TEXT);

        JLabel meta = new JLabel(item.getFabricType() + "  ·  " + item.getOccasion() + "  ·  " + item.getGender());
        meta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        meta.setForeground(COLOR_PRIMARY);

        JLabel desc = new JLabel("<html><body style='width:350px'>" + item.getDescription() + "</body></html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc.setForeground(new Color(80, 60, 40));

        textPanel.add(name);
        textPanel.add(meta);
        textPanel.add(desc);

        // Care badge
        JLabel care = new JLabel("🧺 " + item.getCareInstructions());
        care.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
        care.setForeground(new Color(100, 70, 30));

        card.add(icon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(care, BorderLayout.SOUTH);

        return card;
    }
}