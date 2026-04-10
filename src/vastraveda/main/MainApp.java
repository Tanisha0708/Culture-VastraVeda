package vastraveda.main;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

// Feature imports — Contributors: add your feature import here
import vastraveda.features.feature1_directory.Feature1UI;
import vastraveda.features.feature2_map.Feature2UI;
import vastraveda.features.feature3_fabric.Feature3UI;
import vastraveda.features.feature4_calendar.Feature4UI;
import vastraveda.features.feature5_occasions.Feature5UI;
import vastraveda.features.feature6_quiz.Feature6UI;
import vastraveda.features.feature7_timeline.Feature7UI;
import vastraveda.features.feature8_compare.Feature8UI;
import vastraveda.features.feature9_gallery.Feature9UI;
import vastraveda.features.feature10_stories.Feature10UI;
import vastraveda.features.feature11_care.Feature11UI;
import vastraveda.features.feature12_glossary.Feature12UI;
import vastraveda.features.feature13_designers.Feature13UI;
import vastraveda.features.feature14_states.Feature14UI;
import vastraveda.features.feature15_trends.Feature15UI;
import vastraveda.features.feature16_fabricorigins.Feature16UI;
import vastraveda.features.feature17_versioncontrol.Feature17UI;
import vastraveda.features.feature18_outfitsuggest.Feature18UI;
import vastraveda.features.feature19_fabriccamera.Feature19UI;
import vastraveda.features.feature20_openapi.Feature20UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║              VastraVeda — Main Application Launcher              ║
 * ║                                                                  ║
 * ║  🔒 CONTRIBUTORS: DO NOT add feature logic inside this file.    ║
 * ║     Only update the FEATURE_META array and the switch in        ║
 * ║     openFeature() when adding a new feature.                    ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
public class MainApp extends JFrame {

    // ── Feature Metadata ────────────────────────────────────────────
    // Format: { "Feature Number", "Icon", "Title", "Short description", "Badge color hex" }
    private static final String[][] FEATURE_META = {
        { "1",  "🗂",  "Regional Directory",   "Browse & filter all garments",        "#8B4513" },
        { "2",  "🗺",  "Clothing Map",          "Click states to explore regions",     "#C47A1B" },
        { "3",  "🧵",  "Fabric Explorer",       "Deep dive into Indian textiles",      "#2E7D32" },
        { "4",  "🗓",  "Festival Calendar",     "Clothing for each festival",          "#1565C0" },
        { "5",  "🎊",  "Occasion Guide",        "Dress right for every event",         "#6A1B9A" },
        { "6",  "❓",  "Clothing Quiz",         "Test your textile knowledge",         "#D84315" },
        { "7",  "📜",  "Historical Timeline",   "Clothing through Indian history",     "#4E342E" },
        { "8",  "⚖️",  "Compare Outfits",       "Side-by-side garment comparison",    "#00695C" },
        { "9",  "🖼",  "Visual Gallery",        "Browse beautiful garment photos",     "#AD1457" },
        { "10", "🏆",  "Contributor Leaderboard", "Rank contributors — filters & scores", "#37474F" },
        { "11", "🧺",  "Care & Maintenance",    "Preserve your traditional wear",      "#558B2F" },
        { "12", "📚",  "Clothing Version Control", "History, compare, rollback snapshots", "#0277BD" },
        { "13", "✂️",  "Featured Designers",   "Artisans keeping craft alive",         "#6D4C41" },
        { "14", "🏛",  "State Profiles",        "Clothing of every Indian state",      "#283593" },
        { "15", "🌟",  "Modern Trends",         "Traditional meets contemporary",      "#880E4F" },
        { "16", "🧭",  "Fabric Origins Tracker","Trace origin & spread across India",  "#1A4D8F" },
        { "17", "📖",  "Textile Glossary",       "Terms A–Z search & browse",           "#5D4037" },
        { "18", "✨",  "AI Outfit Suggestions",   "Similar outfits by attributes",       "#7B1FA2" },
        { "19", "📷",  "Fabric Identifier",       "Image + mock fabric classification",  "#00838F" },
        { "20", "🌐",  "Open Clothing API",       "REST + API key + docs",               "#1565C0" },
    };

    public MainApp() {
        super("VastraVeda — Explore Traditional Indian Clothing");
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BaseUI.COLOR_BG_DARK);

