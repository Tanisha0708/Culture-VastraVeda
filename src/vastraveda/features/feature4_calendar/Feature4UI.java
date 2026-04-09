package vastraveda.features.feature4_calendar;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.utils.FilterUtils;
import vastraveda.core.models.ClothingItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Festival calendar: month grid with festival days; click a day to see expected clothing from DataStore.
 */
public class Feature4UI extends BaseUI implements Feature {

    private static final Color SPRING_ACCENT = new Color(34, 139, 34);
    private static final Color SUMMER_ACCENT = new Color(230, 126, 34);
    private static final Color AUTUMN_ACCENT = new Color(192, 57, 43);
    private static final Color WINTER_ACCENT = new Color(41, 128, 185);

    private final Feature4Service service = new Feature4Service();

    private YearMonth viewMonth = YearMonth.from(LocalDate.now());
    private final JPanel calendarGrid = new JPanel(new GridLayout(0, 7, 4, 4));
    private final JLabel monthTitle = new JLabel("", JLabel.CENTER);
    private final JPanel detailBody = new JPanel();
    private final JScrollPane detailScroll;

    public Feature4UI() {
        super("Festival Calendar");
        detailBody.setLayout(new BoxLayout(detailBody, BoxLayout.Y_AXIS));
        detailBody.setBackground(COLOR_CARD);
        detailBody.setBorder(new EmptyBorder(12, 14, 12, 14));
        detailScroll = new JScrollPane(detailBody);
        detailScroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        detailScroll.getViewport().setBackground(COLOR_CARD);
        detailScroll.getVerticalScrollBar().setUnitIncrement(16);
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🗓  Festival Calendar",
            "Click a highlighted festival day to see traditional clothing to wear."), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(16, 0));
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(12, 20, 16, 20));

        JPanel left = new JPanel(new BorderLayout(0, 10));
        left.setOpaque(false);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        nav.setOpaque(false);
        JButton prev = createStyledButton("◀ Month", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        JButton next = createStyledButton("Month ▶", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        prev.addActionListener(e -> {
            viewMonth = viewMonth.minusMonths(1);
            refreshCalendar();
        });
        next.addActionListener(e -> {
            viewMonth = viewMonth.plusMonths(1);
            refreshCalendar();
        });
        nav.add(prev);
        nav.add(next);

        monthTitle.setFont(FONT_TITLE);
        monthTitle.setForeground(COLOR_TEXT);

        JPanel calWrap = createCard();
        calWrap.setLayout(new BorderLayout(8, 8));
        calWrap.add(monthTitle, BorderLayout.NORTH);
        calWrap.add(nav, BorderLayout.SOUTH);

        calendarGrid.setOpaque(false);
        JScrollPane calScroll = new JScrollPane(calendarGrid);
        calScroll.setBorder(null);
        calScroll.getViewport().setBackground(COLOR_CARD);
        calScroll.setPreferredSize(new Dimension(440, 380));

        left.add(calWrap, BorderLayout.NORTH);
        left.add(calScroll, BorderLayout.CENTER);

        JPanel right = createCard();
        right.setLayout(new BorderLayout());
        JLabel detailHeader = new JLabel("Expected attire");
        detailHeader.setFont(FONT_LABEL);
        detailHeader.setForeground(COLOR_PRIMARY);
        detailHeader.setBorder(new EmptyBorder(0, 0, 8, 0));
        right.add(detailHeader, BorderLayout.NORTH);
        right.add(detailScroll, BorderLayout.CENTER);

        JPanel split = new JPanel(new GridLayout(1, 2, 20, 0));
        split.setOpaque(false);
        split.add(left);
        split.add(right);

        content.add(split, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        showPlaceholderDetail();
        refreshCalendar();
    }

    private void refreshCalendar() {
        calendarGrid.removeAll();
        monthTitle.setText(viewMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + viewMonth.getYear());

        String[] dow = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : dow) {
            JLabel h = new JLabel(d, JLabel.CENTER);
            h.setFont(FONT_SMALL);
            h.setForeground(COLOR_PRIMARY);
            calendarGrid.add(h);
        }

        LocalDate first = viewMonth.atDay(1);
        int skip = first.getDayOfWeek().getValue() % 7;
        int daysInMonth = viewMonth.lengthOfMonth();
        int m = viewMonth.getMonthValue();

        for (int i = 0; i < skip; i++) {
            calendarGrid.add(emptyCell());
        }

        for (int day = 1; day <= daysInMonth; day++) {
            List<String[]> onDay = service.getFestivalsOn(m, day);
            calendarGrid.add(dayCell(day, onDay));
        }

        int used = skip + daysInMonth;
        int remainder = used % 7;
        if (remainder != 0) {
            for (int i = 0; i < 7 - remainder; i++) {
                calendarGrid.add(emptyCell());
            }
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private JPanel emptyCell() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        return p;
    }

    private JButton dayCell(int day, List<String[]> festivalsOnDay) {
        boolean hasFestival = !festivalsOnDay.isEmpty();
        JButton b = new JButton();
        b.setFont(FONT_BODY);
        b.setFocusPainted(false);
        b.setMargin(new Insets(6, 2, 6, 2));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (hasFestival) {
            b.setText(festivalDayBlockHtml(day, festivalsOnDay));
            Color accent = accentForCalendarMonth(viewMonth.getMonthValue());
            b.setBackground(accent);
            b.setForeground(Color.WHITE);
            b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_SECONDARY, 2),
                BorderFactory.createEmptyBorder(4, 2, 4, 2)
            ));
            String tip = festivalsOnDay.size() == 1
                ? festivalsOnDay.get(0)[Feature4Service.IDX_NAME]
                : festivalsOnDay.size() + " festivals — click to choose";
            b.setToolTipText(tip);
            b.addActionListener(e -> pickFestivalForDay(day, festivalsOnDay));
        } else {
            b.setText(String.valueOf(day));
            b.setBackground(COLOR_BG);
            b.setForeground(COLOR_TEXT);
            b.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
            b.addActionListener(e -> showNoFestivalMessage(day));
        }

        return b;
    }

    /**
     * Day number plus festival name(s) inside the calendar cell (HTML for line wrap).
     */
    private String festivalDayBlockHtml(int day, List<String[]> festivalsOnDay) {
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < festivalsOnDay.size(); i++) {
            if (i > 0) {
                names.append("<br/>");
            }
            String n = escapeHtml(festivalsOnDay.get(i)[Feature4Service.IDX_NAME]);
            names.append("<span style='font-size:10px;font-weight:normal;color:#FFFFFF'>").append(n).append("</span>");
        }
        return "<html><center><div style='width:78px'>"
            + "<span style='font-size:13px;font-weight:bold;color:#FFFFFF'>" + day + "</span><br/>"
            + names
            + "</div></center></html>";
    }

    private void pickFestivalForDay(int day, List<String[]> festivalsOnDay) {
        String[] row;
        if (festivalsOnDay.size() == 1) {
            row = festivalsOnDay.get(0);
        } else {
            String[] names = new String[festivalsOnDay.size()];
            for (int i = 0; i < festivalsOnDay.size(); i++) {
                names[i] = festivalsOnDay.get(i)[Feature4Service.IDX_NAME];
            }
            String chosen = (String) JOptionPane.showInputDialog(this,
                "Multiple festivals on this day. Pick one:",
                "Festival",
                JOptionPane.QUESTION_MESSAGE,
                null,
                names,
                names[0]);
            if (chosen == null) {
                return;
            }
            row = null;
            for (String[] f : festivalsOnDay) {
                if (chosen.equals(f[Feature4Service.IDX_NAME])) {
                    row = f;
                    break;
                }
            }
            if (row == null) {
                return;
            }
        }
        showFestivalDetail(row);
    }

    private void showNoFestivalMessage(int day) {
        detailBody.removeAll();
        JLabel msg = new JLabel("<html><div style='width:280px'><b>" + day + " "
            + viewMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + "</b><br><br>"
            + "<i>No festival on this date in the demo calendar. Try a highlighted day.</i></div></html>");
        msg.setFont(FONT_BODY);
        msg.setForeground(COLOR_TEXT);
        detailBody.add(msg);
        detailBody.revalidate();
        detailBody.repaint();
    }

    private void showPlaceholderDetail() {
        detailBody.removeAll();
        JLabel msg = new JLabel("<html><div style='width:280px'><i>Click a colored day on the calendar "
            + "to see which clothing items from the directory match that festival and region.</i></div></html>");
        msg.setFont(FONT_BODY);
        msg.setForeground(new Color(100, 90, 80));
        detailBody.add(msg);
        detailBody.revalidate();
        detailBody.repaint();
    }

    private void showFestivalDetail(String[] row) {
        String name = row[Feature4Service.IDX_NAME];
        String month = row[Feature4Service.IDX_MONTH];
        String day = row[Feature4Service.IDX_DAY];
        String region = row[Feature4Service.IDX_REGION];
        String description = row[Feature4Service.IDX_DESCRIPTION];
        Color accent = accentForCalendarMonth(Feature4Service.monthOf(row));

        detailBody.removeAll();

        JPanel banner = new JPanel(new BorderLayout());
        banner.setOpaque(true);
        banner.setBackground(accent);
        banner.setBorder(new EmptyBorder(10, 12, 10, 12));
        JLabel title = new JLabel("<html><b style='color:white'>" + escapeHtml(name) + "</b></html>");
        title.setFont(FONT_LABEL);
        JLabel when = new JLabel("<html><span style='color:#f0f0f0'>" + escapeHtml(month) + " " + escapeHtml(day)
            + " · " + escapeHtml(region) + "</span></html>");
        when.setFont(FONT_SMALL);
        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);
        textCol.add(title);
        textCol.add(Box.createVerticalStrut(4));
        textCol.add(when);
        banner.add(textCol, BorderLayout.CENTER);
        detailBody.add(banner);
        detailBody.add(Box.createVerticalStrut(12));

        JLabel desc = new JLabel("<html><div style='width:300px'>" + escapeHtml(description) + "</div></html>");
        desc.setFont(FONT_BODY);
        desc.setForeground(COLOR_TEXT);
        detailBody.add(desc);
        detailBody.add(Box.createVerticalStrut(12));

        JLabel sub = new JLabel("Clothing often worn (from DataStore, Festival + region match)");
        sub.setFont(FONT_SMALL);
        sub.setForeground(COLOR_PRIMARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailBody.add(sub);
        detailBody.add(Box.createVerticalStrut(8));

        List<ClothingItem> picks = clothingForFestivalRegion(region);
        if (picks.isEmpty()) {
            JLabel empty = new JLabel("<html><i>No festival-tagged items match this region in DataStore.</i></html>");
            empty.setFont(FONT_SMALL);
            empty.setForeground(new Color(100, 90, 80));
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailBody.add(empty);
        } else {
            for (ClothingItem item : picks) {
                JPanel rowPanel = clothingRow(item, accent);
                rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                detailBody.add(rowPanel);
                detailBody.add(Box.createVerticalStrut(6));
            }
        }

        detailBody.revalidate();
        detailBody.repaint();
    }

    private List<ClothingItem> clothingForFestivalRegion(String region) {
        List<ClothingItem> festival = new ArrayList<>(FilterUtils.filterByOccasion("Festival"));
        festival.retainAll(FilterUtils.filterByRegion(region));
        return festival;
    }

    private JPanel clothingRow(ClothingItem item, Color accent) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(true);
        row.setBackground(new Color(255, 252, 245));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                new MatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createLineBorder(COLOR_BORDER, 1)
            ),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JLabel left = new JLabel("<html>" + item.getImageIcon() + " <b>" + escapeHtml(item.getName())
            + "</b><br><span style='color:#666;font-size:11px'>" + escapeHtml(item.getRegion())
            + " · " + escapeHtml(item.getFabricType()) + "</span></html>");
        left.setFont(FONT_BODY);
        row.add(left, BorderLayout.CENTER);
        return row;
    }

    private static Color accentForCalendarMonth(int month) {
        if (month == 3 || month == 4 || month == 5) {
            return SPRING_ACCENT;
        }
        if (month == 6 || month == 7 || month == 8) {
            return SUMMER_ACCENT;
        }
        if (month == 9 || month == 10 || month == 11) {
            return AUTUMN_ACCENT;
        }
        return WINTER_ACCENT;
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
