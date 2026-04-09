package vastraveda.features.feature11_care;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.*;

public class Feature11Service {

    public List<String> getAllFabrics() {
        return DataStore.getAllFabrics();
    }

    public List<ClothingItem> getItemsByFabric(String fabric) {
        if (fabric == null || fabric.equals("All Fabrics")) {
            return DataStore.getAllItems();
        }
        return FilterUtils.filterByFabric(fabric);
    }

    public Map<String, String[]> getCareTips() {
        Map<String, String[]> tips = new LinkedHashMap<>();
        tips.put("Silk",    new String[]{
            "Hand wash in cold water with mild detergent",
            "Dry in shade away from direct sunlight",
            "Iron on low heat with a cloth between iron and fabric",
            "Store folded in muslin cloth",
            "Never wring or twist",
            "Never bleach",
            "Never tumble dry"
        });
        tips.put("Cotton",  new String[]{
            "Machine wash on gentle cycle with mild detergent",
            "Tumble dry on low or air dry",
            "Iron while slightly damp for best results",
            "Store in cool dry place",
            "Avoid bleach on dyed cottons",
            "Do not iron embellished areas directly"
        });
        tips.put("Wool",    new String[]{
            "Dry clean or hand wash in cold water",
            "Lay flat to dry — never hang wet wool",
            "Store with cedar blocks to prevent moths",
            "Never hang — store folded",
            "Never machine wash",
            "Never tumble dry"
        });
        tips.put("Linen",   new String[]{
            "Machine wash on gentle cycle",
            "Iron while damp on medium-high heat",
            "Air dry or tumble dry low",
            "Linen softens beautifully with each wash",
            "Avoid wringing"
        });
        tips.put("Khadi",   new String[]{
            "Hand wash gently in cold water",
            "Dry in shade",
            "Light ironing only",
            "Avoid machine washing — Khadi is delicate"
        });
        tips.put("Brocade", new String[]{
            "Dry clean only",
            "Store rolled, not folded, to protect woven patterns",
            "Keep away from moisture and humidity",
            "Never iron directly — use a pressing cloth"
        });
        tips.put("Chiffon", new String[]{
            "Hand wash in cold water or dry clean",
            "Drip dry — never wring",
            "Iron on lowest setting or steam carefully",
            "Store hanging to prevent creases"
        });
        tips.put("Velvet",  new String[]{
            "Dry clean only",
            "Steam to remove creases — never iron directly",
            "Store hanging to prevent crush marks",
            "Brush gently with a soft cloth to restore pile"
        });
        return tips;
    }

    public String getDefaultTip() {
        return "Always check the garment label. When in doubt, hand wash in cold water and air dry in shade away from direct sunlight.";
    }
}