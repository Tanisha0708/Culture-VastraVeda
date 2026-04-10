package vastraveda.features.feature10_stories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Feature 10 — Contributor leaderboard (mock activity data + scoring).
 */
public class Feature10Service {

    public static final String TIME_ALL = "All time";
    public static final String TIME_30 = "Last 30 days";
    public static final String TIME_7 = "Last 7 days";

    public static final String TYPE_ALL = "All types";
    public static final String TYPE_UI = "UI";
    public static final String TYPE_DATA = "Data";
    public static final String TYPE_DOCS = "Docs";
    public static final String TYPE_REVIEW = "Review";

    public static final class Contributor {
        public final String name;
        public final String state;
        /** Days since epoch mock for filtering */
        public final int lastActiveDay;
        public final int uiCommits;
        public final int dataCommits;
        public final int docCommits;
        public final int reviews;

        public Contributor(String name, String state, int lastActiveDay,
                           int uiCommits, int dataCommits, int docCommits, int reviews) {
            this.name = name;
            this.state = state;
            this.lastActiveDay = lastActiveDay;
            this.uiCommits = uiCommits;
            this.dataCommits = dataCommits;
            this.docCommits = docCommits;
            this.reviews = reviews;
        }

        public int totalScore() {
            return uiCommits * 2 + dataCommits * 3 + docCommits * 2 + reviews * 4;
        }

        public int scoreForTypeFilter(String type) {
            if (TYPE_UI.equals(type)) {
                return uiCommits * 2;
            }
            if (TYPE_DATA.equals(type)) {
                return dataCommits * 3;
            }
            if (TYPE_DOCS.equals(type)) {
                return docCommits * 2;
            }
            if (TYPE_REVIEW.equals(type)) {
                return reviews * 4;
            }
            return totalScore();
        }
    }

    public static final class RankRow {
        public final int rank;
        public final Contributor c;
        public final int displayScore;

        public RankRow(int rank, Contributor c, int displayScore) {
            this.rank = rank;
            this.c = c;
            this.displayScore = displayScore;
        }
    }

    private static final int TODAY = 20000;

    private final List<Contributor> contributors = new ArrayList<>();

    public Feature10Service() {
        contributors.add(new Contributor("Ananya Sharma", "Maharashtra", TODAY - 2, 12, 8, 4, 6));
        contributors.add(new Contributor("Rahul Verma", "Karnataka", TODAY - 40, 20, 15, 3, 10));
        contributors.add(new Contributor("Meera Iyer", "Tamil Nadu", TODAY - 5, 8, 22, 9, 4));
        contributors.add(new Contributor("Vikram Singh", "Punjab", TODAY - 60, 5, 6, 2, 15));
        contributors.add(new Contributor("Sneha Patel", "Gujarat", TODAY - 1, 18, 4, 12, 8));
        contributors.add(new Contributor("Arjun Nair", "Kerala", TODAY - 10, 6, 10, 14, 3));
        contributors.add(new Contributor("Kavita Das", "West Bengal", TODAY - 90, 3, 18, 20, 12));
        contributors.add(new Contributor("Imran Khan", "Uttar Pradesh", TODAY - 3, 14, 7, 5, 5));
        contributors.add(new Contributor("Tenzin Bhutia", "Assam", TODAY - 15, 7, 9, 6, 7));
        contributors.add(new Contributor("Priya Kulkarni", "Telangana", TODAY - 200, 25, 5, 1, 2));
    }

    public List<String> getStateFilters() {
        List<String> s = new ArrayList<>();
        s.add("All states");
        for (Contributor c : contributors) {
            if (!s.contains(c.state)) {
                s.add(c.state);
            }
        }
        Collections.sort(s.subList(1, s.size()));
        return s;
    }

    public List<RankRow> getLeaderboard(String timeFilter, String stateFilter, String typeFilter) {
        int minDay = cutoffDay(timeFilter);
        List<Contributor> filtered = new ArrayList<>();
        for (Contributor c : contributors) {
            if (c.lastActiveDay < minDay) {
                continue;
            }
            if (stateFilter != null && !"All states".equals(stateFilter) && !c.state.equals(stateFilter)) {
                continue;
            }
            filtered.add(c);
        }

        filtered.sort(Comparator.comparingInt((Contributor x) -> x.scoreForTypeFilter(typeFilter)).reversed());

        List<RankRow> rows = new ArrayList<>();
        int r = 1;
        for (Contributor c : filtered) {
            rows.add(new RankRow(r++, c, c.scoreForTypeFilter(typeFilter)));
        }
        return rows;
    }

    private int cutoffDay(String timeFilter) {
        if (TIME_7.equals(timeFilter)) {
            return TODAY - 7;
        }
        if (TIME_30.equals(timeFilter)) {
            return TODAY - 30;
        }
        return Integer.MIN_VALUE;
    }

    public String formatBreakdown(Contributor c) {
        return String.format(Locale.ROOT, "UI:%d Data:%d Docs:%d Rev:%d", c.uiCommits, c.dataCommits, c.docCommits, c.reviews);
    }
}
