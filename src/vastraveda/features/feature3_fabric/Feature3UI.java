package vastraveda.features.feature3_fabric;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Feature3UI extends BaseUI implements Feature {
    
    private JPanel mainPanel;
    private JPanel timelinePanel;
    private JPanel detailPanel;
    private JLabel fabricTitle;
    private JTextArea fabricDetails;
    private String selectedFabric = "Khadi";
    
    // Fabric data with origin and spread history
    private Map<String, FabricData> fabrics;
    
    public Feature3UI() {
        super("🧵 Fabric Explorer - VastraVeda");
        initFabrics();
        setupUI();
        setVisible(true);
    }
    
    private void initFabrics() {
        fabrics = new LinkedHashMap<>();
        
        // KHADI - The Freedom Fabric
        fabrics.put("Khadi", new FabricData(
            "Khadi",
            "🕊️",
            "Hand-spun, hand-woven natural fiber cloth",
            new OriginInfo("Gujarat", "West India", "5000+ years ago", 
                "Ancient India, promoted by Mahatma Gandhi in 1920s"),
            new SpreadInfo[]{
                new SpreadInfo("1918", "Ahmedabad", "First Khadi Ashram established by Gandhi ji"),
                new SpreadInfo("1920", "Nationwide", "Non-cooperation movement - Khadi becomes symbol of Swadeshi"),
                new SpreadInfo("1956", "West Bengal", "Santiniketan - Amar Kutir Khadi center established"),
                new SpreadInfo("1970", "Rajasthan", "Jaipur becomes hub for hand-block printed Khadi"),
                new SpreadInfo("1990", "Odisha", "Sambalpur - Ikat Khadi innovation"),
                new SpreadInfo("2020", "Global", "Khadi exported to 100+ countries, luxury fashion adoption")
            },
            new String[]{"Gandhi's vision of self-reliance", "Worn by freedom fighters", "Now a global sustainable fashion icon"},
            new String[]{"Gujarat", "West Bengal", "Rajasthan", "Odisha", "Tamil Nadu", "Uttar Pradesh"},
            new String[]{"Gandhi Cap", "Khadi Kurta", "Khadi Saree", "Khadi Nehru Jacket"}
        ));
        
        // Muga Silk - Assam's Golden Silk
        fabrics.put("Muga Silk", new FabricData(
            "Muga Silk",
            "🦚",
            "Golden-yellow silk unique to Assam, cannot be dyed",
            new OriginInfo("Assam", "Northeast India", "2000+ years ago", 
                "Ancient Assam - Patkai hills, exclusively produced in Brahmaputra Valley"),
            new SpreadInfo[]{
                new SpreadInfo("300 BCE", "Assam", "Mentioned in Kalika Purana as royal fabric"),
                new SpreadInfo("1200 CE", "Ahom Kingdom", "Royal patronage - exclusively for Ahom royalty"),
                new SpreadInfo("1800s", "British Era", "Exported to Europe, won awards at exhibitions"),
                new SpreadInfo("1970", "National", "Geographical Indication (GI) tag applied"),
                new SpreadInfo("2007", "Global", "Received GI tag - only Assam can produce Muga"),
                new SpreadInfo("2023", "International", "Luxury brands like Louis Vuitton use Muga silk")
            },
            new String[]{"Royal Ahom dynasty exclusive", "GI tagged - only Assam", "Cannot be dyed - natural golden color"},
            new String[]{"Assam (only producer)", "Sualkuchi - Silk Village", "Jorhat", "Golaghat"},
            new String[]{"Muga Mekhela Chador", "Muga Saree", "Royal Shawl", "Traditional Assam attire"}
        ));
        
        // Banarasi Silk - Varanasi's Legacy
        fabrics.put("Banarasi Silk", new FabricData(
            "Banarasi Silk",
            "👑",
            "Rich silk brocade with gold and silver zari work",
            new OriginInfo("Varanasi", "Uttar Pradesh", "600+ years ago", 
                "Mughal era - Persian influence blended with Indian techniques"),
            new SpreadInfo[]{
                new SpreadInfo("1350 CE", "Varanasi", "Silk weaving introduced under Firoz Shah Tughlaq"),
                new SpreadInfo("1600s", "Mughal Court", "Royal patronage under Akbar and Shah Jahan"),
                new SpreadInfo("1800s", "British Era", "Adapted to European tastes, new designs created"),
                new SpreadInfo("1950s", "Bollywood", "Featured in Hindi films, became wedding favorite"),
                new SpreadInfo("2000s", "Global", "International fashion weeks feature Banarasi silk"),
                new SpreadInfo("2020", "Digital", "GI tag protected, online sales boom during COVID")
            },
            new String[]{"Persian-Indian fusion", "Mughal royal courts", "Bollywood wedding saree", "Heirloom quality"},
            new String[]{"Varanasi", "Jaunpur", "Azamgarh", "Mau", "Delhi"},
            new String[]{"Banarasi Saree", "Brocade Sherwani", "Wedding Lehenga", "Royal Dupatta"}
        ));
        
        // Patola - Gujarat's Double Ikat
        fabrics.put("Patola Silk", new FabricData(
            "Patola Silk",
            "🎨",
            "Double ikat woven silk with geometric patterns",
            new OriginInfo("Patan", "Gujarat", "1000+ years ago", 
                "Salvi community from Maharashtra settled in Patan, perfected double ikat"),
            new SpreadInfo[]{
                new SpreadInfo("1000 CE", "Patan", "Salvi weavers brought ikat technique from Maharashtra"),
                new SpreadInfo("1200s", "Solanki Dynasty", "Royal patronage - Patola becomes court dress"),
                new SpreadInfo("1500s", "Indonesia", "Traded to Indonesia, influenced local ikat traditions"),
                new SpreadInfo("1800s", "Gujarat", "Raja of Rajpipla patronized Patola weaving"),
                new SpreadInfo("1950s", "National", "Post-independence revival by Handloom Board"),
                new SpreadInfo("2010", "Global", "GI tag - only Patan can produce authentic Patola")
            },
            new String[]{"Double ikat - both warp and weft tie-dyed", "Can take 6-12 months for one saree", "Temple motifs - elephants, flowers"},
            new String[]{"Patan", "Ahmedabad", "Surat", "Rajkot"},
            new String[]{"Patola Saree", "Royal Dupatta", "Ceremonial Shawl", "Wedding Attire"}
        ));
        
        // Chanderi - Madhya Pradesh's Transparent Silk
        fabrics.put("Chanderi Silk", new FabricData(
            "Chanderi Silk",
            "✨",
            "Lightweight silk-cotton fabric with transparent texture",
            new OriginInfo("Chanderi", "Madhya Pradesh", "2000+ years ago", 
                "Buddhist period - known as 'Chanderi fabric' in Jataka tales"),
            new SpreadInfo[]{
                new SpreadInfo("200 BCE", "Chanderi", "Mentioned in Buddhist literature"),
                new SpreadInfo("1100s", "Scindia Dynasty", "Royal patronage under Maratha rulers"),
                new SpreadInfo("1700s", "Mughal Era", "Gold and silver zari work introduced"),
                new SpreadInfo("1900s", "National", "Favorite of Indian royalty and elites"),
                new SpreadInfo("2000s", "Bollywood", "Featured in films like 'Jodha Akbar'"),
                new SpreadInfo("2019", "Global", "UNESCO recognition for Chanderi weaving cluster")
            },
            new String[]{"Transparent texture", "Gold motifs", "Lightweight - perfect for summer"},
            new String[]{"Chanderi", "Bhopal", "Gwalior", "Indore", "Delhi"},
            new String[]{"Chanderi Saree", "Dupatta", "Kurta Fabric", "Designer Wear"}
        ));
        
        // Pashmina - Kashmir's Cashmere
        fabrics.put("Pashmina", new FabricData(
            "Pashmina",
            "🧣",
            "Fine cashmere wool from Changthangi goat, hand-spun and woven",
            new OriginInfo("Kashmir", "Ladakh", "3000+ years ago", 
                "Changthangi goats in Ladakh - wool known as 'cashmere' in West"),
            new SpreadInfo[]{
                new SpreadInfo("1000 BCE", "Kashmir", "Pashmina weaving mentioned in Nilamata Purana"),
                new SpreadInfo("1500s", "Mughal Court", "Emperor Akbar popularized Pashmina shawls"),
                new SpreadInfo("1700s", "France", "Empress Josephine owned Pashmina shawls"),
                new SpreadInfo("1800s", "Europe", "Queen Victoria collected Pashmina shawls"),
                new SpreadInfo("1970s", "Global", "Pashmina becomes luxury fashion worldwide"),
                new SpreadInfo("2008", "GI Tag", "Kashmir Pashmina gets GI protection")
            },
            new String[]{"Hand-spun by artisans", "So fine it can pass through a ring", "Warmer than sheep wool"},
            new String[]{"Kashmir Valley", "Ladakh", "Srinagar", "Kargil"},
            new String[]{"Pashmina Shawl", "Cashmere Sweater", "Stole", "Wraps"}
        ));
        
        // Ikat - Odisha's Tie-Dye Magic
        fabrics.put("Ikat (Sambalpuri)", new FabricData(
            "Ikat (Sambalpuri)",
            "🌀",
            "Tie-dye technique where yarns are dyed before weaving",
            new OriginInfo("Sambalpur", "Odisha", "2000+ years ago", 
                "Tribal communities - Bhulia weavers developed ikat technique"),
            new SpreadInfo[]{
                new SpreadInfo("200 BCE", "Odisha", "Ikat weaving mentioned in ancient texts"),
                new SpreadInfo("1200s", "Gajapati Dynasty", "Royal patronage - ikat for Jagannath temple"),
                new SpreadInfo("1800s", "British", "Ikat exports to Europe for museum collections"),
                new SpreadInfo("1950s", "National", "Post-independence revival by Sambalpur weavers"),
                new SpreadInfo("2005", "GI Tag", "Sambalpuri Ikat gets GI protection"),
                new SpreadInfo("2022", "Global", "G20 summit delegates gifted Ikat products")
            },
            new String[]{"Double ikat - both warp and weft dyed", "Traditional motifs - conch, wheel, fish", "Geometric patterns"},
            new String[]{"Sambalpur", "Bargarh", "Sonepur", "Bhubaneswar", "Cuttack"},
            new String[]{"Sambalpuri Saree", "Ikat Dupatta", "Scarf", "Home Furnishings"}
        ));
        
        // Kalamkari - Andhra's Hand-Painted Fabric
        fabrics.put("Kalamkari", new FabricData(
            "Kalamkari",
            "🖌️",
            "Hand-painted or block-printed cotton fabric using natural dyes",
            new OriginInfo("Machilipatnam", "Andhra Pradesh", "3000+ years ago", 
                "Mughal and Golconda patronage - Persian influence"),
            new SpreadInfo[]{
                new SpreadInfo("1000 BCE", "Andhra", "Found in ancient Buddhist sites"),
                new SpreadInfo("1500s", "Golconda", "Royal patronage for temple hangings"),
                new SpreadInfo("1700s", "British Era", "Exported to Europe, called 'chintz'"),
                new SpreadInfo("1950s", "National", "Kamaladevi Chattopadhyay revived Kalamkari"),
                new SpreadInfo("2000s", "Global", "UNESCO heritage status for Kalamkari"),
                new SpreadInfo("2023", "Fashion", "Sustainable fashion brands adopt Kalamkari")
            },
            new String[]{"Natural dyes only", "Mythological scenes", "Temple art on fabric"},
            new String[]{"Machilipatnam", "Srikalahasti", "Hyderabad", "Vijayawada", "Chennai"},
            new String[]{"Kalamkari Saree", "Wall Hanging", "Dupatta", "Home Decor"}
        ));
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0x0e0600));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);
        splitPane.setBackground(new Color(0x0e0600));
        
        // LEFT PANEL - Timeline / Fabric List
        JPanel leftPanel = createLeftPanel();
        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setBorder(BorderFactory.createEmptyBorder());
        leftScroll.getViewport().setBackground(new Color(0x0a0500));
        
        // RIGHT PANEL - Detail View
        detailPanel = createDetailPanel();
        
        splitPane.setLeftComponent(leftScroll);
        splitPane.setRightComponent(detailPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
        // Load default fabric
        selectFabric("Khadi");
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0x1a0d05));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel title = new JLabel("🧵 Fabric Origins & Timeline", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0xFF9933));
        
        JLabel subtitle = new JLabel("Discover how traditional Indian fabrics originated and spread across regions", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(0xCCAA88));
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(subtitle, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0x0a0500));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JLabel sectionTitle = new JLabel("📜 INDIAN FABRICS");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sectionTitle.setForeground(new Color(0xFF9933));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sectionTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Fabric buttons in scrollable list
        for (Map.Entry<String, FabricData> entry : fabrics.entrySet()) {
            FabricData fabric = entry.getValue();
            JButton fabricBtn = createFabricButton(fabric);
            fabricBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(fabricBtn);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        // Quick stats
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel statsTitle = new JLabel("📊 QUICK STATS");
        statsTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statsTitle.setForeground(new Color(0xCCAA88));
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(statsTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        String[] stats = {
            "🏺 8+ Traditional Fabrics",
            "📍 20+ Regions of Origin",
            "📅 3000+ Years of History",
            "🌍 100+ Countries Export",
            "✨ 10+ GI Tagged Fabrics"
        };
        
        for (String stat : stats) {
            JLabel statLabel = new JLabel(stat);
            statLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            statLabel.setForeground(new Color(0xf5e6cc));
            statLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(statLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        return panel;
    }
    
    private JButton createFabricButton(FabricData fabric) {
        JButton button = new JButton(fabric.icon + " " + fabric.name);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(new Color(0x1a0d05));
        button.setForeground(new Color(0xf5e6cc));
        button.setBorder(BorderFactory.createLineBorder(new Color(0xa35c2c), 1));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(280, 35));
        
        button.addActionListener(e -> selectFabric(fabric.name));
        
        return button;
    }
    
    private JPanel createDetailPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x1a0d05));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title area
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(0x1a0d05));
        
        fabricTitle = new JLabel();
        fabricTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        fabricTitle.setForeground(new Color(0xFF9933));
        titlePanel.add(fabricTitle);
        
        // Details text area
        fabricDetails = new JTextArea();
        fabricDetails.setEditable(false);
        fabricDetails.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fabricDetails.setBackground(new Color(0x0e0600));
        fabricDetails.setForeground(new Color(0xf5e6cc));
        fabricDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xa35c2c), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JScrollPane scrollPane = new JScrollPane(fabricDetails);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void selectFabric(String fabricName) {
        selectedFabric = fabricName;
        FabricData fabric = fabrics.get(fabricName);
        
        if (fabric != null) {
            updateDetailPanel(fabric);
        }
    }
    
    private void updateDetailPanel(FabricData fabric) {
        fabricTitle.setText(fabric.icon + " " + fabric.name + " - " + fabric.description);
        
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='width: 550px; font-family: Segoe UI;'>");
        
        // Origin Section
        html.append("<h2 style='color: #FF9933;'>📜 ORIGIN & HISTORY</h2>");
        html.append("<div style='background: #1a0d05; padding: 12px; border-radius: 8px; margin-bottom: 15px;'>");
        html.append("<b>📍 Location:</b> ").append(fabric.origin.location).append(", ").append(fabric.origin.region).append("<br>");
        html.append("<b>⏰ Era:</b> ").append(fabric.origin.era).append("<br>");
        html.append("<b>📖 History:</b> ").append(fabric.origin.history).append("<br>");
        html.append("</div>");
        
        // Timeline Section - Connected list view
        html.append("<h2 style='color: #FF9933;'>⏳ SPREAD TIMELINE</h2>");
        html.append("<div style='background: #1a0d05; padding: 12px; border-radius: 8px; margin-bottom: 15px;'>");
        
        for (int i = 0; i < fabric.spreadHistory.length; i++) {
            SpreadInfo spread = fabric.spreadHistory[i];
            html.append("<div style='display: flex; margin-bottom: 12px;'>");
            html.append("<div style='min-width: 70px; color: #FF9933; font-weight: bold;'>").append(spread.year).append("</div>");
            html.append("<div style='flex: 1;'>");
            html.append("<b>").append(spread.location).append("</b><br>");
            html.append("<span style='color: #CCAA88; font-size: 12px;'>").append(spread.event).append("</span>");
            html.append("</div>");
            html.append("</div>");
            
            if (i < fabric.spreadHistory.length - 1) {
                html.append("<div style='margin-left: 35px; margin-bottom: 5px; color: #FF9933;'>↓</div>");
            }
        }
        html.append("</div>");
        
        // Cultural Significance
        html.append("<h2 style='color: #FF9933;'>✨ CULTURAL SIGNIFICANCE</h2>");
        html.append("<div style='background: #1a0d05; padding: 12px; border-radius: 8px; margin-bottom: 15px;'>");
        html.append("<ul style='margin: 0; padding-left: 20px; color: #CCAA88;'>");
        for (String sig : fabric.culturalSignificance) {
            html.append("<li>").append(sig).append("</li>");
        }
        html.append("</ul>");
        html.append("</div>");
        
        // Major Regions
        html.append("<h2 style='color: #FF9933;'>📍 MAJOR PRODUCING REGIONS</h2>");
        html.append("<div style='background: #1a0d05; padding: 12px; border-radius: 8px; margin-bottom: 15px;'>");
        html.append("<div style='display: flex; flex-wrap: wrap; gap: 8px;'>");
        for (String region : fabric.majorRegions) {
            html.append("<span style='background: #FF9933; color: #0e0600; padding: 4px 12px; border-radius: 20px; font-size: 12px;'>📍 ").append(region).append("</span>");
        }
        html.append("</div>");
        html.append("</div>");
        
        // Common Garments
        html.append("<h2 style='color: #FF9933;'>👘 COMMON GARMENTS</h2>");
        html.append("<div style='background: #1a0d05; padding: 12px; border-radius: 8px;'>");
        html.append("<div style='display: flex; flex-wrap: wrap; gap: 8px;'>");
        for (String garment : fabric.commonGarments) {
            html.append("<span style='background: #2a1a0a; border: 1px solid #FF9933; color: #f5e6cc; padding: 4px 12px; border-radius: 20px; font-size: 12px;'>👘 ").append(garment).append("</span>");
        }
        html.append("</div>");
        html.append("</div>");
        
        html.append("</body></html>");
        
        fabricDetails.setText(extractPlainText(fabric));
        fabricDetails.setCaretPosition(0);
    }
    
    private String extractPlainText(FabricData fabric) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(fabric.name.toUpperCase()).append(" - ").append(fabric.description).append("\n");
        sb.append("═".repeat(50)).append("\n\n");
        
        sb.append("📜 ORIGIN & HISTORY\n");
        sb.append("─".repeat(30)).append("\n");
        sb.append("Location: ").append(fabric.origin.location).append(", ").append(fabric.origin.region).append("\n");
        sb.append("Era: ").append(fabric.origin.era).append("\n");
        sb.append("History: ").append(fabric.origin.history).append("\n\n");
        
        sb.append("⏳ SPREAD TIMELINE\n");
        sb.append("─".repeat(30)).append("\n");
        for (SpreadInfo spread : fabric.spreadHistory) {
            sb.append(spread.year).append(" - ").append(spread.location).append("\n");
            sb.append("   └─ ").append(spread.event).append("\n\n");
        }
        
        sb.append("✨ CULTURAL SIGNIFICANCE\n");
        sb.append("─".repeat(30)).append("\n");
        for (String sig : fabric.culturalSignificance) {
            sb.append("• ").append(sig).append("\n");
        }
        sb.append("\n");
        
        sb.append("📍 MAJOR PRODUCING REGIONS\n");
        sb.append("─".repeat(30)).append("\n");
        sb.append(String.join(", ", fabric.majorRegions)).append("\n\n");
        
        sb.append("👘 COMMON GARMENTS\n");
        sb.append("─".repeat(30)).append("\n");
        sb.append(String.join(", ", fabric.commonGarments)).append("\n");
        
        return sb.toString();
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(0x1a0d05));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel footer = new JLabel("✨ Preserving India's Textile Heritage | GI Tagged Fabrics | 3000+ Years of Weaving Tradition ✨");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(0xCCAA88));
        panel.add(footer);
        
        return panel;
    }
    
    @Override
    public void render() {
        setVisible(true);
    }
    
    // Inner Classes
    class OriginInfo {
        String location, region, era, history;
        OriginInfo(String location, String region, String era, String history) {
            this.location = location; this.region = region; this.era = era; this.history = history;
        }
    }
    
    class SpreadInfo {
        String year, location, event;
        SpreadInfo(String year, String location, String event) {
            this.year = year; this.location = location; this.event = event;
        }
    }
    
    class FabricData {
        String name, icon, description;
        OriginInfo origin;
        SpreadInfo[] spreadHistory;
        String[] culturalSignificance;
        String[] majorRegions;
        String[] commonGarments;
        
        FabricData(String name, String icon, String description, OriginInfo origin,
                   SpreadInfo[] spreadHistory, String[] culturalSignificance,
                   String[] majorRegions, String[] commonGarments) {
            this.name = name; this.icon = icon; this.description = description;
            this.origin = origin; this.spreadHistory = spreadHistory;
            this.culturalSignificance = culturalSignificance;
            this.majorRegions = majorRegions; this.commonGarments = commonGarments;
        }
    }
}