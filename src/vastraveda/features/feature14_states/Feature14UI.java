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
        Feature14Service.StateProfile profile = service.getStateProfile(state);
        if (profile == null) {
            detailPanel.add(new JLabel("Profile unavailable for " + state));
            detailPanel.revalidate();
            detailPanel.repaint();
            return;
        }

        JLabel heading = new JLabel(service.getStateEmoji(state) + "  " + state);
        heading.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        heading.setForeground(COLOR_PRIMARY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(heading);
        detailPanel.add(Box.createVerticalStrut(12));

        JLabel region = new JLabel("📍 Region: " + profile.getRegion());
        region.setFont(new Font("Segoe UI", Font.BOLD, 13));
        region.setForeground(COLOR_TEXT);
        region.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(region);
        detailPanel.add(Box.createVerticalStrut(10));

        JLabel coloursHeading = sectionHeading("Signature Colours");
        detailPanel.add(coloursHeading);
        detailPanel.add(Box.createVerticalStrut(6));
        detailPanel.add(buildColourSwatches(profile.getSignatureColours()));
        detailPanel.add(Box.createVerticalStrut(10));

        JLabel giHeading = sectionHeading("GI-Tagged Heritage Items");
        detailPanel.add(giHeading);
        detailPanel.add(Box.createVerticalStrut(6));
        detailPanel.add(buildGiItems(profile.getGiItems()));
        detailPanel.add(Box.createVerticalStrut(10));

        JLabel garmentHeading = sectionHeading("Signature Garments");
        detailPanel.add(garmentHeading);
        detailPanel.add(Box.createVerticalStrut(6));
        detailPanel.add(buildChipRow(profile.getSignatureGarments(), COLOR_PRIMARY));
        detailPanel.add(Box.createVerticalStrut(10));

        JLabel fabricHeading = sectionHeading("Dominant Fabrics");
        detailPanel.add(fabricHeading);
        detailPanel.add(Box.createVerticalStrut(6));
        detailPanel.add(buildChipRow(profile.getDominantFabrics(), new Color(120, 80, 40)));
        detailPanel.add(Box.createVerticalStrut(10));

        JLabel weavingHeading = sectionHeading("Famous Weaving Centres");
        detailPanel.add(weavingHeading);
        detailPanel.add(Box.createVerticalStrut(6));
        detailPanel.add(buildChipRow(profile.getWeavingCentres(), new Color(70, 110, 90)));
        detailPanel.add(Box.createVerticalStrut(14));

        JPanel factCard = new JPanel(new BorderLayout());
        factCard.setBackground(new Color(255, 248, 235));
        factCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        factCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        factCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel factText = new JLabel("<html><body style='width:400px'>"
                + profile.getAbout() + "</body></html>");
        factText.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        factText.setForeground(COLOR_TEXT);
        factCard.add(factText, BorderLayout.CENTER);
        detailPanel.add(factCard);
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

    private JLabel sectionHeading(String text) {
        JLabel heading = new JLabel(text);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        heading.setForeground(COLOR_TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        return heading;
    }

    private JPanel buildColourSwatches(List<String> colourHex) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setBackground(COLOR_BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        for (String hex : colourHex) {
            JPanel swatch = new JPanel();
            swatch.setPreferredSize(new Dimension(24, 24));
            swatch.setBackground(parseColor(hex));
            swatch.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 20)));
            swatch.setToolTipText(hex);
            panel.add(swatch);
        }
        return panel;
    }

    private JPanel buildGiItems(List<String> giItems) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setBackground(COLOR_BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        for (String item : giItems) {
            JLabel badge = new JLabel("🏷 GI Tagged: " + item);
            badge.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
            badge.setForeground(new Color(120, 80, 20));
            badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 160, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            badge.setBackground(new Color(255, 250, 225));
            badge.setOpaque(true);
            panel.add(badge);
        }
        return panel;
    }

    private JPanel buildChipRow(List<String> values, Color borderColor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setBackground(COLOR_BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        for (String value : values) {
            JLabel chip = new JLabel(value);
            chip.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            chip.setOpaque(true);
            chip.setBackground(COLOR_CARD);
            chip.setForeground(COLOR_TEXT);
            panel.add(chip);
        }
        return panel;
    }

    private Color parseColor(String hex) {
        try {
            return Color.decode(hex);
        } catch (NumberFormatException ex) {
            return Color.LIGHT_GRAY;
        }
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