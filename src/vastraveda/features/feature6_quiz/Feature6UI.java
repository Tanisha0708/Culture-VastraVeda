package vastraveda.features.feature6_quiz;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature6UI extends BaseUI implements Feature {

    private final Feature6Service service = new Feature6Service();
    private int contributions = 0;
    private JLabel scoreLabel;
    private JPanel badgePanel;

    public Feature6UI() {
        super("Heritage Badges");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🏅 Clothing Heritage Badges", "Earn recognition as you learn and contribute"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        top.setBackground(new Color(245, 235, 215));
        top.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        top.add(new JLabel("Profile: Guest Contributor"));
        scoreLabel = new JLabel("Contributions: 0");
        scoreLabel.setFont(FONT_LABEL);
        top.add(scoreLabel);
        JButton add = createStyledButton("+ Simulate contribution", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        add.addActionListener(e -> {
            contributions++;
            scoreLabel.setText("Contributions: " + contributions);
            refreshBadges();
            if (contributions == 1 || contributions == 5 || contributions == 10 || contributions == 25 || contributions == 50) {
                JOptionPane.showMessageDialog(this, "New milestone reached — check your badges!", "Badge", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        top.add(add);
        add(top, BorderLayout.NORTH);

        badgePanel = new JPanel();
        badgePanel.setLayout(new BoxLayout(badgePanel, BoxLayout.Y_AXIS));
        badgePanel.setBackground(COLOR_BG);
        badgePanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));

        add(new JScrollPane(badgePanel), BorderLayout.CENTER);
        refreshBadges();
    }

    private void refreshBadges() {
        badgePanel.removeAll();
        JLabel hint = new JLabel("<html><body style='width:520px'>Badges unlock when your contribution count crosses each threshold. " +
            "In a full app this would sync with Issue Tracker, Debates, and Dataset fixes.</body></html>");
        hint.setFont(FONT_SMALL);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgePanel.add(hint);
        badgePanel.add(Box.createVerticalStrut(12));

        List<Feature6Service.Badge> earned = service.earnedBadges(contributions);
        JLabel sec = new JLabel("Your badges (" + earned.size() + " / " + service.getCatalog().size() + "):");
        sec.setFont(FONT_LABEL);
        sec.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgePanel.add(sec);
        badgePanel.add(Box.createVerticalStrut(8));

        for (Feature6Service.Badge b : service.getCatalog()) {
            boolean on = contributions >= b.minContributions;
            JPanel row = createCard();
            row.setLayout(new BorderLayout(8, 4));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, on ? COLOR_SECONDARY : COLOR_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            JLabel left = new JLabel(b.icon + "  " + b.name + (on ? "  ✓" : "  (locked)"));
            left.setFont(new Font("Segoe UI", Font.BOLD, 13));
            left.setForeground(on ? COLOR_TEXT : Color.GRAY);
            JLabel desc = new JLabel("<html><body style='width:480px'>" + b.description + " — unlock at " + b.minContributions + " contributions.</body></html>");
            desc.setFont(FONT_SMALL);
            row.add(left, BorderLayout.NORTH);
            row.add(desc, BorderLayout.CENTER);
            badgePanel.add(row);
            badgePanel.add(Box.createVerticalStrut(6));
        }
        badgePanel.revalidate();
        badgePanel.repaint();
    }
}
