package vastraveda.features.feature1_directory;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  FEATURE 1 — Regional Clothing Directory             ║
 * ║  CONTRIBUTOR: Your Name                              ║
 * ║  Modify ONLY this file and Feature1Service.java      ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Displays structured clothing catalog with filters + pagination.
 */
public class Feature1UI extends BaseUI implements Feature {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> regionFilter;
    private JComboBox<String> communityFilter;
    private JComboBox<String> fabricFilter;
    private JComboBox<String> sortFilter;
    private JTextField searchField;
    private JLabel statusLabel;
    private JLabel pageLabel;
    private JButton prevBtn;
    private JButton nextBtn;

    private final Feature1Service service = new Feature1Service();
    private int currentPage = 1;
    private final int pageSize = 8;
    private Feature1Service.PagedResult<Feature1Service.CatalogItem> latestResult;

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

        add(createHeader("🗂  Regional Clothing Directory",
            "Structured catalog by region, community, and fabric"), BorderLayout.NORTH);

        add(buildFilterPanel(), BorderLayout.CENTER);
    }

    private JPanel buildFilterPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setBackground(COLOR_BG);

        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterRow.setBackground(new Color(245, 235, 215));
        filterRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        searchField = new JTextField(14);
        searchField.setFont(FONT_BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        List<String> regions = service.getAllRegions();
        regions.add(0, "All Regions");
        regionFilter = createComboBox(regions.toArray(new String[0]));

        List<String> communities = service.getAllCommunities();
        communities.add(0, "All Communities");
        communityFilter = createComboBox(communities.toArray(new String[0]));

        List<String> fabrics = service.getAllFabrics();
        fabrics.add(0, "All Fabrics");
        fabricFilter = createComboBox(fabrics.toArray(new String[0]));

        sortFilter = createComboBox(new String[]{"Sort: A-Z", "Sort: Fabric"});

        JButton applyBtn = createStyledButton("Apply Filters", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        JButton resetBtn = createStyledButton("Reset", COLOR_BORDER, COLOR_TEXT);

        filterRow.add(new JLabel("Search:"));
        filterRow.add(searchField);
        filterRow.add(new JLabel("Region:"));
        filterRow.add(regionFilter);
        filterRow.add(new JLabel("Community:"));
        filterRow.add(communityFilter);
        filterRow.add(new JLabel("Fabric:"));
        filterRow.add(fabricFilter);
        filterRow.add(sortFilter);
        filterRow.add(applyBtn);
        filterRow.add(resetBtn);

        String[] columns = {"ID", "Name", "Region", "Community", "Fabric"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
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

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showDetail();
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        scroll.getViewport().setBackground(COLOR_BG);

        applyBtn.addActionListener(e -> {
            currentPage = 1;
            applyFilters();
        });
        resetBtn.addActionListener(e -> {
            regionFilter.setSelectedIndex(0);
            communityFilter.setSelectedIndex(0);
            fabricFilter.setSelectedIndex(0);
            sortFilter.setSelectedIndex(0);
            searchField.setText("");
            currentPage = 1;
            applyFilters();
        });
        searchField.addActionListener(e -> {
            currentPage = 1;
            applyFilters();
        });

        JPanel footer = buildFooter();
        outer.add(filterRow, BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        outer.add(footer, BorderLayout.SOUTH);

        applyFilters();
        return outer;
    }

    private void applyFilters() {
        setLoadingState(true, "Loading filtered clothing catalog...");
        try {
            String region = (String) regionFilter.getSelectedItem();
            String community = (String) communityFilter.getSelectedItem();
            String fabric = (String) fabricFilter.getSelectedItem();
            String search = searchField.getText().trim();
            String sort = sortFilter.getSelectedIndex() == 1 ? "fabric" : "name";

            latestResult = service.queryCatalog(region, community, fabric, search, sort, currentPage, pageSize);
            loadTable(latestResult.getData());
            currentPage = latestResult.getPage();
            pageLabel.setText("Page " + currentPage + " / " + latestResult.getTotalPages());
            statusLabel.setText("  Showing " + latestResult.getData().size() + " of " + latestResult.getTotal() + " filtered items");
            prevBtn.setEnabled(currentPage > 1);
            nextBtn.setEnabled(currentPage < latestResult.getTotalPages());
        } catch (RuntimeException ex) {
            statusLabel.setText("  Unable to load catalog: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Unable to load catalog.\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setLoadingState(false, "");
        }
    }

    private void loadTable(List<Feature1Service.CatalogItem> items) {
        tableModel.setRowCount(0);
        for (Feature1Service.CatalogItem item : items) {
            tableModel.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getRegion(),
                item.getCommunity(),
                item.getFabric()
            });
        }
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(COLOR_BG);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));

        statusLabel = new JLabel("  Ready", JLabel.LEFT);
        statusLabel.setFont(FONT_SMALL);
        statusLabel.setForeground(COLOR_PRIMARY);

        JPanel pager = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pager.setBackground(COLOR_BG);
        prevBtn = createStyledButton("Prev", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        nextBtn = createStyledButton("Next", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        pageLabel = new JLabel("Page 1 / 1");
        pageLabel.setFont(FONT_SMALL);
        pageLabel.setForeground(COLOR_TEXT);

        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                applyFilters();
            }
        });
        nextBtn.addActionListener(e -> {
            if (latestResult != null && currentPage < latestResult.getTotalPages()) {
                currentPage++;
                applyFilters();
            }
        });

        pager.add(prevBtn);
        pager.add(pageLabel);
        pager.add(nextBtn);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(pager, BorderLayout.EAST);
        return footer;
    }

    private void setLoadingState(boolean loading, String message) {
        table.setEnabled(!loading);
        prevBtn.setEnabled(!loading && currentPage > 1);
        if (latestResult != null) {
            nextBtn.setEnabled(!loading && currentPage < latestResult.getTotalPages());
        } else {
            nextBtn.setEnabled(!loading);
        }
        if (loading) {
            statusLabel.setText("  " + message);
        }
    }

    private void showDetail() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        if (latestResult == null || latestResult.getData().isEmpty()) return;
        int modelRow = table.convertRowIndexToModel(row);
        if (modelRow < 0 || modelRow >= latestResult.getData().size()) return;

        Feature1Service.CatalogItem item = latestResult.getData().get(modelRow);
        String msg = service.formatDetail(item);
        JTextArea area = new JTextArea(msg);
        area.setFont(FONT_BODY);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setPreferredSize(new Dimension(420, 240));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
            item.getName(), JOptionPane.PLAIN_MESSAGE);
        table.clearSelection();
    }

    @Override
    public void dispose() {
        try {
            super.dispose();
        } catch (RuntimeException ex) {
            // Suppress UI close errors to avoid app crash during shutdown.
        }
    }
}
