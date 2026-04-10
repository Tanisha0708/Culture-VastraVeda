package vastraveda.features.feature10_stories;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Feature 10 — Contributor Leaderboard.
 */
public class Feature10UI extends BaseUI implements Feature {

    private final Feature10Service service = new Feature10Service();
    private JComboBox<String> timeCombo;
    private JComboBox<String> stateCombo;
    private JComboBox<String> typeCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    public Feature10UI() {
        super("Contributor Leaderboard");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🏆 Contributor Leaderboard", "Rank by activity — filter time, state, contribution type"), BorderLayout.NORTH);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filters.setBackground(new Color(245, 235, 215));
        filters.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        filters.add(new JLabel("Time:"));
        timeCombo = new JComboBox<>(new String[]{
            Feature10Service.TIME_ALL,
            Feature10Service.TIME_30,
            Feature10Service.TIME_7
        });
        timeCombo.setFont(FONT_BODY);

        filters.add(new JLabel("State:"));
        stateCombo = new JComboBox<>(service.getStateFilters().toArray(new String[0]));
        stateCombo.setFont(FONT_BODY);

        filters.add(new JLabel("Type:"));
        typeCombo = new JComboBox<>(new String[]{
            Feature10Service.TYPE_ALL,
            Feature10Service.TYPE_UI,
            Feature10Service.TYPE_DATA,
            Feature10Service.TYPE_DOCS,
            Feature10Service.TYPE_REVIEW
        });
        typeCombo.setFont(FONT_BODY);

        JButton apply = createStyledButton("Apply filters", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        apply.addActionListener(e -> refreshTable());
        filters.add(apply);

        String[] cols = {"Rank", "Contributor", "State", "Score", "Activity (UI/Data/Docs/Reviews)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(FONT_BODY);
        table.getTableHeader().setFont(FONT_LABEL);
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        add(filters, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JLabel foot = new JLabel("  Scoring: UI×2, Data×3, Docs×2, Reviews×4 (filter by type shows partial score)");
        foot.setFont(FONT_SMALL);
        foot.setForeground(COLOR_PRIMARY);
        foot.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
        add(foot, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        String t = (String) timeCombo.getSelectedItem();
        String s = (String) stateCombo.getSelectedItem();
        String ty = (String) typeCombo.getSelectedItem();
        if (Feature10Service.TYPE_ALL.equals(ty)) {
            ty = Feature10Service.TYPE_ALL;
        }
        List<Feature10Service.RankRow> rows = service.getLeaderboard(t, s, ty);
        tableModel.setRowCount(0);
        for (Feature10Service.RankRow row : rows) {
            tableModel.addRow(new Object[]{
                row.rank,
                row.c.name,
                row.c.state,
                row.displayScore,
                service.formatBreakdown(row.c)
            });
        }
    }
}
