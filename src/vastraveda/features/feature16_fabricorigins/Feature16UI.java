package vastraveda.features.feature16_fabricorigins;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Feature16UI extends BaseUI implements Feature {

    private final Feature16Service service = new Feature16Service();
    private JComboBox<String> fabricSelector;
    private JPanel timelinePanel;
    private JLabel profileTitle;
    private JLabel profileMeta;

    public Feature16UI() {
        super("Fabric Origins Tracker");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🧭 Fabric Origins Tracker", "Trace origin and spread of fabrics across India"), BorderLayout.NORTH);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(245, 235, 215));
        top.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JPanel selector = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        selector.setOpaque(false);
        selector.add(new JLabel("Fabric:"));
        fabricSelector = createComboBox(service.getFabricNames().toArray(new String[0]));
        fabricSelector.setPreferredSize(new Dimension(200, 30));
        selector.add(fabricSelector);

        JPanel meta = new JPanel(new GridLayout(2, 1, 0, 2));
        meta.setOpaque(false);
        profileTitle = new JLabel("Select a fabric");
        profileTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        profileTitle.setForeground(COLOR_PRIMARY);
        profileMeta = new JLabel("Origin timeline");
        profileMeta.setFont(FONT_SMALL);
        profileMeta.setForeground(COLOR_TEXT);
        meta.add(profileTitle);
        meta.add(profileMeta);

        top.add(selector, BorderLayout.WEST);
        top.add(meta, BorderLayout.EAST);

        timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(COLOR_BG);
        timelinePanel.setBorder(BorderFactory.createEmptyBorder(12, 18, 16, 18));

        JScrollPane scroll = new JScrollPane(timelinePanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        add(scroll, BorderLayout.SOUTH);

        // layout weights
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, scroll);
        split.setResizeWeight(0.16);
        split.setDividerSize(0);
        split.setEnabled(false);
        add(split, BorderLayout.CENTER);

        fabricSelector.addActionListener(e -> {
            String fabric = (String) fabricSelector.getSelectedItem();
            if (fabric != null) {
                renderFabric(fabric);
            }
        });

        if (fabricSelector.getItemCount() > 0) {
            fabricSelector.setSelectedIndex(0);
            renderFabric((String) fabricSelector.getSelectedItem());
        }
    }

    private void renderFabric(String fabricName) {
        Feature16Service.FabricOrigin fabric = service.getFabric(fabricName);
        timelinePanel.removeAll();
        if (fabric == null) {
            timelinePanel.add(new JLabel("No data found."));
            refresh();
            return;
        }

        profileTitle.setText(fabric.getIcon() + " " + fabric.getFabricName());
        profileMeta.setText("Origin: " + fabric.getOriginRegion() + " (" + service.formatYear(fabric.getOriginYear()) + ")");

        JPanel intro = createCard();
        intro.setLayout(new BorderLayout());
        intro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, COLOR_PRIMARY),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel about = new JLabel("<html><body style='width:700px'>" + fabric.getDescription() + "</body></html>");
        about.setFont(FONT_BODY);
        about.setForeground(COLOR_TEXT);
        intro.add(about, BorderLayout.CENTER);
        timelinePanel.add(intro);
        timelinePanel.add(Box.createVerticalStrut(10));

        List<Step> steps = new ArrayList<>();
        steps.add(new Step(true, fabric.getOriginRegion(), fabric.getOriginYear(),
            "Origin point of " + fabric.getFabricName() + "."));
        for (Feature16Service.SpreadEvent event : fabric.getSpreadHistory()) {
            steps.add(new Step(false, event.getRegion(), event.getYear(), event.getNote()));
        }

        for (Step step : steps) {
            timelinePanel.add(buildStep(step));
            timelinePanel.add(Box.createVerticalStrut(8));
        }

        refresh();
    }

    private JPanel buildStep(Step step) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(COLOR_BG);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));

        JPanel track = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(180, 150, 100));
                g2.fillRect(16, 0, 3, getHeight());
                g2.setColor(step.origin ? COLOR_ACCENT : COLOR_PRIMARY);
                g2.fillOval(8, 28, 18, 18);
                g2.dispose();
            }
        };
        track.setOpaque(false);
        track.setPreferredSize(new Dimension(34, 80));

        JPanel card = createCard();
        card.setLayout(new GridLayout(3, 1, 0, 2));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(step.origin ? COLOR_ACCENT : COLOR_BORDER),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel heading = new JLabel((step.origin ? "Origin" : "Spread") + " • " + step.region);
        heading.setFont(FONT_LABEL);
        heading.setForeground(step.origin ? COLOR_ACCENT : COLOR_PRIMARY);
        JLabel year = new JLabel("Year: " + service.formatYear(step.year));
        year.setFont(FONT_SMALL);
        year.setForeground(COLOR_TEXT);
        JLabel note = new JLabel("<html><body style='width:640px'>" + step.note + "</body></html>");
        note.setFont(FONT_SMALL);
        note.setForeground(new Color(90, 70, 45));

        card.add(heading);
        card.add(year);
        card.add(note);

        row.add(track, BorderLayout.WEST);
        row.add(card, BorderLayout.CENTER);
        return row;
    }

    private void refresh() {
        timelinePanel.revalidate();
        timelinePanel.repaint();
    }

    private static final class Step {
        private final boolean origin;
        private final String region;
        private final int year;
        private final String note;

        private Step(boolean origin, String region, int year, String note) {
            this.origin = origin;
            this.region = region;
            this.year = year;
            this.note = note;
        }
    }
}
