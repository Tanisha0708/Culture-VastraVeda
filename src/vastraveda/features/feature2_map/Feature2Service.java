package vastraveda.features.feature2_map;

/**
 * Feature 2 — Map Service
 */
public class Feature2Service {

    public String truncate(String text, int maxLen) {
        if (text == null) return "";
        return text.length() <= maxLen ? text : text.substring(0, maxLen) + "...";
    }
}
