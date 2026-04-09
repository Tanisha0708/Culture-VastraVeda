package vastraveda.features.feature5_occasions;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature5UI extends BaseUI implements Feature {

    private final Feature5Service service = new Feature5Service();

    public Feature5UI() {
        super("Occasion Guide");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🎊 Occasion Guide", "Dress right for every event"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(COLOR_BG);
        tabs.setForeground(COLOR_TEXT);

        List<String> occasions = service.getAllOccasions();
        for (String occasion : occasions) {
            tabs.addTab(occasion, buildOccasionPanel(occasion));
        }

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildOccasionPanel(String occasion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);

        // Description card at top
        JPanel descCard = createCard();
        descCard.setLayout(new BorderLayout());
        descCard.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        descCard.setBackground(new Color(255, 248, 235));

        JLabel descLabel = new JLabel("<html><body style='width:500px'>"
                + service.getOccasionDescription(occasion) + "</body></html>");
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        descLabel.setForeground(COLOR_TEXT);
        descCard.add(descLabel, BorderLayout.CENTER);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(COLOR_BG);
        topWrapper.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
        topWrapper.add(descCard, BorderLayout.CENTER);

        // Garment cards
        List<ClothingItem> items = service.getByOccasion(occasion);
        JPanel garmentGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        garmentGrid.setBackground(COLOR_BG);
        garmentGrid.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));

        if (items.isEmpty()) {
            JLabel noItems = new JLabel("No garments found for this occasion.", SwingConstants.CENTER);
            noItems.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            noItems.setForeground(COLOR_TEXT);
            garmentGrid.add(noItems);
        } else {
            for (ClothingItem item : items) {
                garmentGrid.add(buildItemCard(item));
            }
        }

        JScrollPane scroll = new JScrollPane(garmentGrid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(topWrapper, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildItemCard(ClothingItem item) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(8, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        // Icon + name row
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        topRow.setBackground(COLOR_CARD);
        JLabel icon = new JLabel(item.getImageIcon());
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));
        name.setForeground(COLOR_TEXT);
        topRow.add(icon);
        topRow.add(name);

        // Details
        JLabel details = new JLabel(item.getRegion() + " · " + item.getFabricType() + " · " + item.getGender());
        details.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        details.setForeground(COLOR_PRIMARY);

        card.add(topRow, BorderLayout.NORTH);
        card.add(details, BorderLayout.SOUTH);

        return card;
    }
}