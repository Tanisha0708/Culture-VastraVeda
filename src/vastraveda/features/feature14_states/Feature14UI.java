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
        JList<String> stateList = new JList<>(states.toArray(new String[0]));
        stateList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        stateList.setForeground(COLOR_TEXT);
        stateList.setBackground(COLOR_BG);
        stateList.setSelectionBackground(COLOR_PRIMARY);
        stateList.setSelectionForeground(COLOR_TEXT_LIGHT);
        stateList.setFixedCellHeight(36);
        stateList.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JScrollPane leftScroll = new JScrollPane(stateList);
        leftScroll.setPreferredSize(new Dimension(200, 0));
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
        split.setDividerLocation(220);
        split.setBackground(COLOR_BG);
        add(split, BorderLayout.CENTER);

        // Selection listener
        stateList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && stateList.getSelectedValue() != null) {
                showStateProfile(stateList.getSelectedValue());
            }
        });

        // Auto-select first state
        if (!states.isEmpty()) {
            stateList.setSelectedIndex(0);
        }
    }

    private void showStateProfile(String state) {
        detailPanel.removeAll();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        // State heading
        JLabel heading = new JLabel("🏛 " + state);
        heading.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        heading.setForeground(COLOR_PRIMARY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(heading);
        detailPanel.add(Box.createVerticalStrut(10));

        // Fact card
        JPanel factCard = createCard();
        factCard.setLayout(new BorderLayout());
        factCard.setBackground(new Color(255, 248, 235));
        factCard.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        factCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        factCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea factText = createTextArea(service.getStateFact(state));
        factText.setLineWrap(true);
        factText.setWrapStyleWord(true);
        factText.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        factText.setBackground(new Color(255, 248, 235));
        factCard.add(factText, BorderLayout.CENTER);
        detailPanel.add(factCard);
        detailPanel.add(Box.createVerticalStrut(16));

        // Garments heading
        JLabel garmentsHeading = new JLabel("Traditional Garments");
        garmentsHeading.setFont(new Font("Segoe UI", Font.BOLD, 14));
        garmentsHeading.setForeground(COLOR_TEXT);
        garmentsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(garmentsHeading);
        detailPanel.add(Box.createVerticalStrut(8));

        List<ClothingItem> items = service.getGarmentsByState(state);

        if (items.isEmpty()) {
            JLabel none = new JLabel("No garments catalogued for this region yet.");
            none.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            none.setForeground(COLOR_TEXT);
            none.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailPanel.add(none);
        } else {
            for (ClothingItem item : items) {
                detailPanel.add(buildGarmentRow(item));
                detailPanel.add(Box.createVerticalStrut(8));
            }
        }

        detailPanel.add(Box.createVerticalGlue());
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private JPanel buildGarmentRow(ClothingItem item) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: icon
        JLabel icon = new JLabel(item.getImageIcon());
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icon.setPreferredSize(new Dimension(40, 40));

        // Right: details
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 2));
        textPanel.setBackground(COLOR_CARD);

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));
        name.setForeground(COLOR_TEXT);

        JLabel meta = new JLabel(item.getFabricType() + " · " + item.getOccasion() + " · " + item.getGender());
        meta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        meta.setForeground(COLOR_PRIMARY);

        JLabel desc = new JLabel("<html>" + item.getDescription() + "</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc.setForeground(new Color(80, 60, 40));

        textPanel.add(name);
        textPanel.add(meta);
        textPanel.add(desc);

        card.add(icon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }
}