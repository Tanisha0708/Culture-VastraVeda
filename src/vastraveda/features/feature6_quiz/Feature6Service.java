package vastraveda.features.feature6_quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Heritage badges — mock contribution counter + milestone rules.
 */
public class Feature6Service {

    public static final class Badge {
        public final String id;
        public final String icon;
        public final String name;
        public final String description;
        public final int minContributions;

        public Badge(String id, String icon, String name, String description, int minContributions) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.description = description;
            this.minContributions = minContributions;
        }
    }

    private final List<Badge> catalog = new ArrayList<>();

    public Feature6Service() {
        catalog.add(new Badge("seed", "🌱", "Thread Explorer", "First contribution recorded", 1));
        catalog.add(new Badge("loom", "🧵", "Loom Learner", "Five accurate submissions", 5));
        catalog.add(new Badge("zari", "✨", "Zari Scholar", "Ten contributions", 10));
        catalog.add(new Badge("heritage", "🏛", "Heritage Keeper", "Twenty-five contributions", 25));
        catalog.add(new Badge("custodian", "🥻", "Culture Custodian", "Fifty contributions", 50));
    }

    public List<Badge> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    public List<Badge> earnedBadges(int contributions) {
        List<Badge> out = new ArrayList<>();
        for (Badge b : catalog) {
            if (contributions >= b.minContributions) {
                out.add(b);
            }
        }
        return out;
    }
}
