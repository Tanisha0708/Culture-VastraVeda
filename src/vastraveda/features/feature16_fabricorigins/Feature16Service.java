package vastraveda.features.feature16_fabricorigins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Feature16Service {

    public static final class SpreadEvent {
        private final String region;
        private final int year;
        private final String note;

        public SpreadEvent(String region, int year, String note) {
            this.region = region;
            this.year = year;
            this.note = note;
        }

        public String getRegion() {
            return region;
        }

        public int getYear() {
            return year;
        }

        public String getNote() {
            return note;
        }
    }

    public static final class FabricOrigin {
        private final int id;
        private final String fabricName;
        private final String originRegion;
        private final int originYear;
        private final String description;
        private final String icon;
        private final List<SpreadEvent> spreadHistory;

        public FabricOrigin(int id, String fabricName, String originRegion, int originYear,
                            String description, String icon, List<SpreadEvent> spreadHistory) {
            this.id = id;
            this.fabricName = fabricName;
            this.originRegion = originRegion;
            this.originYear = originYear;
            this.description = description;
            this.icon = icon;
            this.spreadHistory = spreadHistory;
        }

        public int getId() {
            return id;
        }

        public String getFabricName() {
            return fabricName;
        }

        public String getOriginRegion() {
            return originRegion;
        }

        public int getOriginYear() {
            return originYear;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public List<SpreadEvent> getSpreadHistory() {
            return spreadHistory;
        }
    }

    private final List<FabricOrigin> fabrics = Arrays.asList(
        new FabricOrigin(
            1, "Khadi", "Gujarat", 1918,
            "Khadi is hand-spun, handwoven cloth that became a symbol of self-reliance and freedom.",
            "🪢",
            Arrays.asList(
                new SpreadEvent("Maharashtra", 1920, "Khadi production integrated with village cooperatives."),
                new SpreadEvent("Uttar Pradesh", 1921, "Khadi stores and charkha campaigns increased adoption."),
                new SpreadEvent("West Bengal", 1922, "Urban swadeshi groups normalized khadi for daily wear."),
                new SpreadEvent("Tamil Nadu", 1925, "Handloom associations promoted khadi in the south.")
            )
        ),
        new FabricOrigin(
            2, "Silk", "Assam", 200,
            "Indian silk evolved through regional lineages such as muga, eri, mulberry, and tussar.",
            "🐛",
            Arrays.asList(
                new SpreadEvent("West Bengal", 700, "Murshidabad emerged as a major silk weaving center."),
                new SpreadEvent("Karnataka", 1200, "Mulberry silk production expanded in Mysore region."),
                new SpreadEvent("Tamil Nadu", 1500, "Kanchipuram refined temple-border silk traditions."),
                new SpreadEvent("Uttar Pradesh", 1600, "Banaras advanced zari-rich silk brocade weaving.")
            )
        ),
        new FabricOrigin(
            3, "Cotton", "Indus Valley / Gujarat", -2500,
            "Cotton weaving in the subcontinent has ancient roots and shaped everyday and ceremonial clothing.",
            "🌾",
            Arrays.asList(
                new SpreadEvent("Rajasthan", -1800, "Trade routes expanded cotton cloth circulation."),
                new SpreadEvent("Tamil Nadu", 300, "Fine cotton looms developed in southern weaving centers."),
                new SpreadEvent("Punjab", 900, "Cotton became central to agrarian textile economies."),
                new SpreadEvent("Maharashtra", 1700, "Deccan weaving networks scaled cotton availability.")
            )
        )
    );

    public List<FabricOrigin> getAllFabrics() {
        return Collections.unmodifiableList(fabrics);
    }

    public FabricOrigin getFabric(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (FabricOrigin fabric : fabrics) {
            if (fabric.getFabricName().equalsIgnoreCase(name.trim())) {
                return fabric;
            }
        }
        return null;
    }

    public List<String> getFabricNames() {
        List<String> names = new ArrayList<>();
        for (FabricOrigin fabric : fabrics) {
            names.add(fabric.getFabricName());
        }
        return names;
    }

    public String formatYear(int year) {
        return year < 0 ? Math.abs(year) + " BCE" : year + " CE";
    }
}
