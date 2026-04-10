package vastraveda.features.feature7_timeline;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Feature7UI extends BaseUI implements Feature {

    private final Feature7Service service = new Feature7Service();
    private JComboBox<String> comboA;
    private JComboBox<String> comboB;
    private JPanel comparePanel;
    private String[] garmentNames;

    public Feature7UI() {
        super("Cross-Culture Comparison");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🌍 Cross-Culture Clothing Comparison", "Compare two garments — differences highlighted"), BorderLayout.NORTH);

        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        selectorPanel.setBackground(new Color(245, 235, 215));
        selectorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        List<ClothingItem> items = service.getAllItems();
        List<String> names = new ArrayList<>();
        for (ClothingItem it : items) {
            names.add(it.getName());
        }
        garmentNames = names.toArray(new String[0]);

        JLabel labelA = new JLabel("Garment A:");
        labelA.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelA.setForeground(COLOR_TEXT);
        comboA = createComboBox(garmentNames);
        comboA.setPreferredSize(new Dimension(220, 30));
        JLabel labelB = new JLabel("Garment B:");
        labelB.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelB.setForeground(COLOR_TEXT);
        comboB = createComboBox(garmentNames);
        comboB.setPreferredSize(new Dimension(220, 30));
        if (garmentNames.length > 1) {
            comboB.setSelectedIndex(1);
        }
        JButton compareBtn = createStyledButton("Compare", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        compareBtn.addActionListener(e -> runComparison());
        JButton resetBtn = createStyledButton("Reset", COLOR_BORDER, COLOR_TEXT);
        resetBtn.addActionListener(e -> resetComparison());

        selectorPanel.add(labelA);
        selectorPanel.add(comboA);
        selectorPanel.add(labelB);
        selectorPanel.add(comboB);
        selectorPanel.add(compareBtn);
        selectorPanel.add(resetBtn);

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        legend.setBackground(COLOR_BG);
        legend.add(legendDot(new Color(198, 239, 206), "Same"));
        legend.add(legendDot(new Color(255, 235, 156), "Different"));

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(COLOR_BG);
        topWrapper.add(selectorPanel, BorderLayout.NORTH);
        topWrapper.add(legend, BorderLayout.SOUTH);

        comparePanel = new JPanel(new BorderLayout());
        comparePanel.setBackground(COLOR_BG);
        comparePanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(topWrapper, BorderLayout.NORTH);
        add(new JScrollPane(comparePanel), BorderLayout.CENTER);
        runComparison();
    }

    private void runComparison() {
        List<ClothingItem> items = service.getAllItems();
        int idxA = comboA.getSelectedIndex();
        int idxB = comboB.getSelectedIndex();
        if (idxA < 0 || idxB < 0) {
            return;
        }
        ClothingItem itemA = items.get(idxA);
        ClothingItem itemB = items.get(idxB);
        final String[][] rows = service.buildComparisonRows(itemA, itemB);

        comparePanel.removeAll();
        JPanel headerRow = new JPanel(new GridLayout(1, 3, 10, 0));
        headerRow.setBackground(COLOR_BG);
        headerRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        headerRow.add(new JLabel(""));
        headerRow.add(buildItemHeader(itemA, COLOR_PRIMARY));
        headerRow.add(buildItemHeader(itemB, new Color(100, 60, 20)));

        String[] columns = {"Attribute", itemA.getName(), itemB.getName()};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (String[] row : rows) {
            model.addRow(row);
        }
        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(FONT_BODY);
        table.setGridColor(COLOR_BORDER);
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String[] dataRow = rows[row];
                if (col == 0) {
                    c.setBackground(new Color(245, 235, 215));
                    c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else {
                    boolean match = service.valuesMatch(dataRow[1], dataRow[2]);
                    c.setBackground(match ? new Color(198, 239, 206) : new Color(255, 235, 156));
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
        comparePanel.add(headerRow, BorderLayout.NORTH);
        comparePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        comparePanel.revalidate();
        comparePanel.repaint();
    }

    private JPanel buildItemHeader(ClothingItem item, Color accent) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(6, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 2),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        JLabel icon = new JLabel(item.getImageIcon(), SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));
        name.setForeground(accent);
        JLabel meta = new JLabel(item.getRegion() + " · " + item.getFabricType());
        meta.setFont(FONT_SMALL);
        JPanel text = new JPanel(new BorderLayout());
        text.setOpaque(false);
        text.add(name, BorderLayout.NORTH);
        text.add(meta, BorderLayout.SOUTH);
        card.add(icon, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private void resetComparison() {
        if (garmentNames == null || garmentNames.length == 0) {
            return;
        }
        comboA.setSelectedIndex(0);
        comboB.setSelectedIndex(garmentNames.length > 1 ? 1 : 0);
        runComparison();
    }

    private JPanel legendDot(Color color, String label) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        JLabel dot = new JLabel("  ");
        dot.setOpaque(true);
        dot.setBackground(color);
        dot.setPreferredSize(new Dimension(14, 14));
        dot.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        p.add(dot);
        p.add(new JLabel(label));
        return p;
    }
}
