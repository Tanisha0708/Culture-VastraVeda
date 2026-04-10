package vastraveda.features.feature9_gallery;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Feature9UI extends BaseUI implements Feature {

    private final Feature9Service service = new Feature9Service();
    private JTable issueTable;
    private DefaultTableModel issueModel;
    private List<Feature9Service.Issue> rowToIssue = new ArrayList<>();

    public Feature9UI() {
        super("Issue Tracker");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.add(createHeader("🛡 Issue Tracker — Cultural Accuracy", "Report problems — moderators update status"), BorderLayout.NORTH);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.setBackground(new Color(245, 235, 215));
        JButton report = createStyledButton("Report issue", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        report.addActionListener(e -> openReportForm());
        JButton refresh = createStyledButton("Refresh list", COLOR_BORDER, COLOR_TEXT);
        refresh.addActionListener(e -> reloadTable());
        actions.add(report);
        actions.add(refresh);
        topWrap.add(actions, BorderLayout.SOUTH);
        add(topWrap, BorderLayout.NORTH);

        String[] cols = {"ID", "Garment", "Title", "Reporter", "Status", "Mod note"};
        issueModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        issueTable = new JTable(issueModel);
        issueTable.setFont(FONT_SMALL);
        issueTable.setRowHeight(22);
        issueTable.getTableHeader().setBackground(COLOR_PRIMARY);
        issueTable.getTableHeader().setForeground(Color.WHITE);

        JPanel mod = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        mod.setBorder(BorderFactory.createTitledBorder("Moderator dashboard"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            Feature9Service.STATUS_OPEN,
            Feature9Service.STATUS_REVIEW,
            Feature9Service.STATUS_RESOLVED
        });
        JTextField note = new JTextField(20);
        JButton apply = createStyledButton("Update selected", COLOR_ACCENT, COLOR_TEXT_LIGHT);
        apply.addActionListener(e -> {
            int r = issueTable.getSelectedRow();
            if (r < 0 || r >= rowToIssue.size()) {
                JOptionPane.showMessageDialog(this, "Select an issue row.", "Moderator", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Feature9Service.Issue iss = rowToIssue.get(r);
            service.updateStatus(iss, (String) statusCombo.getSelectedItem(), note.getText().trim());
            reloadTable();
        });
        mod.add(new JLabel("Status:"));
        mod.add(statusCombo);
        mod.add(new JLabel("Note:"));
        mod.add(note);
        mod.add(apply);

        JPanel south = new JPanel(new BorderLayout());
        south.add(mod, BorderLayout.CENTER);

        add(new JScrollPane(issueTable), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        reloadTable();
    }

    private void reloadTable() {
        issueModel.setRowCount(0);
        rowToIssue.clear();
        for (Feature9Service.Issue i : service.getIssues()) {
            rowToIssue.add(i);
            issueModel.addRow(new Object[]{
                i.id, i.garmentName, i.title, i.reporter, i.status,
                i.moderatorNote == null ? "" : i.moderatorNote
            });
        }
    }

    private void openReportForm() {
        List<String> names = new ArrayList<>();
        for (ClothingItem it : service.getGarments()) {
            names.add(it.getName());
        }
        JComboBox<String> g = new JComboBox<>(names.toArray(new String[0]));
        JTextField title = new JTextField(20);
        JTextArea detail = new JTextArea(4, 20);
        JTextField reporter = new JTextField("anonymous", 12);
        JPanel p = new JPanel(new GridLayout(0, 1, 4, 4));
        p.add(new JLabel("Garment:"));
        p.add(g);
        p.add(new JLabel("Title:"));
        p.add(title);
        p.add(new JLabel("Description:"));
        p.add(new JScrollPane(detail));
        p.add(new JLabel("Your name / handle:"));
        p.add(reporter);
        int ok = JOptionPane.showConfirmDialog(this, p, "Report cultural accuracy issue", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION && !title.getText().trim().isEmpty()) {
            service.addIssue((String) g.getSelectedItem(), title.getText().trim(), detail.getText().trim(), reporter.getText().trim());
            reloadTable();
            showInfo("Submitted", "Issue recorded. Thank you.");
        }
    }
}
