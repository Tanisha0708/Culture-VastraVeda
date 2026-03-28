package vastraveda.features.feature1_directory;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.data.DataStore;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.utils.FilterUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  FEATURE 1 — Regional Clothing Directory             ║
 * ║  CONTRIBUTOR: Your Name                              ║
 * ║  Modify ONLY this file and Feature1Service.java      ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Displays all clothing items in a filterable table view.
 */
public class Feature1UI extends BaseUI implements Feature {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> regionFilter;
    private JComboBox<String> genderFilter;
    private JComboBox<String> fabricFilter;
    private JComboBox<String> occasionFilter;
    private JTextField searchField;

    private final Feature1Service service = new Feature1Service();

    public Feature1UI() {
        super("Regional Clothing Directory");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        // Header
        add(createHeader("🗂  Regional Clothing Directory",
            "Browse and filter traditional Indian garments"), BorderLayout.NORTH);

        // Filter Bar
        add(buildFilterPanel(), BorderLayout.CENTER);

        // Status bar
        JLabel status = new JLabel("  Showing all items", JLabel.LEFT);
        status.setFont(FONT_SMALL);
        status.setForeground(COLOR_PRIMARY);
        status.setBorder(BorderFactory.createEmptyBorder(4, 12, 6, 12));
        add(status, BorderLayout.SOUTH);
    }

    private JPanel buildFilterPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setBackground(COLOR_BG);

        // ── Filter Controls ──────────────────────────────────────
        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterRow.setBackground(new Color(245, 235, 215));
        filterRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        searchField = new JTextField(14);
        searchField.setFont(FONT_BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        List<String> regions = DataStore.getAllRegions();
        regions.add(0, "All Regions");
        regionFilter = createComboBox(regions.toArray(new String[0]));

        List<String> fabrics = DataStore.getAllFabrics();
        fabrics.add(0, "All Fabrics");
        fabricFilter = createComboBox(fabrics.toArray(new String[0]));

        genderFilter = createComboBox(new String[]{"All Genders", "Female", "Male", "Unisex"});
        occasionFilter = createComboBox(new String[]{"All Occasions", "Wedding", "Festival", "Casual", "Formal", "Religious", "Dance"});

        JButton applyBtn = createStyledButton("Apply Filters", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        JButton resetBtn = createStyledButton("Reset", COLOR_BORDER, COLOR_TEXT);

        filterRow.add(new JLabel("🔍"));
        filterRow.add(searchField);
        filterRow.add(new JLabel("Region:"));
        filterRow.add(regionFilter);
        filterRow.add(new JLabel("Fabric:"));
        filterRow.add(fabricFilter);
        filterRow.add(new JLabel("Gender:"));
        filterRow.add(genderFilter);
        filterRow.add(new JLabel("Occasion:"));
        filterRow.add(occasionFilter);
        filterRow.add(applyBtn);
        filterRow.add(resetBtn);

        // ── Table ────────────────────────────────────────────────
        String[] columns = {"Icon", "Name", "Region", "Fabric", "Occasion", "Gender", "Era"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(FONT_BODY);
        table.setForeground(COLOR_TEXT);
        table.setGridColor(new Color(225, 210, 185));
        table.setSelectionBackground(new Color(212, 160, 23, 80));
        table.setSelectionForeground(COLOR_TEXT);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(FONT_LABEL);
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(130);

        // Detail on double-click
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) showDetail();
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        scroll.getViewport().setBackground(COLOR_BG);

        // Button actions
        applyBtn.addActionListener(e -> applyFilters());
        resetBtn.addActionListener(e -> {
            regionFilter.setSelectedIndex(0);
            fabricFilter.setSelectedIndex(0);
            genderFilter.setSelectedIndex(0);
            occasionFilter.setSelectedIndex(0);
            searchField.setText("");
            loadTable(DataStore.getAllItems());
        });
        searchField.addActionListener(e -> applyFilters());

        outer.add(filterRow, BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);

        loadTable(DataStore.getAllItems());
        return outer;
    }

    private void applyFilters() {
        String region  = (String) regionFilter.getSelectedItem();
        String fabric  = (String) fabricFilter.getSelectedItem();
        String gender  = (String) genderFilter.getSelectedItem();
        String occasion= (String) occasionFilter.getSelectedItem();
        String search  = searchField.getText().trim();

        List<ClothingItem> filtered = FilterUtils.filterMulti(region, fabric, gender, occasion);
        if (!search.isEmpty()) {
            filtered.retainAll(FilterUtils.searchByName(search));
        }
        loadTable(filtered);
    }

    private void loadTable(List<ClothingItem> items) {
        tableModel.setRowCount(0);
        for (ClothingItem item : items) {
            tableModel.addRow(new Object[]{
                item.getImageIcon(),
                item.getName(),
                item.getRegion(),
                item.getFabricType(),
                item.getOccasion(),
                item.getGender(),
                item.getEra()
            });
        }
    }

    private void showDetail() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        String name = (String) tableModel.getValueAt(row, 1);
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getName().equals(name)) {
                String msg = service.formatDetail(item);
                JTextArea area = new JTextArea(msg);
                area.setFont(FONT_BODY);
                area.setEditable(false);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setPreferredSize(new Dimension(400, 220));
                JOptionPane.showMessageDialog(this, new JScrollPane(area),
                    item.getImageIcon() + " " + item.getName(), JOptionPane.PLAIN_MESSAGE);
                break;
            }
        }
    }
}
