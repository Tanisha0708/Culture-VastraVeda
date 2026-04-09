package vastraveda.features.feature8_compare;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.List;

public class Feature8Service {

    public List<ClothingItem> getAllItems() {
        return DataStore.getAllItems();
    }

    public String[][] buildComparisonRows(ClothingItem a, ClothingItem b) {
        return new String[][]{
            {"Name",               a.getName(),             b.getName()},
            {"Region",             a.getRegion(),           b.getRegion()},
            {"Fabric",             a.getFabricType(),       b.getFabricType()},
            {"Occasion",           a.getOccasion(),         b.getOccasion()},
            {"Gender",             a.getGender(),           b.getGender()},
            {"Era",                a.getEra(),              b.getEra()},
            {"Description",        a.getDescription(),      b.getDescription()},
            {"Care Instructions",  a.getCareInstructions(), b.getCareInstructions()}
        };
    }

    public boolean valuesMatch(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.trim().equalsIgnoreCase(b.trim());
    }
}