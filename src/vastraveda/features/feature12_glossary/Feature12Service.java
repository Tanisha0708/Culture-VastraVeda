package vastraveda.features.feature12_glossary;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Feature 12 — Clothing Version Control: snapshot history per garment (DataStore read-only).
 */
public class Feature12Service {

    public static final class Snapshot {
        private final int version;
        private final String label;
        private final String name;
        private final String region;
        private final String fabricType;
        private final String occasion;
        private final String gender;
        private final String description;
        private final String era;
        private final String careInstructions;

        public Snapshot(int version, String label, ClothingItem item) {
            this.version = version;
            this.label = label;
            this.name = item.getName();
            this.region = item.getRegion();
            this.fabricType = item.getFabricType();
            this.occasion = item.getOccasion();
            this.gender = item.getGender();
            this.description = item.getDescription();
            this.era = item.getEra();
            this.careInstructions = item.getCareInstructions();
        }

        private Snapshot(int version, String label, Snapshot from) {
            this.version = version;
            this.label = label;
            this.name = from.name;
            this.region = from.region;
            this.fabricType = from.fabricType;
            this.occasion = from.occasion;
            this.gender = from.gender;
            this.description = from.description;
            this.era = from.era;
            this.careInstructions = from.careInstructions;
        }

        public int getVersion() {
            return version;
        }

        public String getLabel() {
            return label;
        }

        public Map<String, String> asMap() {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("Name", name);
            m.put("Region", region);
            m.put("Fabric", fabricType);
            m.put("Occasion", occasion);
            m.put("Gender", gender);
            m.put("Era", era);
            m.put("Care", careInstructions);
            m.put("Description", description);
            return m;
        }
    }

    public static final class DiffLine {
        public final String field;
        public final String oldVal;
        public final String newVal;
        public final boolean same;

        public DiffLine(String field, String oldVal, String newVal) {
            this.field = field;
            this.oldVal = oldVal != null ? oldVal : "";
            this.newVal = newVal != null ? newVal : "";
            this.same = this.oldVal.equals(this.newVal);
        }
    }

    private final Map<String, List<Snapshot>> historyByGarment = new HashMap<>();
    private final Map<String, Integer> nextVersion = new HashMap<>();

    public List<String> getGarmentNames() {
        List<String> names = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            names.add(item.getName());
        }
        Collections.sort(names);
        return names;
    }

    public ClothingItem findItem(String name) {
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public Snapshot saveVersion(String garmentName, String label) {
        ClothingItem item = findItem(garmentName);
        if (item == null) {
            return null;
        }
        int v = nextVersion.getOrDefault(garmentName, 0) + 1;
        nextVersion.put(garmentName, v);
        Snapshot snap = new Snapshot(v, label == null || label.isEmpty() ? "v" + v : label, item);
        historyByGarment.computeIfAbsent(garmentName, k -> new ArrayList<>()).add(snap);
        return snap;
    }

    public List<Snapshot> getHistory(String garmentName) {
        return new ArrayList<>(historyByGarment.getOrDefault(garmentName, Collections.emptyList()));
    }

    public List<DiffLine> compare(Snapshot older, Snapshot newer) {
        if (older == null || newer == null) {
            return Collections.emptyList();
        }
        Map<String, String> a = older.asMap();
        Map<String, String> b = newer.asMap();
        List<DiffLine> lines = new ArrayList<>();
        for (String key : a.keySet()) {
            lines.add(new DiffLine(key, a.get(key), b.get(key)));
        }
        return lines;
    }

    public Snapshot rollback(String garmentName, Snapshot target) {
        if (target == null) {
            return null;
        }
        int v = nextVersion.getOrDefault(garmentName, 0) + 1;
        nextVersion.put(garmentName, v);
        Snapshot rolled = new Snapshot(v, "Rollback from v" + target.getVersion(), target);
        historyByGarment.computeIfAbsent(garmentName, k -> new ArrayList<>()).add(rolled);
        return rolled;
    }
}
