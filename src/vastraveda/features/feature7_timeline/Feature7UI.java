package vastraveda.features.feature7_timeline;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Feature7UI extends BaseUI implements Feature {

    private final Feature7Service service = new Feature7Service();
    private final JPanel displayArea = new JPanel();

    public Feature7UI() {
        super("VastraVeda — Chronological Archives");
        buildUI();
    }

    @Override
    public void render() { setVisible(true); }

    private void buildUI() {
        setLayout(new BorderLayout());
        
        // Header using BaseUI helper
        add(createHeader("📜 Historical Timeline", "Journey through the evolution of Indian textiles."), BorderLayout.NORTH);

        // Sidebar: Era Selection List
        DefaultListModel<String> listModel = new DefaultListModel<>();
        service.getTimelineEras().forEach(e -> listModel.addElement(e.name));
        
        JList<String> eraList = new JList<>(listModel);
        eraList.setFont(new Font("Serif", Font.BOLD, 16));
        eraList.setBackground(COLOR_BG_DARK);
        eraList.setForeground(COLOR_TEXT_LIGHT);
        eraList.setSelectionBackground(COLOR_ACCENT);
        eraList.setFixedCellHeight(55);
        
        eraList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateEraDetails(eraList.getSelectedIndex());
        });

        // Main Content Area
        displayArea.setLayout(new BorderLayout());
        displayArea.setBackground(COLOR_BG);
        displayArea.setBorder(new EmptyBorder(30, 30, 30, 30));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(eraList), new JScrollPane(displayArea));
        splitPane.setDividerLocation(200);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        eraList.setSelectedIndex(0); // Default selection
    }

    private void updateEraDetails(int index) {
        displayArea.removeAll();
        Feature7Service.EraData era = service.getTimelineEras().get(index);

        // 1. Era Header Section
        JPanel top = new JPanel(new GridLayout(0, 1));
        top.setOpaque(false);
        
        JLabel title = new JLabel(era.name + " (" + era.years + ")");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(COLOR_PRIMARY);
        
        JTextArea summary = createTextArea(era.summary);
        summary.setFont(new Font("Serif", Font.ITALIC, 16));
        
        top.add(title);
        top.add(summary);
        displayArea.add(top, BorderLayout.NORTH);

        // 2. EXTRA FEATURE: Highlight Box (The "Innovation" Badge)
        JPanel innovationBox = createCard();
        innovationBox.setBackground(new Color(255, 245, 220)); // Light cream highlight
        innovationBox.setLayout(new BorderLayout(10, 10));
        innovationBox.add(new JLabel("🛠 Landmark Innovation: "), BorderLayout.WEST);
        innovationBox.add(new JLabel("<html><i>" + era.innovation + "</i></html>"), BorderLayout.CENTER);
        
        // 3. Garments Gallery Section
        JPanel gallery = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        gallery.setOpaque(false);
        gallery.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER), "Cataloged Garments from this Period",
            TitledBorder.LEFT, TitledBorder.TOP, FONT_BODY, COLOR_TEXT));

        List<ClothingItem> matchingItems = service.getGarmentsForEra(era.name);
        if (matchingItems.isEmpty()) {
            gallery.add(new JLabel("No cataloged items found for this specific era filter."));
        } else {
            for (ClothingItem item : matchingItems) {
                JButton itemBtn = createStyledButton(item.getImageIcon() + " " + item.getName(), COLOR_CARD, COLOR_PRIMARY);
                itemBtn.setToolTipText("Origin: " + item.getRegion());
                itemBtn.addActionListener(e -> showInfo(item.getName(), item.getDescription()));
                gallery.add(itemBtn);
            }
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(innovationBox, BorderLayout.NORTH);
        centerPanel.add(gallery, BorderLayout.CENTER);

        displayArea.add(centerPanel, BorderLayout.CENTER);
        displayArea.revalidate();
        displayArea.repaint();
    }
}