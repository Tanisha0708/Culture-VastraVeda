package vastraveda.features.feature12_glossary;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.models.ClothingItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature12UI extends BaseUI implements Feature {

    private Feature12Service service;
    private JList<String> termList;
    private DefaultListModel<String> listModel;
    private JTextArea meaningArea;
    private JTextField searchField;

    private List<ClothingItem> currentItems;

    public Feature12UI() {
        super("Textile Glossary");
        service = new Feature12Service();
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        // Header
        add(createHeader("📚 Textile Glossary", "Explore Indian textile terms"), BorderLayout.NORTH);

        // 🔍 Search Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Type to search...");
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.SOUTH);

        // 🔤 Alphabet Panel
        JPanel alphabetPanel = new JPanel(new GridLayout(2, 13));
        for (char c = 'A'; c <= 'Z'; c++) {
            char letter = c;
            JButton btn = createStyledButton(String.valueOf(c), COLOR_PRIMARY, COLOR_TEXT_LIGHT);
            btn.addActionListener(e -> loadItems(service.filterByLetter(letter)));
            alphabetPanel.add(btn);
        }
        add(alphabetPanel, BorderLayout.NORTH);

        // 📌 Split Layout
        JSplitPane splitPane = new JSplitPane();

        listModel = new DefaultListModel<>();
        termList = new JList<>(listModel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("📚 Textile Terms"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(termList), BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        meaningArea = createTextArea("Select a term to view details...");
        meaningArea.setFont(new Font("Serif", Font.PLAIN, 16));

        JScrollPane rightScroll = new JScrollPane(meaningArea);
        splitPane.setRightComponent(rightScroll);

        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        // Load initial data
        loadItems(service.getAllItems());

        // 📌 List Selection
        termList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = termList.getSelectedIndex();

                if (index >= 0 && currentItems != null && index < currentItems.size()) {
                    ClothingItem item = currentItems.get(index);

                    String details =
                            "👗 " + item.getName() + "\n\n" +
                            "📍 Region: " + item.getRegion() + "\n" +
                            "🧵 Fabric: " + item.getFabricType() + "\n" +
                            "🎉 Occasion: " + item.getOccasion() + "\n\n" +
                            "📖 Description:\n" +
                            item.getDescription() + "\n\n" +
                            "🧺 Care Tips:\n" +
                            item.getCareInstructions();

                    meaningArea.setText(details);
                }
            }
        });

        // 🔥 Live Search
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = searchField.getText();
                loadItems(service.searchItems(keyword));
            }
        });
    }

    private void loadItems(List<ClothingItem> items) {
        currentItems = items;
        listModel.clear();

        if (items.isEmpty()) {
            listModel.addElement("No results found");
            meaningArea.setText("");
            return;
        }

        for (ClothingItem item : items) {
            listModel.addElement(item.getImageIcon() + " " + item.getName());
        }

        meaningArea.setText("Select a term to view details...");
    }
}