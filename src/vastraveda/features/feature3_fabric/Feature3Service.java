package vastraveda.features.feature3_fabric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    private final List<FabricOrigin> origins = Arrays.asList(
        new FabricOrigin(
            1,
            "Khadi",
            "Gujarat",
            1918,
            "Khadi is a hand-spun and handwoven fabric central to self-reliance and swadeshi identity.",
            Arrays.asList(
                new SpreadEvent("Maharashtra", 1920, "Worn in national movements and village cooperatives."),
                new SpreadEvent("Uttar Pradesh", 1921, "Khadi centers promoted spinning and local weaving."),
                new SpreadEvent("West Bengal", 1922, "Urban swadeshi adoption increased demand."),
                new SpreadEvent("Tamil Nadu", 1925, "Khadi became common in social reform movements.")
            )
        ),
        new FabricOrigin(
            2,
            "Silk",
            "Assam",
            200,
            "Indian silk traditions include muga, mulberry, tussar, and eri lineages across regions.",
            Arrays.asList(
                new SpreadEvent("West Bengal", 700, "Murshidabad silk production expanded."),
                new SpreadEvent("Karnataka", 1200, "Mysore silk weaving evolved into organized production."),
                new SpreadEvent("Tamil Nadu", 1500, "Kanchipuram temple silk traditions matured."),
                new SpreadEvent("Uttar Pradesh", 1600, "Banaras silk-zari brocades grew under royal patronage.")
            )
        ),
        new FabricOrigin(
            3,
            "Cotton",
            "Indus Valley / Gujarat",
            -2500,
            "Cotton is among India's oldest textile fibers and forms the base of everyday traditional wear.",
            Arrays.asList(
                new SpreadEvent("Rajasthan", -1800, "Trade routes diffused cotton cloth across western regions."),
                new SpreadEvent("Tamil Nadu", 300, "Fine cotton weaving strengthened in southern kingdoms."),
                new SpreadEvent("Punjab", 900, "Agrarian cotton and textile use increased regionally."),
                new SpreadEvent("Maharashtra", 1700, "Deccan textile hubs scaled cotton weaving.")
            )
        )
    );

    public List<FabricOrigin> getAllOrigins() {
        return Collections.unmodifiableList(origins);
    }

    public FabricOrigin getOriginByFabric(String fabricName) {
        if (fabricName == null || fabricName.trim().isEmpty()) {
            return null;
        }
        for (FabricOrigin origin : origins) {
            if (origin.getFabricName().equalsIgnoreCase(fabricName.trim())) {
                return origin;
            }
        }
        return null;
    }

    public List<String> getTrackableFabricNames() {
        List<String> names = new ArrayList<>();
        for (FabricOrigin origin : origins) {
            names.add(origin.getFabricName());
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
}
