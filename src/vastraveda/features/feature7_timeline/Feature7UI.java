package vastraveda.features.feature7_timeline;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 7 — Fabric Origins Tracker UI
 */
public class Feature7UI extends BaseUI implements Feature {

    private final Feature7Service service = new Feature7Service();
    private JComboBox<String> fabricSelector;
    private JPanel timelineContent;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JScrollPane timelineScroll;

    public Feature7UI() {
        super("Fabric Origins Tracker");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🧵 Fabric Origins Tracker", "Track origin and geographic spread of Indian fabrics"), BorderLayout.NORTH);

        add(buildTopControls(), BorderLayout.NORTH);
        add(buildTimelinePanel(), BorderLayout.CENTER);

        List<String> names = service.getFabricNames();
        if (!names.isEmpty()) {
            renderTimeline(names.get(0));
        }
    }

    private JPanel buildTopControls() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(245, 235, 215));
        top.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel label = new JLabel("Select Fabric:");
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT);

        List<String> names = service.getFabricNames();
        fabricSelector = createComboBox(names.toArray(new String[0]));
        fabricSelector.setPreferredSize(new Dimension(220, 30));
        fabricSelector.addActionListener(e -> {
            String selected = (String) fabricSelector.getSelectedItem();
            if (selected != null) {
                renderTimeline(selected);
            }
        });

        left.add(label);
        left.add(fabricSelector);

        JPanel right = new JPanel(new GridLayout(2, 1, 0, 2));
        right.setOpaque(false);
        titleLabel = new JLabel("Timeline");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(COLOR_PRIMARY);
        subtitleLabel = new JLabel("Origin and spread");
        subtitleLabel.setFont(FONT_SMALL);
        subtitleLabel.setForeground(COLOR_TEXT);
        right.add(titleLabel);
        right.add(subtitleLabel);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JPanel buildTimelinePanel() {
        timelineContent = new JPanel();
        timelineContent.setLayout(new BoxLayout(timelineContent, BoxLayout.Y_AXIS));
        timelineContent.setBackground(COLOR_BG);
        timelineContent.setBorder(BorderFactory.createEmptyBorder(12, 20, 16, 20));

        timelineScroll = new JScrollPane(timelineContent);
        timelineScroll.setBorder(BorderFactory.createEmptyBorder());
        timelineScroll.getViewport().setBackground(COLOR_BG);
        timelineScroll.getVerticalScrollBar().setUnitIncrement(14);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(COLOR_BG);
        outer.add(timelineScroll, BorderLayout.CENTER);
        return outer;
    }

    private void renderTimeline(String fabricName) {
        Feature7Service.FabricOrigin fabric = service.getFabricByName(fabricName);
        timelineContent.removeAll();
        if (fabric == null) {
            JLabel missing = new JLabel("No timeline data found.", JLabel.CENTER);
            missing.setFont(FONT_BODY);
            missing.setForeground(COLOR_TEXT);
            timelineContent.add(missing);
            refreshTimeline();
            return;
        }

        titleLabel.setText("🧶 " + fabric.getFabricName());
        subtitleLabel.setText("Origin: " + fabric.getOriginRegion() + " (" + service.formatYear(fabric.getOriginYear()) + ")");

        JPanel intro = createCard();
        intro.setLayout(new BorderLayout());
        intro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, COLOR_PRIMARY),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);
        intro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        JLabel desc = new JLabel("<html><body style='width:720px'>" + fabric.getDescription() + "</body></html>");
        desc.setFont(FONT_BODY);
        desc.setForeground(COLOR_TEXT);
        intro.add(desc, BorderLayout.CENTER);
        timelineContent.add(intro);
        timelineContent.add(Box.createVerticalStrut(12));

        List<TimelineStep> steps = buildSteps(fabric);
        for (TimelineStep step : steps) {
            timelineContent.add(buildStepCard(step));
            timelineContent.add(Box.createVerticalStrut(10));
        }

        refreshTimeline();
        SwingUtilities.invokeLater(() -> timelineScroll.getVerticalScrollBar().setValue(0));
    }

    private List<TimelineStep> buildSteps(Feature7Service.FabricOrigin fabric) {
        List<TimelineStep> steps = new ArrayList<>();
        steps.add(new TimelineStep(
            true,
            fabric.getOriginRegion(),
            fabric.getOriginYear(),
            "Origin identified for " + fabric.getFabricName() + "."
        ));
        for (Feature7Service.SpreadEvent event : fabric.getSpreadHistory()) {
            steps.add(new TimelineStep(false, event.getRegion(), event.getYear(), event.getNote()));
        }
        return steps;
    }

    private JPanel buildStepCard(TimelineStep step) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(COLOR_BG);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 86));

        JPanel marker = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(180, 150, 100));
                g2.fillRect(16, 0, 4, getHeight());
                g2.setColor(step.isOrigin ? COLOR_ACCENT : COLOR_PRIMARY);
                g2.fillOval(8, 28, 20, 20);
                g2.dispose();
            }
        };
        marker.setOpaque(false);
        marker.setPreferredSize(new Dimension(34, 80));

        JPanel card = createCard();
        card.setLayout(new GridLayout(3, 1, 0, 2));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(step.isOrigin ? COLOR_ACCENT : COLOR_BORDER),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel heading = new JLabel((step.isOrigin ? "Origin • " : "Spread • ") + step.region);
        heading.setFont(FONT_LABEL);
        heading.setForeground(step.isOrigin ? COLOR_ACCENT : COLOR_PRIMARY);

        JLabel year = new JLabel("Year: " + service.formatYear(step.year));
        year.setFont(FONT_SMALL);
        year.setForeground(COLOR_TEXT);

        JLabel note = new JLabel("<html><body style='width:660px'>" + step.note + "</body></html>");
        note.setFont(FONT_SMALL);
        note.setForeground(new Color(90, 70, 45));

        card.add(heading);
        card.add(year);
        card.add(note);

        row.add(marker, BorderLayout.WEST);
        row.add(card, BorderLayout.CENTER);
        return row;
    }

    private void refreshTimeline() {
        timelineContent.revalidate();
        timelineContent.repaint();
    }

    private static final class TimelineStep {
        private final boolean isOrigin;
        private final String region;
        private final int year;
        private final String note;

        private TimelineStep(boolean isOrigin, String region, int year, String note) {
            this.isOrigin = isOrigin;
            this.region = region;
            this.year = year;
            this.note = note;
        }
    }
}
