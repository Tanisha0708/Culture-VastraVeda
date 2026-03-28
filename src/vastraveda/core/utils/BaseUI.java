package vastraveda.core.utils;

import javax.swing.*;
import java.awt.*;

/**
 * 🔒 CORE BASE CLASS — DO NOT MODIFY
 * 
 * Base JFrame class that all feature UIs should extend.
 * Provides consistent theming, sizing, and utility methods.
 * 
 * CONTRIBUTORS: Extend this class in your FeatureXUI.java
 * 
 * Example:
 *   public class Feature1UI extends BaseUI implements Feature { ... }
 */
public abstract class BaseUI extends JFrame {

    // ─── Shared Color Palette ───────────────────────────────────────────────
    public static final Color COLOR_PRIMARY     = new Color(139, 69, 19);   // Sandalwood Brown
    public static final Color COLOR_SECONDARY   = new Color(212, 160, 23);  // Turmeric Gold
    public static final Color COLOR_ACCENT      = new Color(180, 0, 0);     // Sindoor Red
    public static final Color COLOR_BG          = new Color(255, 248, 235); // Cream
    public static final Color COLOR_BG_DARK     = new Color(45, 25, 10);    // Dark Walnut
    public static final Color COLOR_TEXT        = new Color(40, 20, 5);     // Deep Brown
    public static final Color COLOR_TEXT_LIGHT  = new Color(255, 248, 235); // Cream text
    public static final Color COLOR_CARD        = new Color(255, 255, 245); // Off-white card
    public static final Color COLOR_BORDER      = new Color(200, 160, 100); // Warm border

    // ─── Shared Fonts ───────────────────────────────────────────────────────
    public static final Font FONT_TITLE    = new Font("Serif", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Serif", Font.ITALIC, 15);
    public static final Font FONT_BODY     = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON   = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_SMALL    = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_LABEL    = new Font("SansSerif", Font.BOLD, 12);

    public BaseUI(String title) {
        super("VastraVeda — " + title);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BG);
    }

    /** Creates a styled header panel with a title and subtitle. */
    protected JPanel createHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_BG_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_SECONDARY);

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(FONT_SUBTITLE);
        subLabel.setForeground(new Color(220, 200, 160));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(subLabel);

        header.add(textPanel, BorderLayout.WEST);
        return header;
    }

    /** Creates a styled action button. */
    protected JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    /** Creates a card-style panel with rounded border. */
    protected JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        return card;
    }

    /** Shows an information dialog. */
    protected void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title,
            JOptionPane.INFORMATION_MESSAGE);
    }

    /** Creates a scrollable text area. */
    protected JTextArea createTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(FONT_BODY);
        area.setForeground(COLOR_TEXT);
        area.setBackground(COLOR_BG);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }

    /** Creates a styled combo box. */
    protected <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(FONT_BODY);
        combo.setBackground(Color.WHITE);
        return combo;
    }
}