        add(buildHeroPanel(), BorderLayout.NORTH);
        add(buildFeatureGrid(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ── Hero Banner ──────────────────────────────────────────────────
    private JPanel buildHeroPanel() {
        JPanel hero = new JPanel(new BorderLayout());
        hero.setBackground(BaseUI.COLOR_BG_DARK);
        hero.setBorder(BorderFactory.createEmptyBorder(28, 36, 20, 36));

        JPanel textCol = new JPanel(new GridLayout(3, 1, 4, 4));
        textCol.setOpaque(false);

        JLabel appName = new JLabel("🥻  VastraVeda");
        appName.setFont(new Font("Serif", Font.BOLD, 32));
        appName.setForeground(BaseUI.COLOR_SECONDARY);

        JLabel tagline = new JLabel("वस्त्र-वेद  ·  The Encyclopedia of Indian Traditional Clothing");
        tagline.setFont(new Font("Serif", Font.ITALIC, 14));
        tagline.setForeground(new Color(200, 180, 140));

        JLabel subtitle = new JLabel("Select a feature to explore");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(160, 140, 110));

        textCol.add(appName);
        textCol.add(tagline);
        textCol.add(subtitle);

        // Stats bar
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        stats.setOpaque(false);
        stats.add(makeStat("20", "Features"));
        stats.add(makeStat("15", "Garments"));
        stats.add(makeStat("12", "Regions"));

        hero.add(textCol, BorderLayout.WEST);
        hero.add(stats, BorderLayout.EAST);

        // Decorative bottom border
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BaseUI.COLOR_BG_DARK);
        wrapper.add(hero, BorderLayout.CENTER);
        wrapper.add(new JSeparator() {{
            setForeground(BaseUI.COLOR_SECONDARY);
            setBackground(BaseUI.COLOR_SECONDARY);
        }}, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel makeStat(String number, String label) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 0));
        p.setOpaque(false);
        JLabel num = new JLabel(number, JLabel.CENTER);
        num.setFont(new Font("Serif", Font.BOLD, 22));
        num.setForeground(BaseUI.COLOR_SECONDARY);
        JLabel lbl = new JLabel(label, JLabel.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lbl.setForeground(new Color(160, 140, 110));
        p.add(num); p.add(lbl);
        return p;
    }

    // ── Feature Grid ─────────────────────────────────────────────────
    private JScrollPane buildFeatureGrid() {
        JPanel grid = new JPanel(new GridLayout(5, 4, 14, 14));
        grid.setBackground(new Color(35, 18, 6));
        grid.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        for (String[] meta : FEATURE_META) {
            grid.add(buildFeatureCard(meta));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(35, 18, 6));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private JPanel buildFeatureCard(String[] meta) {
        String num   = meta[0];
        String icon  = meta[1];
        String title = meta[2];
        String desc  = meta[3];
        Color  accent;
        try { accent = Color.decode(meta[4]); }
        catch (Exception e) { accent = BaseUI.COLOR_PRIMARY; }

        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(new Color(55, 35, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);

        JLabel iconLabel = new JLabel(icon + "  ");
        iconLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        iconLabel.setForeground(Color.WHITE);

        JLabel numLabel = new JLabel("#" + num);
        numLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        numLabel.setForeground(new Color(180, 150, 100));

        top.add(iconLabel);
        top.add(numLabel);

        JLabel titleLabel = new JLabel("<html><b>" + title + "</b></html>");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(BaseUI.COLOR_SECONDARY);

        JLabel descLabel = new JLabel("<html><span style='color:#aaa;font-size:10px'>" + desc + "</span></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);
        text.add(top);
        text.add(Box.createRigidArea(new Dimension(0, 4)));
        text.add(titleLabel);
        text.add(Box.createRigidArea(new Dimension(0, 3)));
        text.add(descLabel);

        card.add(text, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(75, 50, 20));
                card.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(55, 35, 15));
                card.repaint();
            }
            @Override public void mouseClicked(MouseEvent e) {
                openFeature(num);
            }
        });

        return card;
    }

    // ── Footer ───────────────────────────────────────────────────────
    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BaseUI.COLOR_BG_DARK);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(80, 60, 30)));

        JLabel lbl = new JLabel("VastraVeda  ·  Open Source Event Project  ·  Built with Java Swing  ·  🇮🇳");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(120, 100, 70));
        footer.add(lbl);
        return footer;
    }

    // ── Feature Router ───────────────────────────────────────────────
    /**
     * Opens the corresponding feature window.
     * Contributors: Add a case here when you add a new feature.
     */
    private void openFeature(String featureNumber) {
        Feature feature;
        switch (featureNumber) {
            case "1":  feature = new Feature1UI();  break;
            case "2":  feature = new Feature2UI();  break;
            case "3":  feature = new Feature3UI();  break;
            case "4":  feature = new Feature4UI();  break;
            case "5":  feature = new Feature5UI();  break;
            case "6":  feature = new Feature6UI();  break;
            case "7":  feature = new Feature7UI();  break;
            case "8":  feature = new Feature8UI();  break;
            case "9":  feature = new Feature9UI();  break;
            case "10": feature = new Feature10UI(); break;
            case "11": feature = new Feature11UI(); break;
            case "12": feature = new Feature12UI(); break;
            case "13": feature = new Feature13UI(); break;
            case "14": feature = new Feature14UI(); break;
            case "15": feature = new Feature15UI(); break;
            case "16": feature = new Feature16UI(); break;
            case "17": feature = new Feature17UI(); break;
            case "18": feature = new Feature18UI(); break;
            case "19": feature = new Feature19UI(); break;
            case "20": feature = new Feature20UI(); break;
            default:
                JOptionPane.showMessageDialog(this,
                    "Feature " + featureNumber + " not yet implemented.",
                    "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                return;
        }
        feature.render();
    }

    // ── Entry Point ──────────────────────────────────────────────────
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}
