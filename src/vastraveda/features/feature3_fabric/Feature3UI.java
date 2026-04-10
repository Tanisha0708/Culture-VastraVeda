package vastraveda.features.feature3_fabric;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.utils.FilterUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  FEATURE 3 — Fabric Explorer                         ║
 * ║  CONTRIBUTOR: Your Name                              ║
 * ║  Modify ONLY this file and Feature3Service.java      ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Explore fabrics used in Indian clothing.
 */
public class Feature3UI extends BaseUI implements Feature {

    private JPanel detailPanel;
    private final Feature3Service service = new Feature3Service();

    private static final Map<String, String[]> FABRIC_INFO = new LinkedHashMap<>();
    static {
        FABRIC_INFO.put("Silk",     new String[]{"🐛", "Soft, lustrous natural fiber. Used in premium sarees and sherwanis.", "#FFF0D0"});
        FABRIC_INFO.put("Cotton",   new String[]{"🌾", "Breathable and versatile. India's most widely used fabric.", "#F0FFF0"});
        FABRIC_INFO.put("Wool",     new String[]{"🐑", "Warm fiber from sheep or goats. Used in shawls and winter wear.", "#F0F0FF"});
        FABRIC_INFO.put("Linen",    new String[]{"🌿", "Crisp natural fiber from flax. Lightweight and durable.", "#FFF8F0"});
        FABRIC_INFO.put("Brocade",  new String[]{"✨", "Richly decorative woven fabric with raised patterns, often with zari.", "#FFF0F8"});
        FABRIC_INFO.put("Pashmina", new String[]{"❄️", "Ultra-fine luxury wool from Changthangi goats of Kashmir.", "#F0F8FF"});
    }

