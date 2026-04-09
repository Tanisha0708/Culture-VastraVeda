package vastraveda.features.feature9_gallery;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;

public class Feature9Service {

    public List<ClothingItem> getAllGarments() {
        return DataStore.getAllItems();
    }

    public ClothingItem getItemDetail(String name) {
        return DataStore.getAllItems().stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}