package vastraveda.features.feature2_map;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Feature 2 — Cultural tagging: tags, attachments to items (by name), filter helpers.
 */
public class Feature2Service {

    public static final class CulturalTag {
        private final String id;
        private String name;

        public CulturalTag(String id, String name) {
            this.id = Objects.requireNonNull(id);
            this.name = Objects.requireNonNull(name);
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final List<CulturalTag> TAGS = new ArrayList<>();
    private static final Map<String, Set<String>> ITEM_NAME_TO_TAG_IDS = new LinkedHashMap<>();

    static {
        seedTagsAndAttachments();
    }

    private static void addTag(String id, String name) {
        TAGS.add(new CulturalTag(id, name));
    }

    private static void attach(String itemName, String tagId) {
        ITEM_NAME_TO_TAG_IDS.computeIfAbsent(itemName, k -> new LinkedHashSet<>()).add(tagId);
    }

    private static void seedTagsAndAttachments() {
        addTag("t-wedding", "Wedding");
        addTag("t-festival", "Festival");
        addTag("t-bridal", "Bridal");
        addTag("t-casual", "Everyday");
        addTag("t-formal", "Formal");
        addTag("t-north", "North India");
        addTag("t-south", "South India");
        addTag("t-east", "East & Northeast");
        addTag("t-west", "West India");
        addTag("t-silk", "Silk heritage");
        addTag("t-cotton", "Cotton craft");
        addTag("t-unisex", "Unisex");

        for (ClothingItem it : DataStore.getAllItems()) {
            String n = it.getName();
            String r = it.getRegion().toLowerCase(Locale.ROOT);
            String o = it.getOccasion().toLowerCase(Locale.ROOT);
            String f = it.getFabricType().toLowerCase(Locale.ROOT);

            if (o.contains("wedding")) {
                attach(n, "t-wedding");
                attach(n, "t-bridal");
            }
            if (o.contains("festival") || o.contains("onam") || o.contains("eid")) {
                attach(n, "t-festival");
            }
            if (o.contains("casual")) {
                attach(n, "t-casual");
            }
            if (o.contains("formal") || o.contains("winter")) {
                attach(n, "t-formal");
            }
            if (it.getGender().equalsIgnoreCase("Unisex")) {
                attach(n, "t-unisex");
            }
            if (f.contains("silk") || f.contains("brocade") || f.contains("muga") || f.contains("pashmina")) {
                attach(n, "t-silk");
            }
            if (f.contains("cotton")) {
                attach(n, "t-cotton");
            }
            if (r.contains("north") || r.contains("punjab") || r.contains("rajasthan")
                || r.contains("gujarat") || r.contains("uttar") || r.contains("kashmir")) {
                attach(n, "t-north");
            }
            if (r.contains("tamil") || r.contains("telangana") || r.contains("kerala") || r.contains("karnataka")) {
                attach(n, "t-south");
            }
            if (r.contains("assam") || r.contains("manipur") || r.contains("bengal") || r.contains("odisha")) {
                attach(n, "t-east");
            }
            if (r.contains("maharashtra") || r.contains("goa")) {
                attach(n, "t-west");
            }
            if (r.contains("pan-india")) {
                attach(n, "t-north");
                attach(n, "t-south");
            }
        }
    }

    public List<CulturalTag> getAllTagsSorted() {
        synchronized (TAGS) {
            return TAGS.stream()
                .sorted(Comparator.comparing(CulturalTag::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        }
    }

    public CulturalTag getTagById(String id) {
        if (id == null) {
            return null;
        }
        synchronized (TAGS) {
            for (CulturalTag t : TAGS) {
                if (t.getId().equals(id)) {
                    return t;
                }
            }
        }
        return null;
    }

    public CulturalTag createTag(String name) {
        String trimmed = name == null ? "" : name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be empty.");
        }
        synchronized (TAGS) {
            for (CulturalTag t : TAGS) {
                if (t.getName().equalsIgnoreCase(trimmed)) {
                    throw new IllegalArgumentException("A tag with that name already exists.");
                }
            }
            CulturalTag tag = new CulturalTag("t-" + UUID.randomUUID().toString().substring(0, 8), trimmed);
            TAGS.add(tag);
            return tag;
        }
    }

    public void updateTag(String id, String newName) {
        CulturalTag t = getTagById(id);
        if (t == null) {
            throw new IllegalArgumentException("Unknown tag id.");
        }
        String trimmed = newName == null ? "" : newName.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be empty.");
        }
        synchronized (TAGS) {
            for (CulturalTag other : TAGS) {
                if (!other.getId().equals(id) && other.getName().equalsIgnoreCase(trimmed)) {
                    throw new IllegalArgumentException("A tag with that name already exists.");
                }
            }
            t.setName(trimmed);
        }
    }

    public void deleteTag(String id) {
        synchronized (TAGS) {
            TAGS.removeIf(t -> t.getId().equals(id));
            for (Set<String> set : ITEM_NAME_TO_TAG_IDS.values()) {
                set.remove(id);
            }
        }
    }

    public Set<String> getTagIdsForItemName(String itemName) {
        Set<String> set = ITEM_NAME_TO_TAG_IDS.get(itemName);
        if (set == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new LinkedHashSet<>(set));
    }

    public List<String> getTagNamesForItemName(String itemName) {
        List<String> out = new ArrayList<>();
        for (String tid : getTagIdsForItemName(itemName)) {
            CulturalTag t = getTagById(tid);
            if (t != null) {
                out.add(t.getName());
            }
        }
        Collections.sort(out, String.CASE_INSENSITIVE_ORDER);
        return out;
    }

    public void setTagIdsForItem(String itemName, Set<String> tagIds) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name required.");
        }
        Set<String> copy = new LinkedHashSet<>();
        if (tagIds != null) {
            for (String id : tagIds) {
                if (getTagById(id) != null) {
                    copy.add(id);
                }
            }
        }
        synchronized (TAGS) {
            if (copy.isEmpty()) {
                ITEM_NAME_TO_TAG_IDS.remove(itemName);
            } else {
                ITEM_NAME_TO_TAG_IDS.put(itemName, copy);
            }
        }
    }

    public List<ClothingItem> getAllItemsFilteredByTagId(String tagIdOrNull) {
        List<ClothingItem> all = new ArrayList<>(DataStore.getAllItems());
        if (tagIdOrNull == null || tagIdOrNull.isEmpty()) {
            all.sort(Comparator.comparing(ClothingItem::getName, String.CASE_INSENSITIVE_ORDER));
            return all;
        }
        List<ClothingItem> out = new ArrayList<>();
        for (ClothingItem it : all) {
            Set<String> ids = ITEM_NAME_TO_TAG_IDS.getOrDefault(it.getName(), Collections.emptySet());
            if (ids.contains(tagIdOrNull)) {
                out.add(it);
            }
        }
        out.sort(Comparator.comparing(ClothingItem::getName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    public String truncate(String text, int maxLen) {
        if (text == null) {
            return "";
        }
        return text.length() <= maxLen ? text : text.substring(0, maxLen) + "...";
    }
}
