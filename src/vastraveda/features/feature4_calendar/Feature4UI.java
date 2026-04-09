package vastraveda.features.feature4_calendar;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.utils.FilterUtils;
import vastraveda.core.models.ClothingItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Feature 4 — Festival calendar: seasons (tabs), festival cards, and DataStore clothing.
 */
public class Feature4UI extends BaseUI implements Feature {

    private static final Color SPRING_ACCENT = new Color(34, 139, 34);
    private static final Color SUMMER_ACCENT = new Color(230, 126, 34);
    private static final Color AUTUMN_ACCENT = new Color(192, 57, 43);
    private static final Color WINTER_ACCENT = new Color(41, 128, 185);

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
        add(createHeader("🗓  Festival Calendar",
                "Discover clothing worn during Indian festivals across the year."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(12, 20, 16, 20));

        JTabbedPane seasons = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        seasons.setFont(FONT_BODY);
        seasons.setBackground(COLOR_BG);

        Map<String, Color> seasonColors = new LinkedHashMap<>();
        seasonColors.put(Feature4Service.SPRING, SPRING_ACCENT);
        seasonColors.put(Feature4Service.SUMMER, SUMMER_ACCENT);
        seasonColors.put(Feature4Service.AUTUMN, AUTUMN_ACCENT);
        seasonColors.put(Feature4Service.WINTER, WINTER_ACCENT);

        for (Map.Entry<String, Color> e : seasonColors.entrySet()) {
            seasons.addTab(e.getKey(), buildSeasonScroll(e.getKey(), e.getValue()));
        }

        content.add(seasons, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JScrollPane buildSeasonScroll(String season, Color accent) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBackground(COLOR_BG);
        column.setBorder(new EmptyBorder(8, 4, 16, 4));

        for (String[] row : service.getFestivalsBySeason(season)) {
            column.add(festivalCard(row, accent));
            column.add(Box.createVerticalStrut(14));
        }

        JScrollPane scroll = new JScrollPane(column);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(COLOR_BG);
        return scroll;
    }

    private JPanel festivalCard(String[] row, Color accent) {
        String name = row[0];
        String month = row[1];
        String region = row[2];
        String description = row[3];

        JPanel card = createCard();
        card.setLayout(new BorderLayout(8, 8));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(4, 0, 0, 0, accent),
            BorderFactory.createEmptyBorder(10, 12, 12, 12)
        ));

        JLabel title = new JLabel("<html><b>" + escapeHtml(name) + "</b></html>");
        title.setFont(FONT_LABEL);
        title.setForeground(COLOR_TEXT);

        JLabel meta = new JLabel("<html><span style='color:#555'>" + escapeHtml(month)
            + " · " + escapeHtml(region) + "</span></html>");
        meta.setFont(FONT_SMALL);

        JLabel desc = new JLabel("<html><div style='width:720px'>" + escapeHtml(description) + "</div></html>");
        desc.setFont(FONT_BODY);
        desc.setForeground(COLOR_TEXT);

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setOpaque(false);
        north.add(title);
        north.add(Box.createVerticalStrut(4));
        north.add(meta);
        north.add(Box.createVerticalStrut(8));
        north.add(desc);

        card.add(north, BorderLayout.NORTH);
        card.add(buildClothingPanel(region), BorderLayout.CENTER);

        return card;
    }

    private JPanel buildClothingPanel(String festivalRegion) {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);

        JLabel heading = new JLabel("Festival picks from the closet");
        heading.setFont(FONT_SMALL);
        heading.setForeground(COLOR_PRIMARY);
        wrap.add(heading, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setBorder(new EmptyBorder(8, 0, 0, 0));

        List<ClothingItem> picks = clothingForFestivalRegion(festivalRegion);
        if (picks.isEmpty()) {
            JLabel empty = new JLabel("<html><i>No festival-tagged items match this region in DataStore.</i></html>");
            empty.setFont(FONT_SMALL);
            empty.setForeground(new Color(100, 90, 80));
            list.add(empty);
        } else {
            for (ClothingItem item : picks) {
                list.add(clothingRow(item));
                list.add(Box.createVerticalStrut(6));
            }
        }

        wrap.add(list, BorderLayout.CENTER);
        return wrap;
    }

    /**
     * Items that are festival-appropriate ({@code FilterUtils.filterByOccasion("Festival")})
     * and match the festival's region ({@code FilterUtils.filterByRegion}).
     */
    private List<ClothingItem> clothingForFestivalRegion(String region) {
        List<ClothingItem> festival = new ArrayList<>(FilterUtils.filterByOccasion("Festival"));
        festival.retainAll(FilterUtils.filterByRegion(region));
        return festival;
    }

    private JPanel clothingRow(ClothingItem item) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(true);
        row.setBackground(new Color(255, 252, 245));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(6, 10, 6, 10)
        ));

        JLabel left = new JLabel("<html>" + item.getImageIcon() + " <b>" + escapeHtml(item.getName())
            + "</b><br><span style='color:#666;font-size:11px'>" + escapeHtml(item.getRegion())
            + " · " + escapeHtml(item.getFabricType()) + "</span></html>");
        left.setFont(FONT_BODY);

        row.add(left, BorderLayout.CENTER);
        return row;
    }

    private static String escapeHtml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;");
    }
}
