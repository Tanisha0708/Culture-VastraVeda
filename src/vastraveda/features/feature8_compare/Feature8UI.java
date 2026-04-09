package vastraveda.features.feature8_compare;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Feature8UI extends BaseUI implements Feature {

    private final Feature8Service service = new Feature8Service();
    private JComboBox<String> comboA;
    private JComboBox<String> comboB;
    private JPanel comparePanel;

    public Feature8UI() {
        super("Compare Outfits");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("⚖️ Compare Outfits", "Side-by-side garment comparison"), BorderLayout.NORTH);

        // Selector panel
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        selectorPanel.setBackground(new Color(245, 235, 215));
        selectorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        List<ClothingItem> items = service.getAllItems();
        String[] names = items.stream().map(ClothingItem::getName).toArray(String[]::new);

        JLabel labelA = new JLabel("Garment A:");
        labelA.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelA.setForeground(COLOR_TEXT);

        comboA = createComboBox(names);
        comboA.setPreferredSize(new Dimension(220, 30));

        JLabel labelB = new JLabel("Garment B:");
        labelB.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelB.setForeground(COLOR_TEXT);

        comboB = createComboBox(names);
        comboB.setPreferredSize(new Dimension(220, 30));
        // Default second selection to a different item
        if (names.length > 1) comboB.setSelectedIndex(1);

        JButton compareBtn = createStyledButton("Compare ⚖️", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        compareBtn.setPreferredSize(new Dimension(130, 32));
        compareBtn.addActionListener(e -> runComparison());

        selectorPanel.add(labelA);
        selectorPanel.add(comboA);
        selectorPanel.add(labelB);
        selectorPanel.add(comboB);
        selectorPanel.add(compareBtn);

        // Legend
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        legend.setBackground(COLOR_BG);
        legend.add(legendDot(new Color(198, 239, 206), "Same value"));
        legend.add(legendDot(new Color(255, 235, 156), "Different value"));

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(COLOR_BG);
        topWrapper.add(selectorPanel, BorderLayout.NORTH);
        topWrapper.add(legend, BorderLayout.SOUTH);

        // Compare result panel
        comparePanel = new JPanel(new BorderLayout());
        comparePanel.setBackground(COLOR_BG);
        comparePanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        showPlaceholder();

        add(topWrapper, BorderLayout.NORTH);
        add(new JScrollPane(comparePanel), BorderLayout.CENTER);

        // Auto-compare on load
        runComparison();
    }

    private void runComparison() {
        List<ClothingItem> items = service.getAllItems();
        int idxA = comboA.getSelectedIndex();
        int idxB = comboB.getSelectedIndex();

        if (idxA < 0 || idxB < 0) return;

        ClothingItem itemA = items.get(idxA);
        ClothingItem itemB = items.get(idxB);
        String[][] rows = service.buildComparisonRows(itemA, itemB);

        comparePanel.removeAll();

        // Item header cards
        JPanel headerRow = new JPanel(new GridLayout(1, 3, 10, 0));
        headerRow.setBackground(COLOR_BG);
        headerRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        headerRow.add(new JLabel(""));  // spacer
        headerRow.add(buildItemHeader(itemA, COLOR_PRIMARY));
        headerRow.add(buildItemHeader(itemB, new Color(100, 60, 20)));

        // Comparison table
        String[] columns = {"Attribute", itemA.getName(), itemB.getName()};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        for (String[] row : rows) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(COLOR_TEXT);
        table.setGridColor(COLOR_BORDER);
        table.setShowVerticalLines(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(280);
        table.getColumnModel().getColumn(2).setPreferredWidth(280);

        // Color renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                String[] dataRow = rows[row];
                if (col == 0) {
                    c.setBackground(new Color(245, 235, 215));
                    c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                } else {
                    boolean match = service.valuesMatch(dataRow[1], dataRow[2]);
                    c.setBackground(sel ? table.getSelectionBackground()
                            : match ? new Color(198, 239, 206) : new Color(255, 235, 156));
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                }
                // Wrap long text
                String text = val != null ? val.toString() : "";
                if (text.length() > 60) {
                    ((JLabel) c).setText("<html><body style='width:260px'>" + text + "</body></html>");
                    table.setRowHeight(row, 52);
                }
                return c;
            }
        });

        comparePanel.add(headerRow, BorderLayout.NORTH);
        comparePanel.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        tableScroll.getViewport().setBackground(COLOR_BG);
        comparePanel.add(tableScroll, BorderLayout.CENTER);

        comparePanel.revalidate();
        comparePanel.repaint();
    }

    private JPanel buildItemHeader(ClothingItem item, Color accentColor) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(6, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        JLabel icon = new JLabel(item.getImageIcon(), SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setPreferredSize(new Dimension(48, 48));

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setForeground(accentColor);

        JLabel region = new JLabel(item.getRegion() + " · " + item.getFabricType());
        region.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        region.setForeground(COLOR_TEXT);

        JPanel text = new JPanel(new BorderLayout());
        text.setBackground(COLOR_CARD);
        text.add(name, BorderLayout.NORTH);
        text.add(region, BorderLayout.SOUTH);

        card.add(icon, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private void showPlaceholder() {
        comparePanel.removeAll();
        JLabel msg = new JLabel("Select two garments above and click Compare", SwingConstants.CENTER);
        msg.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        msg.setForeground(COLOR_TEXT);
        comparePanel.add(msg, BorderLayout.CENTER);
    }

    private JPanel legendDot(Color color, String label) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setBackground(COLOR_BG);
        JLabel dot = new JLabel("  ");
        dot.setOpaque(true);
        dot.setBackground(color);
        dot.setPreferredSize(new Dimension(16, 16));
        dot.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        text.setForeground(COLOR_TEXT);
        p.add(dot);
        p.add(text);
        return p;
    }
}