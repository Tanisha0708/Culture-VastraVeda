package vastraveda.features.feature3_fabric;

import java.util.HashMap;
import java.util.Map;

/**
 * Feature 3 — Fabric Service
 */
public class Feature3Service {

    private static final Map<String, String> CARE_TIPS = new HashMap<>();
    static {
        CARE_TIPS.put("Silk",     "Dry clean only. Store in muslin cloth. Avoid direct sunlight.");
        CARE_TIPS.put("Cotton",   "Machine wash cold. Iron at medium heat. Air dry preferred.");
        CARE_TIPS.put("Wool",     "Dry clean or hand wash in cold water. Lay flat to dry.");
        CARE_TIPS.put("Linen",    "Machine wash cool. Iron while damp. Avoid wringing.");
        CARE_TIPS.put("Brocade",  "Dry clean only. Handle with care to preserve zari work.");
        CARE_TIPS.put("Pashmina", "Dry clean only. Fold, do not hang. Use cedar balls for storage.");
    }

    public String getCareTip(String fabric) {
        return CARE_TIPS.getOrDefault(fabric, "Follow garment label instructions.");
    }
}
