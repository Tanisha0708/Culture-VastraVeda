package vastraveda.features.feature13_designers;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.utils.FilterUtils;
import vastraveda.core.models.ClothingItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature13UI extends BaseUI implements Feature {

    private final Feature13Service service = new Feature13Service();
    private final List<Feature13Service.Artisan> allArtisans = service.getAllArtisans();
    private JPanel gridPanel;
    private JComboBox<String> regionCombo;
    private JComboBox<String> craftCombo;

    public Feature13UI() {
        super("Featured Designers");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("✂️ Featured Designers", "Artisans keeping Indian craft alive"), BorderLayout.NORTH);

        // Filter bar
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        filterBar.setBackground(new Color(245, 235, 215));
        filterBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        JLabel regionLabel = new JLabel("Region:");
        regionLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        regionLabel.setForeground(COLOR_TEXT);

        JLabel craftLabel = new JLabel("Craft:");
        craftLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        craftLabel.setForeground(COLOR_TEXT);

        regionCombo = createComboBox(service.getAllRegions(allArtisans).toArray(new String[0]));
        regionCombo.setPreferredSize(new Dimension(180, 30));

        craftCombo = createComboBox(service.getAllCrafts(allArtisans).toArray(new String[0]));
        craftCombo.setPreferredSize(new Dimension(220, 30));

        JButton resetBtn = createStyledButton("Reset", COLOR_BORDER, COLOR_TEXT);

        filterBar.add(regionLabel);
        filterBar.add(regionCombo);
        filterBar.add(craftLabel);
        filterBar.add(craftCombo);
        filterBar.add(resetBtn);

        // Count label
        JLabel countLabel = new JLabel("Showing " + allArtisans.size() + " artisans");
        countLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        countLabel.setForeground(COLOR_PRIMARY);
        countLabel.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 0));

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(COLOR_BG);
        topWrapper.add(filterBar, BorderLayout.NORTH);
        topWrapper.add(countLabel, BorderLayout.SOUTH);

        // Grid
        gridPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        gridPanel.setBackground(COLOR_BG);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(topWrapper, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Listeners
        regionCombo.addActionListener(e -> applyFilter(countLabel));
        craftCombo.addActionListener(e -> applyFilter(countLabel));
        resetBtn.addActionListener(e -> {
            regionCombo.setSelectedIndex(0);
            craftCombo.setSelectedIndex(0);
        });

        renderGrid(allArtisans);
    }

    private void applyFilter(JLabel countLabel) {
        String region = (String) regionCombo.getSelectedItem();
        String craft = (String) craftCombo.getSelectedItem();
        List<Feature13Service.Artisan> filtered = service.filter(allArtisans, region, craft);
        countLabel.setText("Showing " + filtered.size() + " artisan" + (filtered.size() == 1 ? "" : "s"));
        renderGrid(filtered);
    }

    private void renderGrid(List<Feature13Service.Artisan> artisans) {
        gridPanel.removeAll();
        for (Feature13Service.Artisan a : artisans) {
            gridPanel.add(buildArtisanCard(a));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel buildArtisanCard(Feature13Service.Artisan a) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        // Top: avatar + name + title
        JPanel topRow = new JPanel(new BorderLayout(12, 0));
        topRow.setBackground(COLOR_CARD);

        JLabel avatar = new JLabel(a.avatar, SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        avatar.setPreferredSize(new Dimension(56, 56));
        avatar.setOpaque(true);
        avatar.setBackground(new Color(255, 243, 220));
        avatar.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        JPanel nameBlock = new JPanel(new GridLayout(3, 1, 0, 2));
        nameBlock.setBackground(COLOR_CARD);

        JLabel name = new JLabel(a.name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setForeground(COLOR_PRIMARY);

        JLabel title = new JLabel(a.title);
        title.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        title.setForeground(COLOR_TEXT);

        JLabel region = new JLabel("📍 " + a.region);
        region.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        region.setForeground(new Color(120, 80, 40));

        nameBlock.add(name);
        nameBlock.add(title);
        nameBlock.add(region);

        topRow.add(avatar, BorderLayout.WEST);
        topRow.add(nameBlock, BorderLayout.CENTER);

        // Craft badge
        JLabel craftBadge = new JLabel("  " + a.craft + "  ");
        craftBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        craftBadge.setForeground(new Color(139, 69, 19));
        craftBadge.setBackground(new Color(255, 235, 180));
        craftBadge.setOpaque(true);
        craftBadge.setBorder(BorderFactory.createLineBorder(new Color(200, 160, 80)));

        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        badgeRow.setBackground(COLOR_CARD);
        badgeRow.add(craftBadge);

        // Bio
        JTextArea bio = new JTextArea(a.bio);
        bio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bio.setForeground(COLOR_TEXT);
        bio.setBackground(COLOR_CARD);
        bio.setEditable(false);
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        bio.setBorder(null);

        // Quote
        JLabel quote = new JLabel("<html><i>\"" + a.quote + "\"</i></html>");
        quote.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        quote.setForeground(new Color(139, 90, 43));
        quote.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, COLOR_PRIMARY),
            BorderFactory.createEmptyBorder(4, 8, 4, 4)));

        // View garments button
        JButton viewBtn = createStyledButton("View Garments from " + 
            (a.region.contains(",") ? a.region.split(",")[1].trim() : a.region),
            COLOR_SECONDARY, COLOR_TEXT);
        viewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        viewBtn.addActionListener(e -> showGarments(a));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        btnRow.setBackground(COLOR_CARD);
        btnRow.add(viewBtn);

        // Assemble
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(COLOR_CARD);
        center.add(badgeRow);
        center.add(Box.createVerticalStrut(8));
        center.add(bio);
        center.add(Box.createVerticalStrut(8));
        center.add(quote);
        center.add(btnRow);

        card.add(topRow, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);

        return card;
    }

    private void showGarments(Feature13Service.Artisan a) {
        String state = a.region.contains(",") ? a.region.split(",")[1].trim() : a.region;
        List<ClothingItem> items = FilterUtils.filterByRegion(state);

        if (items.isEmpty()) {
            showInfo("Garments", "No garments found for " + state + " in the current dataset.");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (ClothingItem item : items) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBackground(COLOR_CARD);
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            JLabel icon = new JLabel(item.getImageIcon());
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

            JPanel text = new JPanel(new BorderLayout());
            text.setBackground(COLOR_CARD);
            JLabel nm = new JLabel(item.getName());
            nm.setFont(new Font("Segoe UI", Font.BOLD, 13));
            nm.setForeground(COLOR_TEXT);
            JLabel fab = new JLabel(item.getFabricType() + " · " + item.getOccasion());
            fab.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            fab.setForeground(COLOR_PRIMARY);
            text.add(nm, BorderLayout.NORTH);
            text.add(fab, BorderLayout.SOUTH);

            row.add(icon, BorderLayout.WEST);
            row.add(text, BorderLayout.CENTER);
            panel.add(row);
            panel.add(Box.createVerticalStrut(6));
        }

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(420, 300));
        JOptionPane.showMessageDialog(this, scroll,
            "Garments from " + state, JOptionPane.PLAIN_MESSAGE);
    }
}