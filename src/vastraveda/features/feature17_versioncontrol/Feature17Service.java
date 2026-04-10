package vastraveda.features.feature17_versioncontrol;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Textile glossary (moved from Feature 12 slot). Feature 12 is now Clothing Version Control.
 */
public class Feature17Service {

    public List<ClothingItem> getAllItems() {
        return new ArrayList<>(DataStore.getAllItems());
    }

    public List<ClothingItem> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }
        String q = keyword.toLowerCase(Locale.ROOT);
        List<ClothingItem> out = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getName().toLowerCase(Locale.ROOT).contains(q)) {
                out.add(item);
            }
        }
        return out;
    }

    public List<ClothingItem> filterByLetter(char letter) {
        String prefix = String.valueOf(letter).toUpperCase(Locale.ROOT);
        List<ClothingItem> out = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getName().toUpperCase(Locale.ROOT).startsWith(prefix)) {
                out.add(item);
            }
        }
        return out;
    }
}
