package vastraveda.features.feature9_gallery;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cultural accuracy issues — in-memory store.
 */
public class Feature9Service {

    public static final String STATUS_OPEN = "Open";
    public static final String STATUS_REVIEW = "In review";
    public static final String STATUS_RESOLVED = "Resolved";

    public static final class Issue {
        public final int id;
        public final String garmentName;
        public final String title;
        public final String detail;
        public final String reporter;
        public String status;
        public String moderatorNote;

        public Issue(int id, String garmentName, String title, String detail, String reporter) {
            this.id = id;
            this.garmentName = garmentName;
            this.title = title;
            this.detail = detail;
            this.reporter = reporter;
            this.status = STATUS_OPEN;
            this.moderatorNote = "";
        }
    }

    private final List<Issue> issues = new ArrayList<>();
    private int nextId = 1;

    public Feature9Service() {
        issues.add(new Issue(nextId++, "Dhoti", "Region label too broad", "Pan-India may hide state variants.", "curator1"));
        issues.get(0).status = STATUS_REVIEW;
    }

    public List<ClothingItem> getGarments() {
        return DataStore.getAllItems();
    }

    public List<Issue> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public void addIssue(String garment, String title, String detail, String reporter) {
        issues.add(0, new Issue(nextId++, garment, title, detail, reporter));
    }

    public void updateStatus(Issue issue, String status, String note) {
        issue.status = status;
        if (note != null) {
            issue.moderatorNote = note;
        }
    }
}
