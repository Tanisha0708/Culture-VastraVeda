package vastraveda.features.feature18_outfitsuggest;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature18UI extends BaseUI implements Feature {

    private final Feature18Service service = new Feature18Service();
    private JComboBox<String> itemCombo;
    private JPanel suggestionsPanel;

    public Feature18UI() {
        super("AI Outfit Suggestions");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("✨ AI-Powered Outfit Suggestions", "Similar garments by fabric, region, occasion, gender"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(new Color(245, 235, 215));
        top.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        top.add(new JLabel("Base outfit:"));
        List<ClothingItem> items = service.getAllItems();
        String[] names = items.stream().map(ClothingItem::getName).toArray(String[]::new);
        itemCombo = createComboBox(names);
        itemCombo.setPreferredSize(new Dimension(260, 28));
        top.add(itemCombo);
        JButton go = createStyledButton("Suggest similar", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        go.addActionListener(e -> runSuggest());
        top.add(go);

        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setBackground(COLOR_BG);
        suggestionsPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));

        JScrollPane scroll = new JScrollPane(suggestionsPanel);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.setBorder(BorderFactory.createTitledBorder("Recommendations"));

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        if (names.length > 0) {
            runSuggest();
        }
    }

    private void runSuggest() {
        String name = (String) itemCombo.getSelectedItem();
        ClothingItem base = null;
        for (ClothingItem i : service.getAllItems()) {
            if (i.getName().equals(name)) {
                base = i;
                break;
            }
        }
        suggestionsPanel.removeAll();
        if (base == null) {
            suggestionsPanel.add(new JLabel("No item selected."));
            suggestionsPanel.revalidate();
            suggestionsPanel.repaint();
            return;
        }

        JLabel header = new JLabel("Because you picked: " + base.getName());
        header.setFont(FONT_LABEL);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        suggestionsPanel.add(header);
        suggestionsPanel.add(Box.createVerticalStrut(8));

        List<Feature18Service.RankedItem> ranked = service.suggestSimilar(base, 8);
        if (ranked.isEmpty()) {
            suggestionsPanel.add(new JLabel("No strong matches — try another garment."));
        } else {
            for (Feature18Service.RankedItem r : ranked) {
                suggestionsPanel.add(buildCard(r));
                suggestionsPanel.add(Box.createVerticalStrut(8));
            }
        }
        suggestionsPanel.revalidate();
        suggestionsPanel.repaint();
    }

    private JPanel buildCard(Feature18Service.RankedItem r) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(8, 4));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 88));

        JLabel left = new JLabel(r.item.getImageIcon(), SwingConstants.CENTER);
        left.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JPanel text = new JPanel(new GridLayout(3, 1, 0, 2));
        text.setOpaque(false);
        JLabel title = new JLabel(r.item.getName() + "  ·  score " + r.score);
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JLabel meta = new JLabel(r.item.getRegion() + " · " + r.item.getFabricType() + " · " + r.item.getOccasion());
        meta.setFont(FONT_SMALL);
        meta.setForeground(COLOR_PRIMARY);
        JLabel why = new JLabel("Matched on: " + r.reason);
        why.setFont(FONT_SMALL);
        why.setForeground(new Color(80, 60, 40));
        text.add(title);
        text.add(meta);
        text.add(why);

        card.add(left, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }
}
