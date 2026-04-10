package vastraveda.features.feature4_calendar;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Feature 4 — Multilingual contributions, translator workflow, display language toggle.
 */
public class Feature4UI extends BaseUI implements Feature {

    private final Feature4Service service = new Feature4Service();
    private JComboBox<Feature4Service.LangOption> displayLangCombo;
    private JList<String> itemList;
    private DefaultListModel<String> itemModel;
    private JTextArea previewArea;
    private JComboBox<String> contributeItemCombo;
    private JComboBox<Feature4Service.LangOption> contributeLangCombo;
    private JTextArea contributeText;
    private JTable translatorTable;
    private DefaultTableModel translatorModel;
    private JTextArea englishRefineArea;

    public Feature4UI() {
        super("Multilingual Contributions");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));
        add(createHeader("🌐  Multilingual Contribution Support",
            "Submit in your language · translators publish · toggle display language"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_LABEL);
        tabs.addTab("Browse (language)", buildBrowseTab());
        tabs.addTab("Contribute", buildContributeTab());
        tabs.addTab("Translator dashboard", buildTranslatorTab());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildBrowseTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(8, 12, 12, 12));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        top.setOpaque(false);
        top.add(new JLabel("Display language:"));
        displayLangCombo = new JComboBox<>();
        for (Feature4Service.LangOption o : service.getLanguageOptions()) {
            displayLangCombo.addItem(o);
        }
        displayLangCombo.addActionListener(e -> refreshPreview());
        top.add(displayLangCombo);

        itemModel = new DefaultListModel<>();
        for (String n : service.getAllItemNamesSorted()) {
            itemModel.addElement(n);
        }
        itemList = new JList<>(itemModel);
        itemList.setFont(FONT_BODY);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshPreview();
            }
        });

        previewArea = new JTextArea(10, 40);
        previewArea.setEditable(false);
        previewArea.setLineWrap(true);
        previewArea.setWrapStyleWord(true);
        previewArea.setFont(FONT_BODY);
        previewArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(itemList), new JScrollPane(previewArea));
        split.setResizeWeight(0.28);
        split.setDividerSize(6);

        root.add(top, BorderLayout.NORTH);
        root.add(split, BorderLayout.CENTER);

        if (itemModel.getSize() > 0) {
            itemList.setSelectedIndex(0);
        }
        refreshPreview();
        return root;
    }

    private void refreshPreview() {
        String name = itemList.getSelectedValue();
        Feature4Service.LangOption lang = (Feature4Service.LangOption) displayLangCombo.getSelectedItem();
        if (name == null || lang == null) {
            previewArea.setText("");
            return;
        }
        String text = service.getLocalizedText(name, Feature4Service.FIELD_DESCRIPTION, lang.getCode());
        previewArea.setText(text);
        previewArea.setCaretPosition(0);
    }

    private JPanel buildContributeTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(12, 16, 16, 16));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0;
        gc.gridy = 0;
        form.add(new JLabel("Clothing item"), gc);
        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contributeItemCombo = new JComboBox<>();
        for (String n : service.getAllItemNamesSorted()) {
            contributeItemCombo.addItem(n);
        }
        form.add(contributeItemCombo, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0;
        form.add(new JLabel("Content language"), gc);
        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contributeLangCombo = new JComboBox<>();
        for (Feature4Service.LangOption o : service.getLanguageOptions()) {
            if (!"en".equalsIgnoreCase(o.getCode())) {
                contributeLangCombo.addItem(o);
            }
        }
        form.add(contributeLangCombo, gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        contributeText = new JTextArea(8, 36);
        contributeText.setLineWrap(true);
        contributeText.setWrapStyleWord(true);
        contributeText.setFont(FONT_BODY);
        form.add(new JScrollPane(contributeText), gc);

        JButton submit = createStyledButton("Submit to translation queue", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        submit.addActionListener(e -> {
            try {
                String item = (String) contributeItemCombo.getSelectedItem();
                Feature4Service.LangOption lang = (Feature4Service.LangOption) contributeLangCombo.getSelectedItem();
                service.submitContribution(item, Feature4Service.FIELD_DESCRIPTION, lang.getCode(), contributeText.getText());
                contributeText.setText("");
                reloadTranslatorTable();
                JOptionPane.showMessageDialog(this, "Submitted. Translators can publish it from the dashboard.",
                    "Queued", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Contribute", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setOpaque(false);
        south.add(submit);

        root.add(form, BorderLayout.CENTER);
        root.add(south, BorderLayout.SOUTH);
        return root;
    }

    private JPanel buildTranslatorTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(8, 12, 12, 12));

        JLabel hint = new JLabel("<html>Pending rows: publish stores the community text for its language. "
            + "Optional English box updates the canonical English blurb.</html>");
        hint.setFont(FONT_SMALL);
        hint.setForeground(new Color(90, 70, 40));

        String[] cols = {"ID", "Item", "Field", "From lang", "Status", "Source preview"};
        translatorModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        translatorTable = new JTable(translatorModel);
        translatorTable.setFont(FONT_SMALL);
        translatorTable.setRowHeight(22);
        translatorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        translatorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPendingIntoEditor();
            }
        });

        englishRefineArea = new JTextArea(4, 40);
        englishRefineArea.setLineWrap(true);
        englishRefineArea.setWrapStyleWord(true);
        englishRefineArea.setFont(FONT_BODY);
        englishRefineArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)));

        JButton markReview = createStyledButton("Mark in review", COLOR_BORDER, COLOR_TEXT);
        markReview.addActionListener(e -> {
            String id = getSelectedQueueId();
            if (id == null) {
                return;
            }
            try {
                service.markInReview(id);
                reloadTranslatorTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Queue", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton publish = createStyledButton("Publish translation", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        publish.addActionListener(e -> {
            String id = getSelectedQueueId();
            if (id == null) {
                return;
            }
            try {
                service.publishTranslation(id, englishRefineArea.getText());
                englishRefineArea.setText("");
                reloadTranslatorTable();
                refreshPreview();
                JOptionPane.showMessageDialog(this, "Saved to in-memory catalog for this session.",
                    "Published", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Publish", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        actions.add(markReview);
        actions.add(publish);

        JPanel south = new JPanel(new BorderLayout(4, 4));
        south.setOpaque(false);
        south.add(new JLabel("Optional English refinement (updates 'en' if provided)"), BorderLayout.NORTH);
        south.add(new JScrollPane(englishRefineArea), BorderLayout.CENTER);
        south.add(actions, BorderLayout.SOUTH);

        root.add(hint, BorderLayout.NORTH);
        root.add(new JScrollPane(translatorTable), BorderLayout.CENTER);
        root.add(south, BorderLayout.SOUTH);

        reloadTranslatorTable();
        return root;
    }

    private String getSelectedQueueId() {
        int r = translatorTable.getSelectedRow();
        if (r < 0) {
            return null;
        }
        Object v = translatorModel.getValueAt(r, 0);
        return v == null ? null : v.toString();
    }

    private void loadSelectedPendingIntoEditor() {
        int r = translatorTable.getSelectedRow();
        if (r < 0) {
            englishRefineArea.setText("");
            return;
        }
        String status = String.valueOf(translatorModel.getValueAt(r, 4));
        if (!"PENDING".equalsIgnoreCase(status) && !"IN_REVIEW".equalsIgnoreCase(status)) {
            englishRefineArea.setText("");
            return;
        }
        String preview = String.valueOf(translatorModel.getValueAt(r, 5));
        englishRefineArea.setText("");
        englishRefineArea.setToolTipText(preview);
    }

    private void reloadTranslatorTable() {
        translatorModel.setRowCount(0);
        List<Feature4Service.PendingContribution> rows = service.getAllQueueForDashboard();
        for (Feature4Service.PendingContribution p : rows) {
            String prev = p.getText();
            if (prev.length() > 80) {
                prev = prev.substring(0, 80) + "...";
            }
            translatorModel.addRow(new Object[]{
                p.getId(),
                p.getItemName(),
                p.getField(),
                p.getSourceLang().toUpperCase(),
                p.getStatus(),
                prev
            });
        }
    }

}
