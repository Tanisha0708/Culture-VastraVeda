package vastraveda.features.feature2_map;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Feature 2 - Map Service
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
    private final Map<String, RegionInfo> regions = new LinkedHashMap<>();

    public Feature2Service() {
        register(
            "Kashmir",
            new Color(70, 123, 191),
            "Pashmina and layered winter drapes define Kashmir's textile identity.",
            "Best known for fine wool craftsmanship and garments built for mountain climates.",
            points(34, 9, 45, 6, 52, 13, 48, 20, 38, 19, 32, 14)
        );
        register(
            "Punjab",
            new Color(198, 103, 57),
            "Punjab celebrates vivid embroidery, festive dupattas, and practical silhouettes.",
            "Phulkari work turns everyday cotton into celebratory clothing full of color and floral rhythm.",
            points(30, 22, 40, 20, 46, 28, 39, 35, 29, 31)
        );
        register(
            "Rajasthan",
            new Color(187, 92, 52),
            "Rajasthan is known for desert-ready drapes, mirror work, and royal court influence.",
            "Bandhani, leheriya, and angrakha styles bring movement and bold contrast to regional dress.",
            points(20, 31, 35, 30, 40, 44, 30, 54, 17, 47, 15, 38)
        );
        register(
            "Gujarat",
            new Color(233, 154, 68),
            "Gujarat blends tie-dye traditions with rich embroidery and festive silhouettes.",
            "Garments here often feature handcrafted detail, bright palettes, and celebratory layering.",
            points(14, 49, 30, 55, 28, 66, 18, 69, 11, 58)
        );
        register(
            "Uttar Pradesh",
            new Color(149, 104, 189),
            "Uttar Pradesh brings together royal weaving lineages and ceremonial dressing traditions.",
            "Banarasi silk remains one of India's most iconic textiles for weddings and heritage occasions.",
            points(39, 29, 58, 28, 66, 34, 58, 41, 43, 39)
        );
        register(
            "Bihar",
            new Color(141, 118, 94),
            "Bihar sits at a cultural crossroads where woven and draped traditions meet.",
            "Regional attire often favors comfortable fabrics with ceremonial styling for festivals and rituals.",
            points(58, 35, 68, 35, 73, 41, 65, 47, 56, 43)
        );
        register(
            "West Bengal",
            new Color(184, 86, 130),
            "West Bengal is associated with elegant drapes, soft fabrics, and literary-cultural refinement.",
            "Festive clothing here balances understated grace with statement borders, weaves, and artisan detail.",
            points(66, 38, 76, 37, 78, 50, 72, 57, 66, 49)
        );
        register(
            "Assam",
            new Color(67, 153, 169),
            "Assam's identity is shaped by silk heritage, especially Muga and Pat traditions.",
            "The mekhela chador is central to ceremonial and festive dress across the state.",
            points(79, 35, 92, 35, 95, 42, 91, 48, 80, 45)
        );
        register(
            "Manipur",
            new Color(121, 164, 76),
            "Manipur's attire is deeply tied to dance, ritual, and woven wrap silhouettes.",
            "Traditional dress often emphasizes graceful structure and strong links to community performance culture.",
            points(89, 49, 96, 49, 97, 58, 91, 60, 87, 55)
        );
        register(
            "Odisha",
            new Color(196, 103, 103),
            "Odisha is home to handloom traditions that pair temple aesthetics with coastal craft histories.",
            "Regional clothing often features ikat patterning, refined borders, and ceremonial draping.",
            points(60, 49, 71, 50, 73, 63, 65, 69, 58, 60)
        );
        register(
            "Maharashtra",
            new Color(91, 154, 101),
            "Maharashtra combines courtly influence with practical drapes designed for movement.",
            "Nauvari-inspired silhouettes, layered menswear, and festive textiles shape its clothing vocabulary.",
            points(31, 55, 48, 52, 56, 61, 50, 74, 35, 72, 28, 64)
        );
        register(
            "Telangana",
            new Color(116, 93, 178),
            "Telangana is celebrated for bold ikat geometry and ceremonial silk weaving centers.",
            "Pochampally's graphic patterns give the state's clothing traditions a distinct visual language.",
            points(49, 54, 60, 53, 63, 65, 56, 75, 47, 68)
        );
        register(
            "Karnataka",
            new Color(71, 145, 180),
            "Karnataka's dress traditions range from temple-town silks to everyday drapes with regional variation.",
            "The state's textile identity is built on weaving excellence, muted grandeur, and occasion-driven styling.",
            points(33, 73, 46, 74, 49, 89, 39, 96, 31, 86)
        );
        register(
            "Kerala",
            new Color(94, 184, 152),
            "Kerala favors restrained elegance with ivory textiles, gold borders, and climate-friendly fabrics.",
            "Kasavu attire stands out for its calm palette and strong connection to festival dressing.",
            points(35, 95, 40, 96, 42, 110, 37, 121, 32, 118, 31, 103)
        );
        register(
            "Tamil Nadu",
            new Color(171, 120, 73),
            "Tamil Nadu's clothing heritage is anchored in lustrous silks and temple-inspired motifs.",
            "Kanjivaram weaving gives the state a signature bridal and ceremonial identity across India.",
            points(41, 90, 57, 88, 62, 102, 57, 116, 43, 118, 38, 105)
        );
    }

    public List<RegionInfo> getRegions() {
        return new ArrayList<>(regions.values());
    }

    public RegionInfo getRegion(String name) {
        return regions.get(name);
    }

    public List<ClothingItem> getItemsForRegion(String region) {
        return FilterUtils.filterByRegion(region);
    }

    public String getSelectionSummary(RegionInfo region, List<ClothingItem> items) {
        if (region == null) {
            return "Select a highlighted state on the map to explore its traditional clothing.";
        }
        if (items.isEmpty()) {
            return region.getFact() + " No clothing entries are stored for this state yet.";
        }
        return region.getFact() + " " + items.size() + " clothing entr" + (items.size() == 1 ? "y is" : "ies are") +
            " currently available in this region.";
    }

    public String truncate(String text, int maxLen) {
        if (text == null) {
            return "";
        }
        return text.length() <= maxLen ? text : text.substring(0, maxLen - 3) + "...";
    }

    private void register(String name, Color accent, String fact, String cultureNote, Point2D.Double[] points) {
        regions.put(name, new RegionInfo(name, accent, fact, cultureNote, Arrays.asList(points)));
    }

    private Point2D.Double[] points(double... coordinates) {
        Point2D.Double[] values = new Point2D.Double[coordinates.length / 2];
        for (int i = 0; i < coordinates.length; i += 2) {
            values[i / 2] = new Point2D.Double(coordinates[i], coordinates[i + 1]);
        }
        return values;
    }

    public static class RegionInfo {
        private final String name;
        private final Color accent;
        private final String fact;
        private final String cultureNote;
        private final List<Point2D.Double> points;

        RegionInfo(String name, Color accent, String fact, String cultureNote, List<Point2D.Double> points) {
            this.name = name;
            this.accent = accent;
            this.fact = fact;
            this.cultureNote = cultureNote;
            this.points = Collections.unmodifiableList(points);
        }

        public String getName() {
            return name;
        }

        public Color getAccent() {
            return accent;
        }

        public String getFact() {
            return fact;
        }

        public String getCultureNote() {
            return cultureNote;
        }

        public List<Point2D.Double> getPoints() {
            return points;
        }
    }
}
