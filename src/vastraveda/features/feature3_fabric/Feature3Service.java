package vastraveda.features.feature3_fabric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

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

    // ── Fabric substitution guide ─────────────────────────────────────────

    public static final class FabricAlternative {
        private final String fabricName;
        private final String explanation;
        private final String contextHint;

        public FabricAlternative(String fabricName, String explanation, String contextHint) {
            this.fabricName = Objects.requireNonNull(fabricName);
            this.explanation = Objects.requireNonNull(explanation);
            this.contextHint = contextHint == null ? "" : contextHint;
        }

        public String getFabricName() {
            return fabricName;
        }

        public String getExplanation() {
            return explanation;
        }

        public String getContextHint() {
            return contextHint;
        }
    }

    public static final class PendingSubstitution {
        private final String id;
        private final String baseFabric;
        private final String suggestedFabric;
        private final String explanation;
        private final String submitterNote;

        public PendingSubstitution(String id, String baseFabric, String suggestedFabric,
                                   String explanation, String submitterNote) {
            this.id = id;
            this.baseFabric = baseFabric;
            this.suggestedFabric = suggestedFabric;
            this.explanation = explanation;
            this.submitterNote = submitterNote;
        }

        public String getId() {
            return id;
        }

        public String getBaseFabric() {
            return baseFabric;
        }

        public String getSuggestedFabric() {
            return suggestedFabric;
        }

        public String getExplanation() {
            return explanation;
        }

        public String getSubmitterNote() {
            return submitterNote;
        }
    }

    private static final Map<String, List<FabricAlternative>> SUBSTITUTIONS = new LinkedHashMap<>();
    private static final Map<String, List<FabricAlternative>> USER_APPROVED = new LinkedHashMap<>();
    private static final List<PendingSubstitution> PENDING = new CopyOnWriteArrayList<>();

    static {
        SUBSTITUTIONS.put("Silk", Arrays.asList(
            new FabricAlternative("Cotton (fine)", "Breathable for humid climates; less sheen but comfortable for long wear.",
                "Hot weather · affordability"),
            new FabricAlternative("Viscose / Art silk", "Drape similar to silk at lower cost; good for festive looks on a budget.",
                "Affordability"),
            new FabricAlternative("Tussar", "Natural silk with earthy texture; often more affordable than mulberry silk.",
                "Texture · regional craft")
        ));
        SUBSTITUTIONS.put("Cotton", Arrays.asList(
            new FabricAlternative("Linen", "Crisp and cool; wrinkles more but excellent in dry heat.",
                "Hot dry weather"),
            new FabricAlternative("Khadi", "Hand-spun cotton with cultural heritage; similar care, more structured drape.",
                "Heritage · everyday"),
            new FabricAlternative("Viscose", "Softer drape for dresses; less durable than pure cotton.",
                "Drape · cost")
        ));
        SUBSTITUTIONS.put("Wool", Arrays.asList(
            new FabricAlternative("Pashmina blend", "Softer hand-feel; often lighter than heavy woolens.",
                "Comfort · luxury"),
            new FabricAlternative("Fleece / Acrylic blend", "Budget warmth for casual layering; not traditional but practical.",
                "Affordability · casual")
        ));
        SUBSTITUTIONS.put("Linen", Arrays.asList(
            new FabricAlternative("Cotton", "Easier to maintain; less crisp but widely available.",
                "Care · cost"),
            new FabricAlternative("Khadi cotton", "Similar breathability with Indian craft context.",
                "Heritage")
        ));
        SUBSTITUTIONS.put("Brocade", Arrays.asList(
            new FabricAlternative("Jacquard cotton", "Patterned weave without metallic zari; lighter maintenance.",
                "Affordability · care"),
            new FabricAlternative("Silk with printed motifs", "Visual richness without heavy brocade weight.",
                "Weight · comfort")
        ));
        SUBSTITUTIONS.put("Pashmina", Arrays.asList(
            new FabricAlternative("Fine wool shawl", "Warmth at lower price; verify ethical sourcing.",
                "Affordability"),
            new FabricAlternative("Pashmina blend", "Mix fibers for durability; softer price point.",
                "Durability · cost")
        ));
    }

    /** Normalizes keys like "Silk/Cotton" → first token for lookup. */
    public String normalizeFabricKey(String fabricFromItem) {
        if (fabricFromItem == null || fabricFromItem.trim().isEmpty()) {
            return "";
        }
        String first = fabricFromItem.split("/")[0].trim();
        for (String key : SUBSTITUTIONS.keySet()) {
            if (first.toLowerCase(Locale.ROOT).startsWith(key.toLowerCase(Locale.ROOT))) {
                return key;
            }
        }
        for (String key : SUBSTITUTIONS.keySet()) {
            if (key.toLowerCase(Locale.ROOT).contains(first.toLowerCase(Locale.ROOT))
                || first.toLowerCase(Locale.ROOT).contains(key.toLowerCase(Locale.ROOT))) {
                return key;
            }
        }
        return first;
    }

    public List<FabricAlternative> getAlternativesForFabric(String fabricFromItem) {
        String key = normalizeFabricKey(fabricFromItem);
        List<FabricAlternative> out = new ArrayList<>();
        List<FabricAlternative> builtIn = SUBSTITUTIONS.get(key);
        if (builtIn != null) {
            out.addAll(builtIn);
        }
        synchronized (USER_APPROVED) {
            List<FabricAlternative> extra = USER_APPROVED.get(key);
            if (extra != null) {
                out.addAll(extra);
            }
        }
        return Collections.unmodifiableList(out);
    }

    public PendingSubstitution submitSuggestion(String baseFabric, String suggestedFabric,
                                                  String explanation, String submitterNote) {
        String id = "sub-" + UUID.randomUUID().toString().substring(0, 8);
        PendingSubstitution p = new PendingSubstitution(id,
            baseFabric == null ? "" : baseFabric.trim(),
            suggestedFabric == null ? "" : suggestedFabric.trim(),
            explanation == null ? "" : explanation.trim(),
            submitterNote == null ? "" : submitterNote.trim());
        PENDING.add(p);
        return p;
    }

    public List<PendingSubstitution> getPendingSuggestions() {
        return Collections.unmodifiableList(new ArrayList<>(PENDING));
    }

    public void approveSuggestion(String id) {
        for (PendingSubstitution p : new ArrayList<>(PENDING)) {
            if (p.getId().equals(id)) {
                String key = normalizeFabricKey(p.getBaseFabric());
                if (!p.getSuggestedFabric().isEmpty()) {
                    FabricAlternative alt = new FabricAlternative(
                        p.getSuggestedFabric(),
                        p.getExplanation().isEmpty()
                            ? "Community-suggested alternative."
                            : p.getExplanation(),
                        "User suggestion · approved");
                    synchronized (USER_APPROVED) {
                        USER_APPROVED.computeIfAbsent(key, k -> new ArrayList<>()).add(alt);
                    }
                }
                PENDING.remove(p);
                return;
            }
        }
    }

    public void rejectSuggestion(String id) {
        PENDING.removeIf(p -> p.getId().equals(id));
    }
}
