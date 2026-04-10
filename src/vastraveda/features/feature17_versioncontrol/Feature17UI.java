package vastraveda.features.feature17_versioncontrol;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Feature17UI extends BaseUI implements Feature {

    private final Feature17Service service = new Feature17Service();
    private JComboBox<String> garmentCombo;
    private JList<String> historyList;
    private DefaultListModel<String> historyModel;
    private JTextField labelField;
    private JPanel diffPanel;
    private JComboBox<String> compareA;
    private JComboBox<String> compareB;

    public Feature17UI() {
        super("Clothing Version Control");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📚 Clothing Version Control", "Save versions, view history, compare, rollback"), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(8, 8));
        main.setBackground(COLOR_BG);
        main.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        top.setOpaque(false);
        top.add(new JLabel("Garment:"));
        garmentCombo = createComboBox(service.getGarmentNames().toArray(new String[0]));
        garmentCombo.setPreferredSize(new Dimension(220, 28));
        garmentCombo.addActionListener(e -> refreshHistory());
        top.add(garmentCombo);
        top.add(new JLabel("Label:"));
        labelField = new JTextField(12);
        top.add(labelField);
        JButton saveBtn = createStyledButton("Save version", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        saveBtn.addActionListener(e -> doSave());
        top.add(saveBtn);
        JButton refreshBtn = createStyledButton("Refresh", COLOR_BORDER, COLOR_TEXT);
        refreshBtn.addActionListener(e -> refreshHistory());
        top.add(refreshBtn);

        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);
        historyList.setFont(FONT_BODY);
        JScrollPane histScroll = new JScrollPane(historyList);
        histScroll.setBorder(BorderFactory.createTitledBorder("Version history"));
        histScroll.setPreferredSize(new Dimension(280, 200));

        JPanel compareRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        compareRow.setOpaque(false);
        compareRow.add(new JLabel("Compare:"));
        compareA = new JComboBox<>();
        compareB = new JComboBox<>();
        compareA.setPreferredSize(new Dimension(140, 26));
        compareB.setPreferredSize(new Dimension(140, 26));
        compareRow.add(compareA);
        compareRow.add(new JLabel("vs"));
        compareRow.add(compareB);
        JButton cmpBtn = createStyledButton("Show diff", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        cmpBtn.addActionListener(e -> showDiff());
        compareRow.add(cmpBtn);
        JButton rollBtn = createStyledButton("Rollback to selected", COLOR_ACCENT, COLOR_TEXT_LIGHT);
        rollBtn.addActionListener(e -> doRollback());
        compareRow.add(rollBtn);

        diffPanel = new JPanel(new BorderLayout());
        diffPanel.setBackground(COLOR_BG);
        JScrollPane diffScroll = new JScrollPane(diffPanel);
        diffScroll.setBorder(BorderFactory.createTitledBorder("Differences (green = same, amber = changed)"));

        JPanel leftCol = new JPanel(new BorderLayout(4, 4));
        leftCol.setOpaque(false);
        leftCol.add(histScroll, BorderLayout.CENTER);
        leftCol.add(compareRow, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCol, diffScroll);
        split.setResizeWeight(0.35);

        main.add(top, BorderLayout.NORTH);
        main.add(split, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);

        refreshHistory();
    }

    private void doSave() {
        String g = (String) garmentCombo.getSelectedItem();
        if (g == null) {
            return;
        }
        service.saveVersion(g, labelField.getText().trim());
        labelField.setText("");
        refreshHistory();
        showInfo("Saved", "Version saved for " + g);
    }

    private void refreshHistory() {
        String g = (String) garmentCombo.getSelectedItem();
        historyModel.clear();
        compareA.removeAllItems();
        compareB.removeAllItems();
        if (g == null) {
            return;
        }
        List<Feature17Service.Snapshot> snaps = service.getHistory(g);
        for (Feature17Service.Snapshot s : snaps) {
            String line = "v" + s.getVersion() + " — " + s.getLabel();
            historyModel.addElement(line);
            compareA.addItem(line);
            compareB.addItem(line);
        }
        diffPanel.removeAll();
        diffPanel.add(new JLabel("Select two versions and click Show diff.", SwingConstants.CENTER), BorderLayout.CENTER);
        diffPanel.revalidate();
        diffPanel.repaint();
    }

    private Feature17Service.Snapshot snapshotAt(int index) {
        String g = (String) garmentCombo.getSelectedItem();
        if (g == null || index < 0) {
            return null;
        }
        List<Feature17Service.Snapshot> list = service.getHistory(g);
        return index < list.size() ? list.get(index) : null;
    }

    private void showDiff() {
        int ia = compareA.getSelectedIndex();
        int ib = compareB.getSelectedIndex();
        if (ia < 0 || ib < 0 || ia == ib) {
            JOptionPane.showMessageDialog(this, "Pick two different versions.", "Compare", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Feature17Service.Snapshot sa = snapshotAt(ia);
        Feature17Service.Snapshot sb = snapshotAt(ib);
        if (sa == null || sb == null) {
            return;
        }
        Feature17Service.Snapshot older = sa.getVersion() <= sb.getVersion() ? sa : sb;
        Feature17Service.Snapshot newer = sa.getVersion() <= sb.getVersion() ? sb : sa;
        List<Feature17Service.DiffLine> lines = service.compare(older, newer);

        diffPanel.removeAll();
        String[] cols = {"Field", "Older", "Newer"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Feature17Service.DiffLine d : lines) {
            model.addRow(new Object[]{d.field, d.oldVal, d.newVal});
        }
        JTable table = new JTable(model);
        table.setFont(FONT_SMALL);
        table.setRowHeight(22);
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                boolean same = lines.get(row).same;
                if (col == 0) {
                    c.setBackground(new Color(245, 235, 215));
                } else {
                    c.setBackground(same ? new Color(198, 239, 206) : new Color(255, 235, 156));
                }
                return c;
            }
        });
        diffPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        diffPanel.revalidate();
        diffPanel.repaint();
    }

    private void doRollback() {
        int idx = historyList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a version in the list.", "Rollback", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String g = (String) garmentCombo.getSelectedItem();
        Feature17Service.Snapshot target = snapshotAt(idx);
        if (g == null || target == null) {
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
            "Record rollback as a new version pointing at snapshot v" + target.getVersion() + "?",
            "Rollback", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            service.rollback(g, target);
            refreshHistory();
            showInfo("Rollback", "New history entry added. Use Compare to see lineage.");
        }
    }
}
