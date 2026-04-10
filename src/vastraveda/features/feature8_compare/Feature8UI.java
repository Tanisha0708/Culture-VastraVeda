package vastraveda.features.feature8_compare;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Feature8UI extends BaseUI implements Feature {

    private final Feature8Service service = new Feature8Service();
    private JList<String> threadList;
    private DefaultListModel<String> threadModel;
    private JTextArea discussionArea;
    private List<Feature8Service.Thread> threadRefs = new ArrayList<>();
    private JTextField replyField;
    private JCheckBox modCheck;

    public Feature8UI() {
        super("Debate Board");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("💬 Debate & Dispute Board", "Discuss origins — moderators can close threads"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(new Color(245, 235, 215));
        JButton start = createStyledButton("New thread", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        start.addActionListener(e -> newThreadDialog());
        top.add(start);
        add(top, BorderLayout.NORTH);

        threadModel = new DefaultListModel<>();
        threadList = new JList<>(threadModel);
        threadList.setFont(FONT_BODY);
        threadList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showThread();
            }
        });
        JScrollPane left = new JScrollPane(threadList);
        left.setBorder(BorderFactory.createTitledBorder("Threads"));

        discussionArea = createTextArea("");
        discussionArea.setFont(FONT_BODY);
        JScrollPane mid = new JScrollPane(discussionArea);
        mid.setBorder(BorderFactory.createTitledBorder("Discussion"));

        JPanel bottom = new JPanel(new BorderLayout(4, 4));
        bottom.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        replyField = new JTextField();
        modCheck = new JCheckBox("Post as moderator");
        JButton send = createStyledButton("Reply", COLOR_BORDER, COLOR_TEXT);
        send.addActionListener(e -> sendReply());
        JButton close = createStyledButton("Close thread (mod)", COLOR_ACCENT, COLOR_TEXT_LIGHT);
        close.addActionListener(e -> closeSelected());
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(modCheck);
        row.add(send);
        row.add(close);
        bottom.add(replyField, BorderLayout.CENTER);
        bottom.add(row, BorderLayout.EAST);

        JPanel rightCol = new JPanel(new BorderLayout());
        rightCol.add(mid, BorderLayout.CENTER);
        rightCol.add(bottom, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, rightCol);
        split.setResizeWeight(0.28);
        add(split, BorderLayout.CENTER);

        refreshThreadList();
    }

    private void refreshThreadList() {
        threadModel.clear();
        threadRefs.clear();
        for (Feature8Service.Thread t : service.getThreads()) {
            String line = (t.closed ? "[CLOSED] " : "") + t.garmentName + ": " + t.title;
            threadModel.addElement(line);
            threadRefs.add(t);
        }
    }

    private Feature8Service.Thread selectedThread() {
        int i = threadList.getSelectedIndex();
        if (i < 0 || i >= threadRefs.size()) {
            return null;
        }
        return threadRefs.get(i);
    }

    private void showThread() {
        Feature8Service.Thread t = selectedThread();
        if (t == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Garment: ").append(t.garmentName).append("\n");
        if (t.closed && t.resolutionNote != null && !t.resolutionNote.isEmpty()) {
            sb.append("Resolution: ").append(t.resolutionNote).append("\n");
        }
        sb.append("\n---\n");
        for (Feature8Service.Post p : t.posts) {
            sb.append(p.moderator ? "[MOD] " : "").append(p.author).append(": ").append(p.text).append("\n\n");
        }
        discussionArea.setText(sb.toString());
    }

    private void sendReply() {
        Feature8Service.Thread t = selectedThread();
        if (t == null || t.closed) {
            return;
        }
        String text = replyField.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        String author = modCheck.isSelected() ? "Moderator" : "Contributor";
        service.addReply(t, author, text, modCheck.isSelected());
        replyField.setText("");
        showThread();
    }

    private void closeSelected() {
        Feature8Service.Thread t = selectedThread();
        if (t == null || t.closed) {
            return;
        }
        String res = JOptionPane.showInputDialog(this, "Resolution note:", "Close thread", JOptionPane.PLAIN_MESSAGE);
        if (res == null) {
            return;
        }
        service.closeThread(t, res);
        refreshThreadList();
        showThread();
    }

    private void newThreadDialog() {
        List<String> names = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            names.add(item.getName());
        }
        JComboBox<String> g = new JComboBox<>(names.toArray(new String[0]));
        JTextField title = new JTextField(24);
        JTextArea body = new JTextArea(4, 24);
        JPanel p = new JPanel(new GridLayout(0, 1, 4, 4));
        p.add(new JLabel("Garment:"));
        p.add(g);
        p.add(new JLabel("Title:"));
        p.add(title);
        p.add(new JLabel("Opening post:"));
        p.add(new JScrollPane(body));
        int ok = JOptionPane.showConfirmDialog(this, p, "New debate thread", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION && !title.getText().trim().isEmpty()) {
            service.addThread((String) g.getSelectedItem(), title.getText().trim(), body.getText().trim());
            refreshThreadList();
        }
    }
}
