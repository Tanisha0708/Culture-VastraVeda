package vastraveda.features.feature2_map;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Feature 2 - Interactive Clothing Map
 *
 * Displays a clickable India map with region-specific clothing details.
 */
public class Feature2UI extends BaseUI implements Feature {

    private final Feature2Service service = new Feature2Service();
    private final Map<String, JButton> legendButtons = new LinkedHashMap<>();

    private MapCanvas mapCanvas;
    private JPanel resultPanel;
    private JLabel selectedStateLabel;
    private JPanel mapCanvas;
    private JButton activeRegionButton;
    private final Map<String, JButton> regionButtons = new HashMap<>();
    private final Feature2Service service = new Feature2Service();
    private JLabel selectionMetaLabel;
    private JLabel summaryLabel;
    private JLabel mapHintLabel;
    private JButton quickViewButton;
    private Feature2Service.RegionInfo selectedRegion;

    public Feature2UI() {
        super("Interactive Clothing Map");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        add(createHeader(
            "Interactive Clothing Map",
            "Click a state on the India map to view its traditional clothing in the side panel"
        ), BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildMapPanel(), buildDetailPanel());
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(7);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        showRegion(null);
    }

    private JPanel buildMapPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(240, 228, 200));
        outer.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 6));

        JLabel mapTitle = new JLabel("India — Regions", JLabel.CENTER);
        mapTitle.setFont(FONT_LABEL);
        mapTitle.setForeground(COLOR_PRIMARY);
        mapTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        mapCanvas = new JPanel(null);
        mapCanvas.setOpaque(true);
        mapCanvas.setBackground(new Color(251, 244, 232));
        mapCanvas.setPreferredSize(new Dimension(430, 400));

        drawRegionButtons();

        outer.add(mapTitle, BorderLayout.NORTH);
        outer.add(new JScrollPane(mapCanvas) {{
            setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
            getViewport().setBackground(new Color(251, 244, 232));
        }}, BorderLayout.CENTER);

        JPanel outer = new JPanel(new BorderLayout(0, 14));
        outer.setBackground(COLOR_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 10));

        JPanel introCard = createCard();
        introCard.setLayout(new BorderLayout(0, 10));
        introCard.setBackground(new Color(255, 252, 246));

        JLabel title = new JLabel("Map View", SwingConstants.LEFT);
        title.setFont(FONT_SUBTITLE);
        title.setForeground(COLOR_PRIMARY);

        JLabel copy = new JLabel("<html><body style='width:390px'>"
            + "Use the map for direct exploration and the chips below for quick navigation. Hover to preview a state, then click to lock the selection and open its clothing details."
            + "</body></html>");
        copy.setFont(FONT_BODY);
        copy.setForeground(new Color(96, 70, 40));

        introCard.add(title, BorderLayout.NORTH);
        introCard.add(copy, BorderLayout.CENTER);

        mapCanvas = new MapCanvas(service.getRegions());
        mapCanvas.setPreferredSize(new Dimension(450, 330));

        JPanel canvasWrap = createCard();
        canvasWrap.setLayout(new BorderLayout(0, 10));
        canvasWrap.setBackground(new Color(253, 248, 239));
        canvasWrap.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        canvasWrap.add(mapCanvas, BorderLayout.CENTER);

        mapHintLabel = new JLabel("Hover over a state to preview it");
        mapHintLabel.setFont(FONT_SMALL);
        mapHintLabel.setForeground(new Color(116, 88, 55));
        canvasWrap.add(mapHintLabel, BorderLayout.SOUTH);

        JPanel legend = new JPanel(new GridLayout(0, 2, 10, 10));
        legend.setOpaque(false);
        for (Feature2Service.RegionInfo region : service.getRegions()) {
            JButton chip = new JButton(region.getName());
            chip.setFont(new Font("SansSerif", Font.BOLD, 11));
            chip.setMargin(new Insets(8, 12, 8, 12));
            chip.setOpaque(true);
            chip.setFocusPainted(false);
            chip.setBorder(BorderFactory.createLineBorder(region.getAccent().darker()));
            chip.setBackground(lighten(region.getAccent(), 0.86f));
            chip.setForeground(new Color(58, 35, 16));
            chip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            chip.addActionListener(e -> showRegion(region));
            legendButtons.put(region.getName(), chip);
            legend.add(chip);
        }

        JPanel legendWrap = createCard();
        legendWrap.setLayout(new BorderLayout(0, 8));
        JLabel quickSelectTitle = new JLabel("Quick Select");
        quickSelectTitle.setFont(FONT_LABEL);
        quickSelectTitle.setForeground(COLOR_PRIMARY);
        legendWrap.add(quickSelectTitle, BorderLayout.NORTH);
        legendWrap.add(legend, BorderLayout.CENTER);

        outer.add(introCard, BorderLayout.NORTH);
        outer.add(canvasWrap, BorderLayout.CENTER);
        outer.add(legendWrap, BorderLayout.SOUTH);
        return outer;
    }

    private JPanel buildDetailPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 12));
        outer.setBackground(COLOR_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 18));

        JPanel topCard = createCard();
        topCard.setLayout(new BorderLayout(0, 12));

        JPanel textWrap = new JPanel();
        textWrap.setOpaque(false);
        textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));

        selectedStateLabel = new JLabel("← Select a region/state to explore clothing", JLabel.CENTER);
        selectedStateLabel.setFont(FONT_SUBTITLE);
        selectedStateLabel = new JLabel("Select a region");
        selectedStateLabel.setFont(FONT_TITLE);
        selectedStateLabel.setForeground(COLOR_PRIMARY);

        selectionMetaLabel = new JLabel("No state selected yet");
        selectionMetaLabel.setFont(FONT_LABEL);
        selectionMetaLabel.setForeground(new Color(126, 92, 54));

        summaryLabel = new JLabel();
        summaryLabel.setFont(FONT_BODY);
        summaryLabel.setForeground(COLOR_TEXT);

        textWrap.add(selectedStateLabel);
        textWrap.add(Box.createRigidArea(new Dimension(0, 4)));
        textWrap.add(selectionMetaLabel);
        textWrap.add(Box.createRigidArea(new Dimension(0, 8)));
        textWrap.add(summaryLabel);

        quickViewButton = createStyledButton("Open Quick View", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        quickViewButton.addActionListener(e -> openQuickView());
        quickViewButton.setEnabled(false);
        quickViewButton.setPreferredSize(new Dimension(156, 38));

        topCard.add(textWrap, BorderLayout.CENTER);

        JPanel actionRow = new JPanel(new BorderLayout());
        actionRow.setOpaque(false);
        actionRow.add(quickViewButton, BorderLayout.WEST);
        topCard.add(actionRow, BorderLayout.SOUTH);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(COLOR_BG);

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(COLOR_BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        outer.add(topCard, BorderLayout.NORTH);
        outer.add(scrollPane, BorderLayout.CENTER);
        return outer;
    }

    private void drawRegionButtons() {
        Color[] regionColors = {
            new Color(255, 160, 80), new Color(220, 100, 60), new Color(180, 120, 200),
            new Color(100, 160, 220), new Color(255, 180, 60), new Color(120, 180, 120),
            new Color(200, 100, 160), new Color(80, 180, 200), new Color(200, 140, 80)
        };

        int idx = 0;
        for (Feature2Service.MapRegion region : service.getMapRegions()) {
            String name = region.getName();
            Color col = regionColors[idx % regionColors.length];
            JButton btn = createRegionButton(name, col);
            btn.setBounds(region.getX(), region.getY(), 110, 30);
            btn.addActionListener(e -> showRegion(name, col));
            regionButtons.put(name, btn);
            mapCanvas.add(btn);
            idx++;
        }
    }

    private JButton createRegionButton(String name, Color color) {
        JButton btn = new JButton(name);
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Click to view clothing from " + name);
        return btn;
    }

    private void highlightButton(String region) {
        if (activeRegionButton != null) {
            activeRegionButton.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        }
        JButton btn = regionButtons.get(region);
        if (btn != null) {
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BG_DARK, 2),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
            ));
            activeRegionButton = btn;
        }
    }

    private void showRegion(String region, Color accent) {
        highlightButton(region);
        selectedStateLabel.setText("📍 " + region);
        selectedStateLabel.setForeground(accent);
        resultPanel.removeAll();

        List<ClothingItem> items = service.getTopItemsByRegion(region, 12);
    private void showRegion(Feature2Service.RegionInfo region) {
        selectedRegion = region;
        mapCanvas.setSelectedRegion(region == null ? null : region.getName());
        syncLegendSelection(region == null ? null : region.getName());

        resultPanel.removeAll();

        if (region == null) {
            selectedStateLabel.setText("Select a region");
            selectedStateLabel.setForeground(COLOR_PRIMARY);
            selectionMetaLabel.setText("Interactive India clothing explorer");
            summaryLabel.setText("<html><body style='width:330px'>"
                + service.getSelectionSummary(null, new ArrayList<ClothingItem>())
                + "</body></html>");
            quickViewButton.setEnabled(false);
            resultPanel.add(buildEmptyState());
            mapHintLabel.setText("Hover over a state to preview it");
        } else {
            List<ClothingItem> items = service.getItemsForRegion(region.getName());
            selectedStateLabel.setText(region.getName());
            selectedStateLabel.setForeground(region.getAccent().darker());
            selectionMetaLabel.setText(items.size() + " clothing item" + (items.size() == 1 ? "" : "s") + " in the catalog");
            summaryLabel.setText("<html><body style='width:330px'>"
                + service.getSelectionSummary(region, items)
                + "</body></html>");
            quickViewButton.setEnabled(true);
            mapHintLabel.setText(region.getName() + " selected on the map");

            resultPanel.add(buildRegionOverview(region, items.size()));
            resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            if (items.isEmpty()) {
                resultPanel.add(buildNoItemsCard(region));
            } else {
                for (ClothingItem item : items) {
                    resultPanel.add(buildItemCard(item, region.getAccent()));
                    resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private JPanel buildEmptyState() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 12));

        JLabel art = new JLabel("India Clothing Atlas", SwingConstants.CENTER);
        art.setFont(new Font("Serif", Font.BOLD, 24));
        art.setForeground(COLOR_SECONDARY.darker());

        JLabel text = new JLabel("<html><center>"
            + "Pick any highlighted state from the map or the quick-select chips.<br>"
            + "The side panel will show regional garments and heritage notes."
            + "</center></html>", SwingConstants.CENTER);
        text.setFont(FONT_BODY);
        text.setForeground(new Color(102, 78, 48));

        card.add(art, BorderLayout.NORTH);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildRegionOverview(Feature2Service.RegionInfo region, int itemCount) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(14, 0));
        card.setBackground(lighten(region.getAccent(), 0.93f));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(lighten(region.getAccent(), 0.55f), 1),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(10, 10));
        accent.setBackground(region.getAccent());
        accent.setBorder(BorderFactory.createLineBorder(region.getAccent().darker()));

        JLabel content = new JLabel("<html><body style='width:300px'>"
            + "<b>Regional note:</b> " + region.getCultureNote()
            + "<br><br><b>Catalog coverage:</b> " + itemCount + " mapped clothing item" + (itemCount == 1 ? "" : "s")
            + "</body></html>");
        content.setFont(FONT_BODY);
        content.setForeground(COLOR_TEXT);

        card.add(accent, BorderLayout.WEST);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildNoItemsCard(Feature2Service.RegionInfo region) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JLabel label = new JLabel("<html><body style='width:300px'>"
            + "<b>No clothing records yet for " + region.getName() + ".</b><br>"
            + "The map region is interactive and ready, but this project's in-memory catalog does not currently include garments tagged to this state."
            + "</body></html>");
        label.setFont(FONT_BODY);
        label.setForeground(new Color(96, 70, 40));
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildItemCard(ClothingItem item, Color accent) {
        JPanel card = new JPanel(new BorderLayout(14, 0));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, accent),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        JLabel icon = new JLabel(item.getImageIcon(), SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(64, 64));
        icon.setFont(new Font("Serif", Font.PLAIN, 28));
        icon.setOpaque(true);
        icon.setBackground(lighten(accent, 0.90f));
        icon.setBorder(BorderFactory.createLineBorder(lighten(accent, 0.45f)));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Serif", Font.BOLD, 17));
        name.setForeground(COLOR_TEXT);

        JLabel tags = new JLabel(item.getFabricType() + "  |  " + item.getOccasion() + "  |  " + item.getGender());
        tags.setFont(FONT_SMALL);
        tags.setForeground(new Color(112, 86, 54));

        JLabel era = new JLabel("Era: " + item.getEra() + "  |  Care: " + item.getCareInstructions());
        era.setFont(FONT_SMALL);
        era.setForeground(new Color(132, 102, 68));

        JLabel desc = new JLabel("<html><body style='width:300px'>"
            + service.truncate(item.getDescription(), 160)
            + "</body></html>");
        desc.setFont(FONT_BODY);
        desc.setForeground(new Color(78, 56, 34));

        text.add(name);
        text.add(Box.createRigidArea(new Dimension(0, 4)));
        text.add(tags);
        text.add(Box.createRigidArea(new Dimension(0, 4)));
        text.add(era);
        text.add(Box.createRigidArea(new Dimension(0, 7)));
        text.add(desc);

        card.add(icon, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openItemPopup(item);
            }
        });
        return card;
    }

    private void openItemPopup(ClothingItem item) {
        JTextArea area = new JTextArea(
            "Name: " + item.getName() + "\n" +
            "Region: " + item.getRegion() + "\n" +
            "Fabric: " + item.getFabricType() + "\n" +
            "Occasion: " + item.getOccasion() + "\n" +
            "Gender: " + item.getGender() + "\n\n" +
            item.getDescription()
        );
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(FONT_BODY);
        area.setBackground(COLOR_CARD);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setPreferredSize(new Dimension(420, 220));

        JOptionPane.showMessageDialog(
            this,
            new JScrollPane(area),
            item.getImageIcon() + " " + item.getName(),
            JOptionPane.PLAIN_MESSAGE
        );
    }
    private void openQuickView() {
        if (selectedRegion == null) {
            return;
        }
        List<ClothingItem> items = service.getItemsForRegion(selectedRegion.getName());
        StringBuilder builder = new StringBuilder();
        builder.append(selectedRegion.getName()).append("\n\n");
        builder.append(selectedRegion.getFact()).append("\n\n");
        if (items.isEmpty()) {
            builder.append("No clothing items are mapped to this state yet.");
        } else {
            builder.append("Traditional clothing:\n");
            for (ClothingItem item : items) {
                builder.append("- ").append(item.getName()).append(" (").append(item.getFabricType()).append(")\n");
            }
        }

        JOptionPane.showMessageDialog(
            this,
            createTextArea(builder.toString()),
            selectedRegion.getName() + " Clothing",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void syncLegendSelection(String selectedName) {
        for (Map.Entry<String, JButton> entry : legendButtons.entrySet()) {
            Feature2Service.RegionInfo region = service.getRegion(entry.getKey());
            JButton button = entry.getValue();
            boolean selected = entry.getKey().equals(selectedName);
            button.setBackground(selected ? region.getAccent() : lighten(region.getAccent(), 0.86f));
            button.setForeground(selected ? Color.WHITE : new Color(58, 35, 16));
        }
    }

    private Color lighten(Color color, float factor) {
        int red = Math.min(255, Math.round(color.getRed() + (255 - color.getRed()) * factor));
        int green = Math.min(255, Math.round(color.getGreen() + (255 - color.getGreen()) * factor));
        int blue = Math.min(255, Math.round(color.getBlue() + (255 - color.getBlue()) * factor));
        return new Color(red, green, blue);
    }

    private class MapCanvas extends JPanel {
        private final List<Feature2Service.RegionInfo> regions;
        private final Map<String, Polygon> hitAreas = new LinkedHashMap<>();
        private String hoveredRegion;
        private String selectedRegionName;

        MapCanvas(List<Feature2Service.RegionInfo> regions) {
            this.regions = regions;
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setToolTipText("");

            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    String regionName = findRegion(e.getX(), e.getY());
                    if (!safeEquals(regionName, hoveredRegion)) {
                        hoveredRegion = regionName;
                        mapHintLabel.setText(regionName == null
                            ? (selectedRegion == null ? "Hover over a state to preview it" : selectedRegion.getName() + " selected on the map")
                            : "Previewing " + regionName);
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoveredRegion = null;
                    mapHintLabel.setText(selectedRegion == null ? "Hover over a state to preview it" : selectedRegion.getName() + " selected on the map");
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    String regionName = findRegion(e.getX(), e.getY());
                    if (regionName != null) {
                        showRegion(service.getRegion(regionName));
                    }
                }
            };
            addMouseMotionListener(mouseHandler);
            addMouseListener(mouseHandler);
        }

        void setSelectedRegion(String regionName) {
            selectedRegionName = regionName;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setPaint(new GradientPaint(0, 0, new Color(255, 250, 241), 0, height, new Color(244, 231, 206)));
            g2.fill(new RoundRectangle2D.Double(0, 0, width, height, 28, 28));

            g2.setColor(new Color(235, 222, 194));
            g2.fillOval(width - 120, 18, 86, 86);
            g2.fillOval(28, height - 110, 72, 72);

            hitAreas.clear();
            int leftPad = Math.max(36, (width - 220) / 2);
            int topPad = 18;
            double scale = Math.min((width - 120) / 100.0, (height - 45) / 125.0);

            for (Feature2Service.RegionInfo region : regions) {
                Polygon polygon = toPolygon(region, leftPad, topPad, scale);
                hitAreas.put(region.getName(), polygon);

                Color fill = lighten(region.getAccent(), 0.18f);
                if (region.getName().equals(selectedRegionName)) {
                    fill = region.getAccent();
                } else if (region.getName().equals(hoveredRegion)) {
                    fill = lighten(region.getAccent(), 0.05f);
                }

                g2.setColor(fill);
                g2.fillPolygon(polygon);

                g2.setStroke(new BasicStroke(region.getName().equals(selectedRegionName) ? 3.0f : 1.5f));
                g2.setColor(region.getName().equals(selectedRegionName) ? COLOR_BG_DARK : new Color(97, 69, 33));
                g2.drawPolygon(polygon);

                Point2D center = getCenter(polygon);
                g2.setColor(region.getName().equals(selectedRegionName) ? Color.WHITE : new Color(255, 255, 255, 210));
                g2.fillOval((int) center.getX() - 4, (int) center.getY() - 4, 8, 8);
            }

            String focusRegion = hoveredRegion != null ? hoveredRegion : selectedRegionName;
            if (focusRegion != null) {
                drawFocusBadge(g2, focusRegion, 18, 18, service.getRegion(focusRegion).getAccent());
            }

            g2.setColor(new Color(112, 92, 62));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString("Hover to preview. Click to pin a state.", 18, height - 18);
            g2.dispose();
        }

        private Polygon toPolygon(Feature2Service.RegionInfo region, int leftPad, int topPad, double scale) {
            Polygon polygon = new Polygon();
            for (Point2D.Double point : region.getPoints()) {
                polygon.addPoint((int) Math.round(leftPad + point.x * scale), (int) Math.round(topPad + point.y * scale));
            }
            return polygon;
        }

        private String findRegion(int x, int y) {
            for (Map.Entry<String, Polygon> entry : hitAreas.entrySet()) {
                if (entry.getValue().contains(x, y)) {
                    return entry.getKey();
                }
            }
            return null;
        }

        private Point2D getCenter(Polygon polygon) {
            double sumX = 0;
            double sumY = 0;
            for (int i = 0; i < polygon.npoints; i++) {
                sumX += polygon.xpoints[i];
                sumY += polygon.ypoints[i];
            }
            return new Point2D.Double(sumX / polygon.npoints, sumY / polygon.npoints);
        }

        private void drawFocusBadge(Graphics2D g2, String regionName, int x, int y, Color accent) {
            String label = "Selected: " + regionName;
            if (hoveredRegion != null && hoveredRegion.equals(regionName)) {
                label = "Preview: " + regionName;
            }
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            int textWidth = g2.getFontMetrics().stringWidth(label);
            int badgeWidth = textWidth + 24;
            g2.setColor(new Color(255, 252, 246, 235));
            g2.fillRoundRect(x, y, badgeWidth, 28, 16, 16);
            g2.setColor(accent.darker());
            g2.drawRoundRect(x, y, badgeWidth, 28, 16, 16);
            g2.fillOval(x + 10, y + 9, 10, 10);
            g2.setColor(COLOR_TEXT);
            g2.drawString(label, x + 28, y + 18);
        }

        private boolean safeEquals(String first, String second) {
            if (first == null) {
                return second == null;
            }
            return first.equals(second);
        }
    }
}
