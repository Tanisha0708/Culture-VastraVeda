package vastraveda.features.feature4_calendar;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Feature 4 — Multilingual contributions and translation workflow (in-memory).
 */
public class Feature4Service {

    public static final String FIELD_DESCRIPTION = "description";

    public static final class LangOption {
        private final String code;
        private final String label;

        public LangOption(String code, String label) {
            this.code = code;
            this.label = label;
        }

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label + " (" + code + ")";
        }
    }

    public static final class PendingContribution {
        private final String id;
        private final String itemName;
        private final String field;
        private final String sourceLang;
        private final String text;
        private String status;

        public PendingContribution(String id, String itemName, String field,
                                   String sourceLang, String text, String status) {
            this.id = id;
            this.itemName = itemName;
            this.field = field;
            this.sourceLang = sourceLang;
            this.text = text;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getItemName() {
            return itemName;
        }

        public String getField() {
            return field;
        }

        public String getSourceLang() {
            return sourceLang;
        }

        public String getText() {
            return text;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    private static final Map<String, Map<String, String>> TEXT_BY_ITEM_AND_LANG = new LinkedHashMap<>();
    private static final List<PendingContribution> QUEUE = new CopyOnWriteArrayList<>();

    static {
        for (ClothingItem it : DataStore.getAllItems()) {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("en", it.getDescription());
            TEXT_BY_ITEM_AND_LANG.put(it.getName(), m);
        }
    }

    public List<LangOption> getLanguageOptions() {
        return Collections.unmodifiableList(ArraysAsLangOptions());
    }

    private static List<LangOption> ArraysAsLangOptions() {
        List<LangOption> list = new ArrayList<>();
        list.add(new LangOption("en", "English"));
        list.add(new LangOption("hi", "Hindi (हिन्दी)"));
        list.add(new LangOption("ta", "Tamil (தமிழ்)"));
        list.add(new LangOption("bn", "Bengali (বাংলা)"));
        list.add(new LangOption("mr", "Marathi (मराठी)"));
        return list;
    }

    public String getDisplayLabelForCode(String code) {
        if (code == null) {
            return "";
        }
        for (LangOption o : ArraysAsLangOptions()) {
            if (o.getCode().equalsIgnoreCase(code)) {
                return o.getLabel();
            }
        }
        return code.toUpperCase(Locale.ROOT);
    }

    /**
     * Localized text for an item field; falls back to English, then live {@link ClothingItem} description.
     */
    public String getLocalizedText(String itemName, String field, String langCode) {
        if (!FIELD_DESCRIPTION.equals(field)) {
            return "";
        }
        Map<String, String> langs = TEXT_BY_ITEM_AND_LANG.get(itemName);
        if (langs != null) {
            String en = langs.get("en");
            if (langCode != null && langs.containsKey(langCode.toLowerCase(Locale.ROOT))) {
                String t = langs.get(langCode.toLowerCase(Locale.ROOT));
                if (t != null && !t.trim().isEmpty()) {
                    return t;
                }
            }
            if (en != null && !en.trim().isEmpty()) {
                return en;
            }
        }
        for (ClothingItem it : DataStore.getAllItems()) {
            if (it.getName().equals(itemName)) {
                return it.getDescription();
            }
        }
        return "";
    }

    public void submitContribution(String itemName, String field, String sourceLang, String text) {
        Objects.requireNonNull(itemName);
        String lang = sourceLang == null ? "" : sourceLang.trim().toLowerCase(Locale.ROOT);
        if (lang.isEmpty()) {
            throw new IllegalArgumentException("Choose a language.");
        }
        String body = text == null ? "" : text.trim();
        if (body.isEmpty()) {
            throw new IllegalArgumentException("Enter description text.");
        }
        if (!FIELD_DESCRIPTION.equals(field)) {
            throw new IllegalArgumentException("Only description field is supported.");
        }
        boolean knownItem = false;
        for (ClothingItem it : DataStore.getAllItems()) {
            if (it.getName().equals(itemName)) {
                knownItem = true;
                break;
            }
        }
        if (!knownItem) {
            throw new IllegalArgumentException("Unknown clothing item.");
        }
        String id = "q-" + UUID.randomUUID().toString().substring(0, 8);
        QUEUE.add(new PendingContribution(id, itemName, field, lang, body, "PENDING"));
    }

    public List<PendingContribution> getPendingQueue() {
        List<PendingContribution> out = new ArrayList<>();
        for (PendingContribution p : QUEUE) {
            if ("PENDING".equalsIgnoreCase(p.getStatus())) {
                out.add(p);
            }
        }
        out.sort(Comparator.comparing(PendingContribution::getItemName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    public List<PendingContribution> getAllQueueForDashboard() {
        List<PendingContribution> out = new ArrayList<>(QUEUE);
        out.sort(Comparator.comparing(PendingContribution::getStatus)
            .thenComparing(PendingContribution::getItemName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    /**
     * Translator publishes source text into the catalog for its language, and optional English refinement.
     */
    public void publishTranslation(String pendingId, String englishTextOrNull) {
        PendingContribution found = null;
        for (PendingContribution p : QUEUE) {
            if (p.getId().equals(pendingId)) {
                found = p;
                break;
            }
        }
        if (found == null) {
            throw new IllegalArgumentException("Entry not found.");
        }
        if (!"PENDING".equalsIgnoreCase(found.getStatus())) {
            throw new IllegalArgumentException("Already processed.");
        }
        synchronized (TEXT_BY_ITEM_AND_LANG) {
            Map<String, String> m = TEXT_BY_ITEM_AND_LANG.computeIfAbsent(found.getItemName(), k -> new LinkedHashMap<>());
            m.put(found.getSourceLang(), found.getText());
            if (englishTextOrNull != null && !englishTextOrNull.trim().isEmpty()) {
                m.put("en", englishTextOrNull.trim());
            }
        }
        found.setStatus("DONE");
    }

    public void markInReview(String pendingId) {
        for (PendingContribution p : QUEUE) {
            if (p.getId().equals(pendingId) && "PENDING".equalsIgnoreCase(p.getStatus())) {
                p.setStatus("IN_REVIEW");
                return;
            }
        }
    }

    public List<String> getAllItemNamesSorted() {
        List<String> names = new ArrayList<>();
        for (ClothingItem it : DataStore.getAllItems()) {
            names.add(it.getName());
        }
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }
}
