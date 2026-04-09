package vastraveda.features.feature10_stories;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Feature 10 — Cultural Stories UI
 */
public class Feature10UI extends BaseUI implements Feature {

    private final Feature10Service service = new Feature10Service();

    private JList<String> itemList;
    private JTextArea storyArea;
    private JLabel metaLabel;

    public Feature10UI() {
        super("Cultural Stories");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        add(createHeader("📖 Cultural Stories", "Explore the heritage behind garments"), BorderLayout.NORTH);

        List<ClothingItem> items = DataStore.getAllItems();

        // LEFT: List
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ClothingItem item : items) {
            listModel.addElement(item.getName());
        }

        itemList = new JList<>(listModel);
        itemList.setFont(FONT_BODY);
        JScrollPane leftScroll = new JScrollPane(itemList);

        // RIGHT: Story Panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(COLOR_BG);

        storyArea = new JTextArea("Select an item to view its story...");
        storyArea.setWrapStyleWord(true);
        storyArea.setLineWrap(true);
        storyArea.setEditable(false);
        storyArea.setFont(FONT_BODY);
        storyArea.setBackground(COLOR_CARD);

        JScrollPane storyScroll = new JScrollPane(storyArea);

        metaLabel = new JLabel(" ");
        metaLabel.setFont(FONT_SMALL);
        metaLabel.setForeground(COLOR_PRIMARY);

        rightPanel.add(storyScroll, BorderLayout.CENTER);
        rightPanel.add(metaLabel, BorderLayout.SOUTH);

        // SPLIT PANE
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightPanel);
        splitPane.setDividerLocation(200);

        add(splitPane, BorderLayout.CENTER);

        // LISTENER
        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedName = itemList.getSelectedValue();

                if (selectedName != null) {
                    String story = service.getStory(selectedName);
                    storyArea.setText(story);

                    // Find metadata
                    for (ClothingItem item : items) {
                        if (item.getName().equals(selectedName)) {
                            metaLabel.setText(
                                    "Region: " + item.getRegion() +
                                    " | Fabric: " + item.getFabricType() +
                                    " | Occasion: " + item.getOccasion()
                            );
                            break;
                        }
                    }
                }
            }
        });
    }
}