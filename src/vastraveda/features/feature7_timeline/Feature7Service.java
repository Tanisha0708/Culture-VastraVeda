package vastraveda.features.feature7_timeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Feature 7 — Fabric Origins Tracker Service
 */
public class Feature7Service {

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
        private final List<SpreadEvent> spreadHistory;

        public FabricOrigin(int id, String fabricName, String originRegion,
                            int originYear, String description, List<SpreadEvent> spreadHistory) {
            this.id = id;
            this.fabricName = fabricName;
            this.originRegion = originRegion;
            this.originYear = originYear;
            this.description = description;
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

        public List<SpreadEvent> getSpreadHistory() {
            return spreadHistory;
        }
    }

    private final List<FabricOrigin> fabrics = Arrays.asList(
        new FabricOrigin(
            1,
            "Khadi",
            "Gujarat",
            1918,
            "Hand-spun and handwoven fabric associated with India's self-reliance movement.",
            Arrays.asList(
                new SpreadEvent("Maharashtra", 1920, "Adopted in nationalist campaigns and rural cooperatives."),
                new SpreadEvent("Uttar Pradesh", 1921, "Khadi centers promoted village spinning and weaving."),
                new SpreadEvent("West Bengal", 1922, "Urban swadeshi groups encouraged khadi garments."),
                new SpreadEvent("Tamil Nadu", 1925, "Local handloom unions integrated khadi in daily attire.")
            )
        ),
        new FabricOrigin(
            2,
            "Silk",
            "Assam",
            200,
            "India's silk traditions include muga, eri, mulberry, and tussar weaving lineages.",
            Arrays.asList(
                new SpreadEvent("West Bengal", 700, "Silk weaving hubs grew around Murshidabad."),
                new SpreadEvent("Karnataka", 1200, "Mysore region expanded mulberry silk production."),
                new SpreadEvent("Tamil Nadu", 1500, "Kanchipuram developed temple-border silk weaving."),
                new SpreadEvent("Uttar Pradesh", 1600, "Banaras brocade weaving flourished with silk-zari blends.")
            )
        ),
        new FabricOrigin(
            3,
            "Cotton",
            "Indus Valley / Gujarat",
            -2500,
            "Cotton weaving in India dates back to ancient civilization and became the base of many regional textiles.",
            Arrays.asList(
                new SpreadEvent("Rajasthan", -1800, "Trade routes moved cotton cloth across western India."),
                new SpreadEvent("Tamil Nadu", 300, "Fine cotton weaving became established in southern looms."),
                new SpreadEvent("Punjab", 900, "Cotton became central to regional dress and agriculture."),
                new SpreadEvent("Maharashtra", 1700, "Deccan weaving networks expanded cotton availability.")
            )
        )
    );

    public List<FabricOrigin> getAllFabricOrigins() {
        return Collections.unmodifiableList(fabrics);
    }

    public FabricOrigin getFabricByName(String fabricName) {
        if (fabricName == null || fabricName.trim().isEmpty()) {
            return null;
        }
        for (FabricOrigin fabric : fabrics) {
            if (fabric.getFabricName().equalsIgnoreCase(fabricName.trim())) {
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
        if (year < 0) {
            return Math.abs(year) + " BCE";
        }
        return year + " CE";
    }
}
