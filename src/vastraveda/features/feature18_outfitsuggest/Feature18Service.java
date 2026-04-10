package vastraveda.features.feature18_outfitsuggest;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Feature18Service {

    public static final class RankedItem {
        public final ClothingItem item;
        public final int score;
        public final String reason;

        public RankedItem(ClothingItem item, int score, String reason) {
            this.item = item;
            this.score = score;
            this.reason = reason;
        }
    }

    public List<ClothingItem> getAllItems() {
        return DataStore.getAllItems();
    }

    public List<RankedItem> suggestSimilar(ClothingItem base, int limit) {
        if (base == null) {
            return Collections.emptyList();
        }
        List<RankedItem> ranked = new ArrayList<>();
        for (ClothingItem other : DataStore.getAllItems()) {
            if (other == base || other.getName().equals(base.getName())) {
                continue;
            }
            int score = 0;
            List<String> reasons = new ArrayList<>();
            if (eqFabric(base.getFabricType(), other.getFabricType())) {
                score += 40;
                reasons.add("fabric");
            }
            if (containsIgnoreCase(base.getRegion(), other.getRegion())
                || containsIgnoreCase(other.getRegion(), base.getRegion())) {
                score += 25;
                reasons.add("region");
            }
            if (eqOccasion(base.getOccasion(), other.getOccasion())) {
                score += 20;
                reasons.add("occasion");
            }
            if (base.getGender().equalsIgnoreCase(other.getGender())
                || "Unisex".equalsIgnoreCase(other.getGender())
                || "Unisex".equalsIgnoreCase(base.getGender())) {
                score += 15;
                reasons.add("gender");
            }
            if (score > 0) {
                ranked.add(new RankedItem(other, score, String.join(", ", reasons)));
            }
        }
        ranked.sort(Comparator.comparingInt((RankedItem r) -> r.score).reversed());
        if (ranked.size() > limit) {
            return ranked.subList(0, limit);
        }
        return ranked;
    }

    private boolean eqFabric(String a, String b) {
        String x = a.toLowerCase(Locale.ROOT);
        String y = b.toLowerCase(Locale.ROOT);
        return x.contains(y) || y.contains(x) || tokenOverlap(x, y);
    }

    private boolean tokenOverlap(String x, String y) {
        String[] ta = x.split("[/,\\s]+");
        String[] tb = y.split("[/,\\s]+");
        for (String s : ta) {
            if (s.length() < 3) {
                continue;
            }
            for (String t : tb) {
                if (t.length() >= 3 && (s.contains(t) || t.contains(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsIgnoreCase(String a, String b) {
        return a.toLowerCase(Locale.ROOT).contains(b.toLowerCase(Locale.ROOT))
            || b.toLowerCase(Locale.ROOT).contains(a.toLowerCase(Locale.ROOT));
    }

    private boolean eqOccasion(String a, String b) {
        String x = a.toLowerCase(Locale.ROOT);
        String y = b.toLowerCase(Locale.ROOT);
        return x.contains(y) || y.contains(x);
    }
}
