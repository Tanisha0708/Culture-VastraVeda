package vastraveda.features.feature2_map;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Feature 2 — Cultural tagging: browse with pill filters, admin CRUD + assign tags.
 */
public class Feature2UI extends BaseUI implements Feature {

    private final Feature2Service service = new Feature2Service();
    private JPanel pillBar;
    private JPanel cardsPanel;
    private String selectedTagId;
    private JList<Feature2Service.CulturalTag> adminTagList;
    private DefaultListModel<Feature2Service.CulturalTag> adminTagModel;
    private JComboBox<String> assignItemCombo;
    private JList<Feature2Service.CulturalTag> assignTagList;

    public Feature2UI() {
        super("Cultural Tagging");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));
        add(createHeader("🏷  Cultural Tagging System",
            "Pills on cards · click to filter · admin manages tags & assignments"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_LABEL);
        tabs.addTab("Browse & filter", buildBrowseTab());
        tabs.addTab("Admin — tags & items", buildAdminTab());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildBrowseTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(8, 12, 12, 12));

        JLabel hint = new JLabel("Click a tag pill below to filter garments. Click \"All\" to clear.");
        hint.setFont(FONT_SMALL);
        hint.setForeground(new Color(100, 80, 50));
        root.add(hint, BorderLayout.NORTH);

        pillBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        pillBar.setOpaque(false);
        JScrollPane pillScroll = new JScrollPane(pillBar);
        pillScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pillScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pillScroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        pillScroll.getViewport().setBackground(new Color(250, 242, 228));
        pillScroll.setPreferredSize(new Dimension(800, 52));

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(COLOR_BG);

        JScrollPane scroll = new JScrollPane(cardsPanel);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scroll.getViewport().setBackground(COLOR_BG);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(pillScroll, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        refreshPillBarAndCards();
        return root;
    }

    private void refreshPillBarAndCards() {
        pillBar.removeAll();
        pillBar.add(makeFilterPill("All", null, selectedTagId == null));

        for (Feature2Service.CulturalTag t : service.getAllTagsSorted()) {
            pillBar.add(makeFilterPill(t.getName(), t.getId(), t.getId().equals(selectedTagId)));
        }
        pillBar.revalidate();
        pillBar.repaint();

        cardsPanel.removeAll();
        List<ClothingItem> items = service.getAllItemsFilteredByTagId(selectedTagId);
        Color accent = COLOR_PRIMARY;
        for (ClothingItem item : items) {
            cardsPanel.add(buildItemCard(item, accent));
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if (items.isEmpty()) {
            JLabel empty = new JLabel("No items match this tag.", JLabel.CENTER);
            empty.setFont(FONT_BODY);
            empty.setForeground(Color.GRAY);
            cardsPanel.add(empty);
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JButton makeFilterPill(String label, String tagId, boolean active) {
        JButton b = new JButton(label);
        b.setFont(FONT_SMALL);
        b.setFocusPainted(false);
        if (active) {
            b.setBackground(COLOR_PRIMARY);
            b.setForeground(COLOR_TEXT_LIGHT);
        } else {
            b.setBackground(new Color(230, 215, 190));
            b.setForeground(COLOR_TEXT);
        }
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(active ? COLOR_BG_DARK : COLOR_BORDER),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> {
            selectedTagId = tagId;
            refreshPillBarAndCards();
        });
        return b;
    }

    private JPanel buildItemCard(ClothingItem item, Color accent) {
        JPanel card = new JPanel(new BorderLayout(8, 6));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(item.getImageIcon(), JLabel.CENTER);
        icon.setFont(new Font("Serif", Font.PLAIN, 28));

        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("Serif", Font.BOLD, 14));
        name.setForeground(COLOR_TEXT);

        JLabel meta = new JLabel(item.getRegion() + " · " + item.getFabricType() + " · " + item.getOccasion());
        meta.setFont(FONT_SMALL);
        meta.setForeground(Color.GRAY);

        JLabel desc = new JLabel("<html><body style='width:420px'>" + service.truncate(item.getDescription(), 120) + "</body></html>");
        desc.setFont(FONT_SMALL);
        desc.setForeground(new Color(80, 60, 40));

        textCol.add(name);
        textCol.add(meta);
        textCol.add(Box.createRigidArea(new Dimension(0, 4)));
        textCol.add(desc);

        JPanel pills = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        pills.setOpaque(false);
        for (Feature2Service.CulturalTag t : tagsForItem(item.getName())) {
            pills.add(makeCardPill(t, id -> {
                selectedTagId = id;
                refreshPillBarAndCards();
            }));
        }
        if (pills.getComponentCount() == 0) {
            JLabel none = new JLabel("(no tags — assign in Admin)");
            none.setFont(FONT_SMALL);
            none.setForeground(Color.GRAY);
            pills.add(none);
        }

        JPanel east = new JPanel(new BorderLayout());
        east.setOpaque(false);
        east.add(textCol, BorderLayout.NORTH);
        east.add(pills, BorderLayout.SOUTH);

        card.add(icon, BorderLayout.WEST);
        card.add(east, BorderLayout.CENTER);
        return card;
    }

    private List<Feature2Service.CulturalTag> tagsForItem(String itemName) {
        List<Feature2Service.CulturalTag> list = new ArrayList<>();
        for (String id : service.getTagIdsForItemName(itemName)) {
            Feature2Service.CulturalTag t = service.getTagById(id);
            if (t != null) {
                list.add(t);
            }
        }
        list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return list;
    }

    private JButton makeCardPill(Feature2Service.CulturalTag t, Consumer<String> onPickTag) {
        JButton p = new JButton(t.getName());
        p.setFont(new Font("SansSerif", Font.PLAIN, 10));
        p.setBackground(new Color(255, 236, 200));
        p.setForeground(COLOR_TEXT);
        p.setFocusPainted(false);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SECONDARY),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addActionListener(e -> onPickTag.accept(t.getId()));
        return p;
    }

    private JPanel buildAdminTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBackground(COLOR_BG);
        root.setBorder(new EmptyBorder(8, 12, 12, 12));

        JPanel left = new JPanel(new BorderLayout(6, 6));
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(280, 400));

        adminTagModel = new DefaultListModel<>();
        reloadAdminTagModel();
        adminTagList = new JList<>(adminTagModel);
        adminTagList.setFont(FONT_BODY);
        adminTagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel tagBtns = new JPanel(new GridLayout(2, 2, 4, 4));
        tagBtns.setOpaque(false);
        JButton add = createStyledButton("New tag", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        JButton edit = createStyledButton("Rename", COLOR_BORDER, COLOR_TEXT);
        JButton del = createStyledButton("Delete", new Color(160, 60, 40), COLOR_TEXT_LIGHT);
        add.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Tag name:", "New cultural tag", JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                return;
            }
            try {
                service.createTag(name);
                reloadAdminTagModel();
                refreshAssignTagListModel();
                refreshPillBarAndCards();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Tag", JOptionPane.WARNING_MESSAGE);
            }
        });
        edit.addActionListener(e -> {
            Feature2Service.CulturalTag t = adminTagList.getSelectedValue();
            if (t == null) {
                return;
            }
            String name = JOptionPane.showInputDialog(this, "New name:", t.getName());
            if (name == null) {
                return;
            }
            try {
                service.updateTag(t.getId(), name);
                reloadAdminTagModel();
                refreshAssignTagListModel();
                refreshPillBarAndCards();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Tag", JOptionPane.WARNING_MESSAGE);
            }
        });
        del.addActionListener(e -> {
            Feature2Service.CulturalTag t = adminTagList.getSelectedValue();
            if (t == null) {
                return;
            }
            int ok = JOptionPane.showConfirmDialog(this,
                "Delete tag \"" + t.getName() + "\" from all items?",
                "Confirm", JOptionPane.OK_CANCEL_OPTION);
            if (ok == JOptionPane.OK_OPTION) {
                service.deleteTag(t.getId());
                reloadAdminTagModel();
                refreshAssignTagListModel();
                if (selectedTagId != null && selectedTagId.equals(t.getId())) {
                    selectedTagId = null;
                }
                refreshPillBarAndCards();
            }
        });
        tagBtns.add(add);
        tagBtns.add(edit);
        tagBtns.add(del);

        left.add(new JLabel("Tags (admin)"), BorderLayout.NORTH);
        left.add(new JScrollPane(adminTagList), BorderLayout.CENTER);
        left.add(tagBtns, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout(6, 6));
        right.setOpaque(false);

        assignItemCombo = new JComboBox<>();
        reloadItemCombo();

        assignTagList = new JList<>();
        assignTagList.setVisibleRowCount(8);
        assignTagList.setFont(FONT_BODY);
        refreshAssignTagListModel();
        assignTagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JButton applyAssign = createStyledButton("Save tags for item", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        applyAssign.addActionListener(e -> {
            String item = (String) assignItemCombo.getSelectedItem();
            if (item == null) {
                return;
            }
            List<Feature2Service.CulturalTag> sel = assignTagList.getSelectedValuesList();
            Set<String> ids = new LinkedHashSet<>();
            for (Feature2Service.CulturalTag t : sel) {
                ids.add(t.getId());
            }
            try {
                service.setTagIdsForItem(item, ids);
                refreshPillBarAndCards();
                JOptionPane.showMessageDialog(this, "Tags updated for " + item + ".", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton syncSelection = createStyledButton("Load item's tags", COLOR_BORDER, COLOR_TEXT);
        syncSelection.addActionListener(e -> {
            String item = (String) assignItemCombo.getSelectedItem();
            if (item == null) {
                return;
            }
            Set<String> ids = service.getTagIdsForItemName(item);
            int n = assignTagList.getModel().getSize();
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Feature2Service.CulturalTag t = assignTagList.getModel().getElementAt(i);
                if (ids.contains(t.getId())) {
                    indices.add(i);
                }
            }
            int[] arr = indices.stream().mapToInt(Integer::intValue).toArray();
            assignTagList.setSelectedIndices(arr);
        });

        JPanel assignTop = new JPanel(new BorderLayout(4, 4));
        assignTop.setOpaque(false);
        assignTop.add(new JLabel("Clothing item"), BorderLayout.NORTH);
        assignTop.add(assignItemCombo, BorderLayout.CENTER);

        JPanel assignBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        assignBtns.setOpaque(false);
        assignBtns.add(syncSelection);
        assignBtns.add(applyAssign);

        right.add(assignTop, BorderLayout.NORTH);
        right.add(new JScrollPane(assignTagList), BorderLayout.CENTER);
        right.add(assignBtns, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.35);
        split.setDividerSize(6);
        split.setBorder(null);
        root.add(split, BorderLayout.CENTER);
        return root;
    }

    private void reloadAdminTagModel() {
        adminTagModel.clear();
        for (Feature2Service.CulturalTag t : service.getAllTagsSorted()) {
            adminTagModel.addElement(t);
        }
    }

    private void reloadItemCombo() {
        assignItemCombo.removeAllItems();
        for (ClothingItem it : new ArrayList<>(DataStore.getAllItems())) {
            assignItemCombo.addItem(it.getName());
        }
    }

    private void refreshAssignTagListModel() {
        DefaultListModel<Feature2Service.CulturalTag> m = new DefaultListModel<>();
        for (Feature2Service.CulturalTag t : service.getAllTagsSorted()) {
            m.addElement(t);
        }
        assignTagList.setModel(m);
    }
}
