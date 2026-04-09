package vastraveda.features.feature9_gallery;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature9UI extends BaseUI implements Feature {

    private final Feature9Service service = new Feature9Service();

    public Feature9UI() {
        super("Visual Gallery");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🖼 Visual Gallery", "Browse beautiful garment styles"), BorderLayout.NORTH);

        List<ClothingItem> items = service.getAllGarments();

        // Grid panel: 3 columns
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 12, 12));
        gridPanel.setBackground(COLOR_BG);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        for (ClothingItem item : items) {
            gridPanel.add(buildGarmentCard(item));
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBackground(COLOR_BG);
        scrollPane.getViewport().setBackground(COLOR_BG);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel buildGarmentCard(ClothingItem item) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 6));
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        card.setBackground(COLOR_CARD);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Large emoji icon
        JLabel icon = new JLabel(item.getImageIcon(), SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        icon.setPreferredSize(new Dimension(100, 60));
        card.add(icon, BorderLayout.NORTH);

        // Name
        JLabel name = new JLabel(item.getName(), SwingConstants.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));
        name.setForeground(COLOR_TEXT);
        card.add(name, BorderLayout.CENTER);

        // Region badge
        JLabel region = new JLabel(item.getRegion(), SwingConstants.CENTER);
        region.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        region.setForeground(COLOR_PRIMARY);
        card.add(region, BorderLayout.SOUTH);

        // Click → detail dialog
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showDetailDialog(item);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(255, 245, 220));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(COLOR_CARD);
            }
        });

        return card;
    }

    private void showDetailDialog(ClothingItem item) {
        JDialog dialog = new JDialog(this, item.getName(), true);
        dialog.setSize(420, 360);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(COLOR_BG);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(COLOR_BG_DARK);
        JLabel headerIcon = new JLabel(item.getImageIcon() + "  " + item.getName());
        headerIcon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        headerIcon.setForeground(COLOR_TEXT_LIGHT);
        header.add(headerIcon);
        dialog.add(header, BorderLayout.NORTH);

        // Details panel
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(COLOR_BG);
        details.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        details.add(detailRow("Region", item.getRegion()));
        details.add(Box.createVerticalStrut(8));
        details.add(detailRow("Fabric", item.getFabricType()));
        details.add(Box.createVerticalStrut(8));
        details.add(detailRow("Occasion", item.getOccasion()));
        details.add(Box.createVerticalStrut(8));
        details.add(detailRow("Gender", item.getGender()));
        details.add(Box.createVerticalStrut(8));
        details.add(detailRow("Era", item.getEra()));
        details.add(Box.createVerticalStrut(12));

        JTextArea desc = createTextArea(item.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        details.add(desc);

        dialog.add(new JScrollPane(details), BorderLayout.CENTER);

        JButton close = createStyledButton("Close", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        close.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(COLOR_BG);
        btnPanel.add(close);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel detailRow(String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(COLOR_BG);
        JLabel lbl = new JLabel(label + ": ");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(COLOR_PRIMARY);
        JLabel val = new JLabel(value != null ? value : "N/A");
        val.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        val.setForeground(COLOR_TEXT);
        row.add(lbl);
        row.add(val);
        return row;
    }
}