    public Feature3UI() {
        super("Fabric Substitution Guide");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));
        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(createHeader("🧵  Fabric Substitution Guide",
            "Explore fabrics · find similar options · suggest & moderate alternatives"), BorderLayout.CENTER);
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        topBar.setBackground(COLOR_BG_DARK);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 24, 10, 24));
        JButton modBtn = createStyledButton("Moderator — pending suggestions", COLOR_SECONDARY, COLOR_BG_DARK);
        modBtn.addActionListener(e -> openModeratorDialog());
        topBar.add(modBtn);
        north.add(topBar, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            buildFabricList(), buildDetailArea());
        split.setDividerLocation(240);
        split.setDividerSize(6);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
    }

    private JPanel buildFabricList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 238, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        JLabel title = new JLabel("Select a Fabric");
        title.setFont(FONT_LABEL);
        title.setForeground(COLOR_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 4, 10, 0));
        panel.add(title);

        for (Map.Entry<String, String[]> entry : FABRIC_INFO.entrySet()) {
            String fabric = entry.getKey();
            String[] info = entry.getValue();
            Color bg = Color.decode(info[2]);

            JButton btn = new JButton(info[0] + "  " + fabric);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
            btn.setBackground(bg);
            btn.setForeground(COLOR_TEXT);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            btn.addActionListener(e -> showFabric(fabric, info));
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 6)));
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JScrollPane buildDetailArea() {
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(COLOR_BG);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel hint = new JLabel("← Choose a fabric from the left panel");
        hint.setFont(FONT_SUBTITLE);
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(hint);

        JScrollPane scroll = new JScrollPane(detailPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_BG);
        return scroll;
    }

    private void showFabric(String fabric, String[] info) {
        detailPanel.removeAll();
        // Fabric header
        JLabel icon = new JLabel(info[0], JLabel.CENTER);
        icon.setFont(new Font("Serif", Font.PLAIN, 48));
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(fabric);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 26));
        nameLabel.setForeground(COLOR_PRIMARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea desc = createTextArea(info[1]);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setBackground(Color.decode(info[2]));
        desc.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        desc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Care tip
        String careTip = service.getCareTip(fabric);
        JLabel careLabel = new JLabel("🧺 Care: " + careTip);
        careLabel.setFont(FONT_SMALL);
        careLabel.setForeground(new Color(100, 80, 50));
        careLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton similarBtn = createStyledButton("Find similar fabric", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        similarBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        similarBtn.addActionListener(e -> openSubstitutionModal(fabric));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(COLOR_BORDER);

        JLabel clothesTitle = new JLabel("Garments using " + fabric);
        clothesTitle.setFont(FONT_LABEL);
        clothesTitle.setForeground(COLOR_PRIMARY);
        clothesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailPanel.add(icon);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        detailPanel.add(nameLabel);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailPanel.add(desc);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        detailPanel.add(careLabel);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        detailPanel.add(similarBtn);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        detailPanel.add(sep);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailPanel.add(clothesTitle);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        List<ClothingItem> items = FilterUtils.filterByFabric(fabric);
        if (items.isEmpty()) {
            JLabel none = new JLabel("No items found for this fabric.");
            none.setFont(FONT_BODY);
            none.setForeground(Color.GRAY);
            none.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailPanel.add(none);
        } else {
            Color accent = Color.decode(info[2]).darker();
            for (ClothingItem item : items) {
                detailPanel.add(buildItemRow(item, accent));
                detailPanel.add(Box.createRigidArea(new Dimension(0, 6)));
            }
        }

        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private JPanel buildItemRow(ClothingItem item, Color accent) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(COLOR_CARD);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(item.getImageIcon());
        icon.setFont(new Font("Serif", Font.PLAIN, 22));

        JPanel text = new JPanel(new GridLayout(2, 1, 2, 2));
        text.setOpaque(false);

        JLabel name = new JLabel(item.getName() + " · " + item.getRegion());
        name.setFont(new Font("SansSerif", Font.BOLD, 12));
        JLabel meta = new JLabel(item.getOccasion() + " · " + item.getGender());
        meta.setFont(FONT_SMALL);
        meta.setForeground(Color.GRAY);

        text.add(name);
        text.add(meta);

        row.add(icon, BorderLayout.WEST);
        row.add(text, BorderLayout.CENTER);
        return row;
    }

    private void openSubstitutionModal(String fabric) {
        JDialog dlg = new JDialog(this, "Similar fabrics — " + fabric, true);
        dlg.setLayout(new BorderLayout(8, 8));
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        body.setBackground(COLOR_BG);

        java.util.List<Feature3Service.FabricAlternative> alts = service.getAlternativesForFabric(fabric);
        if (alts.isEmpty()) {
            body.add(new JLabel("<html>No curated alternatives for <b>" + fabric + "</b>.<br>Add one below.</html>"));
        } else {
            for (Feature3Service.FabricAlternative a : alts) {
                JTextArea block = new JTextArea(
                    a.getFabricName() + "\n" + a.getExplanation()
                        + (a.getContextHint().isEmpty() ? "" : "\nContext: " + a.getContextHint()));
                block.setEditable(false);
                block.setLineWrap(true);
                block.setWrapStyleWord(true);
                block.setFont(FONT_BODY);
                block.setBackground(COLOR_CARD);
                block.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                block.setAlignmentX(Component.LEFT_ALIGNMENT);
                block.setMaximumSize(new Dimension(480, 120));
                body.add(block);
                body.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = 0;
        gc.gridy = 0;
        form.add(new JLabel("Suggest fabric:"), gc);
        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        JTextField suggestFabric = new JTextField(18);
        form.add(suggestFabric, gc);
        gc.gridy++;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0;
        form.add(new JLabel("Why it works:"), gc);
        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        JTextArea why = new JTextArea(3, 18);
        why.setLineWrap(true);
        why.setWrapStyleWord(true);
        form.add(new JScrollPane(why), gc);
        gc.gridy++;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Note (optional):"), gc);
        gc.gridx = 1;
        JTextField note = new JTextField(18);
        form.add(note, gc);

        JButton submit = createStyledButton("Submit for review", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        submit.addActionListener(ev -> {
            try {
                service.submitSuggestion(fabric, suggestFabric.getText(), why.getText(), note.getText());
                JOptionPane.showMessageDialog(dlg, "Suggestion saved as pending for moderators.", "Thanks", JOptionPane.INFORMATION_MESSAGE);
                suggestFabric.setText("");
                why.setText("");
                note.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setOpaque(false);
        south.add(submit);

        body.add(Box.createRigidArea(new Dimension(0, 8)));
        body.add(new JLabel("Suggest a new alternative"));
        body.add(form);
        body.add(south);

        dlg.add(new JScrollPane(body), BorderLayout.CENTER);
        JButton close = createStyledButton("Close", COLOR_BORDER, COLOR_TEXT);
        close.addActionListener(ev -> dlg.dispose());
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        foot.add(close);
        dlg.add(foot, BorderLayout.SOUTH);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setMinimumSize(new Dimension(520, 420));
        dlg.setVisible(true);
    }

    private void openModeratorDialog() {
        JDialog dlg = new JDialog(this, "Moderator — fabric substitutions", true);
        dlg.setLayout(new BorderLayout(8, 8));
        DefaultListModel<String> model = new DefaultListModel<>();
        java.util.Map<String, Feature3Service.PendingSubstitution> map = new java.util.LinkedHashMap<>();
        Runnable reload = () -> {
            model.clear();
            map.clear();
            for (Feature3Service.PendingSubstitution p : service.getPendingSuggestions()) {
                String line = p.getId() + " | base: " + p.getBaseFabric() + " → " + p.getSuggestedFabric();
                map.put(line, p);
                model.addElement(line);
            }
        };
        reload.run();
        JList<String> list = new JList<>(model);
        list.setFont(FONT_SMALL);
        JTextArea detail = new JTextArea(6, 40);
        detail.setEditable(false);
        detail.setFont(FONT_BODY);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = list.getSelectedValue();
                Feature3Service.PendingSubstitution p = sel == null ? null : map.get(sel);
                if (p != null) {
                    detail.setText("Explanation:\n" + p.getExplanation() + "\n\nNote:\n" + p.getSubmitterNote());
                } else {
                    detail.setText("");
                }
            }
        });
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton approve = createStyledButton("Approve (add to guide)", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        JButton reject = createStyledButton("Reject (discard)", new Color(140, 50, 40), COLOR_TEXT_LIGHT);
        approve.addActionListener(e -> {
            String sel = list.getSelectedValue();
            Feature3Service.PendingSubstitution p = sel == null ? null : map.get(sel);
            if (p == null) {
                return;
            }
            service.approveSuggestion(p.getId());
            reload.run();
            detail.setText("");
        });
        reject.addActionListener(e -> {
            String sel = list.getSelectedValue();
            Feature3Service.PendingSubstitution p = sel == null ? null : map.get(sel);
            if (p == null) {
                return;
            }
            service.rejectSuggestion(p.getId());
            reload.run();
            detail.setText("");
        });
        btns.add(approve);
        btns.add(reject);
        JPanel center = new JPanel(new BorderLayout());
        center.add(new JScrollPane(list), BorderLayout.NORTH);
        center.add(new JScrollPane(detail), BorderLayout.CENTER);
        center.add(btns, BorderLayout.SOUTH);
        dlg.add(center, BorderLayout.CENTER);
        JButton close = createStyledButton("Close", COLOR_BORDER, COLOR_TEXT);
        close.addActionListener(e -> dlg.dispose());
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        foot.add(close);
        dlg.add(foot, BorderLayout.SOUTH);
        dlg.setSize(520, 420);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
}
