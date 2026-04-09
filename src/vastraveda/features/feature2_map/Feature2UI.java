package vastraveda.features.feature2_map;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  FEATURE 2 — Interactive Clothing Map                ║
 * ║  CONTRIBUTOR: Your Name                              ║
 * ║  Modify ONLY this file and Feature2Service.java      ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Displays Indian states as clickable buttons.
 * Clicking a state shows clothing from that region.
 */
public class Feature2UI extends BaseUI implements Feature {

    private JPanel resultPanel;
    private JLabel selectedStateLabel;
    private JPanel mapCanvas;
    private JButton activeRegionButton;
    private final Map<String, JButton> regionButtons = new HashMap<>();
    private final Feature2Service service = new Feature2Service();

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

        add(createHeader("🗺  Interactive Clothing Map",
            "Click a region to explore its traditional clothing"), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            buildMapPanel(), buildResultPanel());
        split.setDividerLocation(360);
        split.setDividerSize(6);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
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

        return outer;
    }

    private JPanel buildResultPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setBackground(COLOR_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 12));

        selectedStateLabel = new JLabel("← Select a region/state to explore clothing", JLabel.CENTER);
        selectedStateLabel.setFont(FONT_SUBTITLE);
        selectedStateLabel.setForeground(COLOR_PRIMARY);
        selectedStateLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(COLOR_BG);

        JScrollPane scroll = new JScrollPane(resultPanel);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scroll.getViewport().setBackground(COLOR_BG);

        outer.add(selectedStateLabel, BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
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

        if (items.isEmpty()) {
            JLabel none = new JLabel("No items found for " + region, JLabel.CENTER);
            none.setFont(FONT_BODY);
            none.setForeground(Color.GRAY);
            resultPanel.add(none);
        } else {
            for (ClothingItem item : items) {
                resultPanel.add(buildItemCard(item, accent));
                resultPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private JPanel buildItemCard(ClothingItem item, Color accent) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(item.getImageIcon(), JLabel.CENTER);
        icon.setFont(new Font("Serif", Font.PLAIN, 28));

        JPanel text = new JPanel(new GridLayout(3, 1, 2, 2));
        text.setOpaque(false);

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Serif", Font.BOLD, 14));
        name.setForeground(COLOR_TEXT);

        JLabel tags = new JLabel(item.getFabricType() + " · " + item.getOccasion() + " · " + item.getGender());
        tags.setFont(FONT_SMALL);
        tags.setForeground(Color.GRAY);

        JLabel desc = new JLabel("<html><body style='width:240px'>" + service.truncate(item.getDescription(), 80) + "</body></html>");
        desc.setFont(FONT_SMALL);
        desc.setForeground(new Color(80, 60, 40));

        text.add(name);
        text.add(tags);
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
}
