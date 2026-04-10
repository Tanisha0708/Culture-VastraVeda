package vastraveda.features.feature17_versioncontrol;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature17UI extends BaseUI implements Feature {

    private final Feature17Service service = new Feature17Service();
    private JList<String> termList;
    private DefaultListModel<String> listModel;
    private JTextArea meaningArea;
    private JTextField searchField;
    private List<ClothingItem> currentItems;

    public Feature17UI() {
        super("Textile Glossary");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📚 Textile Glossary", "Explore Indian textile terms (A–Z)"), BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Type to search...");
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.SOUTH);

        JPanel alphabetPanel = new JPanel(new GridLayout(2, 13, 2, 2));
        alphabetPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        for (char c = 'A'; c <= 'Z'; c++) {
            char letter = c;
            JButton btn = createStyledButton(String.valueOf(c), COLOR_PRIMARY, COLOR_TEXT_LIGHT);
            btn.addActionListener(e -> loadItems(service.filterByLetter(letter)));
            alphabetPanel.add(btn);
        }
        add(alphabetPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane();
        listModel = new DefaultListModel<>();
        termList = new JList<>(listModel);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Terms"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(termList), BorderLayout.CENTER);
        splitPane.setLeftComponent(leftPanel);

        meaningArea = createTextArea("Select a term to view details...");
        meaningArea.setFont(new Font("Serif", Font.PLAIN, 15));
        splitPane.setRightComponent(new JScrollPane(meaningArea));
        splitPane.setDividerLocation(260);
        add(splitPane, BorderLayout.CENTER);

        loadItems(service.getAllItems());

        termList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = termList.getSelectedIndex();
                if (index >= 0 && currentItems != null && index < currentItems.size()) {
                    ClothingItem item = currentItems.get(index);
                    meaningArea.setText(
                        item.getImageIcon() + " " + item.getName() + "\n\n"
                            + "Region: " + item.getRegion() + "\n"
                            + "Fabric: " + item.getFabricType() + "\n"
                            + "Occasion: " + item.getOccasion() + "\n\n"
                            + item.getDescription() + "\n\n"
                            + "Care: " + item.getCareInstructions()
                    );
                }
            }
        });

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadItems(service.searchItems(searchField.getText()));
            }
        });
    }

    private void loadItems(List<ClothingItem> items) {
        currentItems = items;
        listModel.clear();
        if (items.isEmpty()) {
            listModel.addElement("No results");
            meaningArea.setText("");
            return;
        }
        for (ClothingItem item : items) {
            listModel.addElement(item.getImageIcon() + " " + item.getName());
        }
        meaningArea.setText("Select a term to view details...");
    }
}
