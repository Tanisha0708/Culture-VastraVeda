package vastraveda.features.feature12_glossary;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.List;
import java.util.stream.Collectors;

public class Feature12Service {

    public List<ClothingItem> getAllItems() {
        return DataStore.getAllItems();
    }

    public List<ClothingItem> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }

        return DataStore.getAllItems()
                .stream()
                .filter(item ->
                        item.getName().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    // 🔤 NEW: Filter by alphabet
    public List<ClothingItem> filterByLetter(char letter) {
        return DataStore.getAllItems()
                .stream()
                .filter(item ->
                        item.getName().toUpperCase().startsWith(String.valueOf(letter))
                )
                .collect(Collectors.toList());
    }
}