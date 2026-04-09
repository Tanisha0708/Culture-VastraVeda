package vastraveda.features.feature3_fabric;

import java.util.HashMap;
import java.util.Map;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

public class Feature3Service {

    public Map<String, String> getFabricSpread() {

        Map<String, String> spreadMap = new HashMap<>();
        Map<String, StringBuilder> temp = new HashMap<>();

        for (ClothingItem item : DataStore.getAllItems()) {

            String fabric = item.getFabricType().split("/")[0].trim();
            String region = item.getRegion();

            temp.putIfAbsent(fabric, new StringBuilder());

            if (!temp.get(fabric).toString().contains(region)) {
                temp.get(fabric).append(region).append(", ");
            }
        }

        for (String fabric : temp.keySet()) {
            String value = temp.get(fabric).toString();
            spreadMap.put(fabric, value.substring(0, value.length() - 2));
        }

        return spreadMap;
    }
}