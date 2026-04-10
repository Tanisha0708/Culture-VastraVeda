package vastraveda.features.feature7_timeline;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.List;

/**
 * Cross-culture clothing comparison (two items, attribute diff).
 */
public class Feature7Service {

    public List<ClothingItem> getAllItems() {
        return DataStore.getAllItems();
    }

    public String[][] buildComparisonRows(ClothingItem a, ClothingItem b) {
        return new String[][]{
            {"Name", a.getName(), b.getName()},
            {"Region", a.getRegion(), b.getRegion()},
            {"Fabric", a.getFabricType(), b.getFabricType()},
            {"Occasion", a.getOccasion(), b.getOccasion()},
            {"Gender", a.getGender(), b.getGender()},
            {"Era", a.getEra(), b.getEra()},
            {"Description", a.getDescription(), b.getDescription()},
            {"Care", a.getCareInstructions(), b.getCareInstructions()}
        };
    }

    public boolean valuesMatch(String x, String y) {
        if (x == null && y == null) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.trim().equalsIgnoreCase(y.trim());
    }
}
