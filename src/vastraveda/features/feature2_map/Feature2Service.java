package vastraveda.features.feature2_map;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Feature 2 — Map Service
 */
public class Feature2Service {

    public static final class MapRegion {
        private final String name;
        private final int x;
        private final int y;

        public MapRegion(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public List<MapRegion> getMapRegions() {
        return Arrays.asList(
            new MapRegion("Kashmir", 110, 28),
            new MapRegion("Punjab", 95, 68),
            new MapRegion("Uttar Pradesh", 175, 88),
            new MapRegion("Rajasthan", 85, 126),
            new MapRegion("Gujarat", 70, 182),
            new MapRegion("Maharashtra", 130, 206),
            new MapRegion("Goa", 108, 246),
            new MapRegion("Karnataka", 138, 272),
            new MapRegion("Kerala", 146, 336),
            new MapRegion("Tamil Nadu", 184, 324),
            new MapRegion("Telangana", 196, 248),
            new MapRegion("Odisha", 252, 204),
            new MapRegion("West Bengal", 284, 156),
            new MapRegion("Assam", 342, 122),
            new MapRegion("Manipur", 362, 162),
            new MapRegion("Bihar", 240, 128)
        );
    }

    public List<ClothingItem> getItemsByRegion(String region) {
        return FilterUtils.filterByRegion(region);
    }

    public List<ClothingItem> getTopItemsByRegion(String region, int limit) {
        List<ClothingItem> all = getItemsByRegion(region);
        int safeLimit = Math.max(1, limit);
        if (all.size() <= safeLimit) {
            return all;
        }
        return new ArrayList<>(all.subList(0, safeLimit));
    }

    public String truncate(String text, int maxLen) {
        if (text == null) return "";
        return text.length() <= maxLen ? text : text.substring(0, maxLen) + "...";
    }
}
