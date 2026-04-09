package vastraveda.features.feature14_states;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.*;

public class Feature14Service {

    public static final class StateProfile {
        private final String stateName;
        private final String region;
        private final String about;
        private final List<String> weavingCentres;
        private final List<String> signatureGarments;
        private final List<String> dominantFabrics;
        private final List<String> signatureColours;
        private final List<String> giItems;

        public StateProfile(String stateName, String region, String about,
                            List<String> weavingCentres, List<String> signatureGarments,
                            List<String> dominantFabrics, List<String> signatureColours,
                            List<String> giItems) {
            this.stateName = stateName;
            this.region = region;
            this.about = about;
            this.weavingCentres = weavingCentres;
            this.signatureGarments = signatureGarments;
            this.dominantFabrics = dominantFabrics;
            this.signatureColours = signatureColours;
            this.giItems = giItems;
        }

        public String getStateName() {
            return stateName;
        }

        public String getRegion() {
            return region;
        }

        public String getAbout() {
            return about;
        }

        public List<String> getWeavingCentres() {
            return weavingCentres;
        }

        public List<String> getSignatureGarments() {
            return signatureGarments;
        }

        public List<String> getDominantFabrics() {
            return dominantFabrics;
        }

        public List<String> getSignatureColours() {
            return signatureColours;
        }

        public List<String> getGiItems() {
            return giItems;
        }
    }

    private final Map<String, StateProfile> profiles = new HashMap<>();

    public Feature14Service() {
        seedProfiles();
    }

    public List<String> getAllStates() {
        List<String> states = new ArrayList<>(profiles.keySet());
        Collections.sort(states);
        return states;
    }

    public List<ClothingItem> getGarmentsByState(String state) {
        if ("Kashmir".equalsIgnoreCase(state)) {
            return FilterUtils.filterByRegion("Kashmir");
        }
        return FilterUtils.filterByRegion(state);
    }

    public StateProfile getStateProfile(String state) {
        return profiles.get(state);
    }

    public String getStateFact(String state) {
        StateProfile profile = profiles.get(state);
        if (profile != null) {
            return profile.getAbout();
        }
        return state + " has a rich tradition of handloom weaving and regional textile arts.";
    }

    public String getStateEmoji(String state) {
        Map<String, String> emojis = new HashMap<>();
        emojis.put("Punjab",         "🌾");
        emojis.put("Rajasthan",      "🏜");
        emojis.put("Gujarat",        "🪁");
        emojis.put("Maharashtra",    "🦚");
        emojis.put("West Bengal",    "🐯");
        emojis.put("Tamil Nadu",     "🏛");
        emojis.put("Kerala",         "🌴");
        emojis.put("Karnataka",      "🌺");
        emojis.put("Andhra Pradesh", "🎨");
        emojis.put("Odisha",         "🛕");
        emojis.put("Assam",          "🍵");
        emojis.put("Manipur",        "💃");
        emojis.put("Telangana",      "💎");
        emojis.put("Jammu & Kashmir","❄");
        return emojis.getOrDefault(state, "🏛");
    }

    private void seedProfiles() {
        addProfile(new StateProfile(
            "Punjab", "North India",
            "Punjab is renowned for vibrant Phulkari embroidery and festive textiles tied to agrarian celebrations.",
            Arrays.asList("Amritsar", "Patiala"),
            Arrays.asList("Phulkari Dupatta", "Punjabi Salwar Kameez"),
            Arrays.asList("Cotton", "Khaddar"),
            Arrays.asList("#E86A17", "#2E8B57", "#E94B8A"),
            Arrays.asList("Phulkari")
        ));
        addProfile(new StateProfile(
            "Rajasthan", "North West India",
            "Rajasthan showcases bold bandhani, leheriya, and mirror-work traditions with bright desert color palettes.",
            Arrays.asList("Jodhpur", "Jaipur", "Barmer"),
            Arrays.asList("Bandhani Saree", "Ghagra Choli", "Angrakha"),
            Arrays.asList("Cotton", "Silk"),
            Arrays.asList("#C62828", "#FBC02D", "#FB8C00"),
            Arrays.asList("Bandhani", "Leheriya")
        ));
        addProfile(new StateProfile(
            "Uttar Pradesh", "North India",
            "Varanasi's brocade weaving has made Banarasi textiles a ceremonial benchmark across India.",
            Collections.singletonList("Varanasi"),
            Arrays.asList("Banarasi Saree", "Dhoti Kurta"),
            Arrays.asList("Silk", "Brocade"),
            Arrays.asList("#8E2430", "#C9A227", "#2E7D32"),
            Collections.singletonList("Banarasi Silk")
        ));
        addProfile(new StateProfile(
            "Kashmir", "North India",
            "Kashmir is globally known for fine pashmina and refined wool work adapted to mountain climates.",
            Collections.singletonList("Srinagar"),
            Arrays.asList("Pashmina Shawl", "Pheran"),
            Arrays.asList("Wool", "Pashmina"),
            Arrays.asList("#FFF8E1", "#F5F5DC", "#FF9933"),
            Arrays.asList("Pashmina", "Kani Shawl")
        ));
        addProfile(new StateProfile(
            "Gujarat", "West India",
            "Gujarat combines double-ikat precision with vibrant tie-dye and embroidered folk attire.",
            Arrays.asList("Patan", "Surat", "Kutch"),
            Arrays.asList("Patola Saree", "Bandhani", "Ghagra"),
            Arrays.asList("Silk", "Cotton"),
            Arrays.asList("#C62828", "#2E7D32", "#FBC02D"),
            Collections.singletonList("Patola Silk")
        ));
        addProfile(new StateProfile(
            "Tamil Nadu", "South India",
            "Temple-inspired motifs and heavy silk weaving define Tamil Nadu's signature bridal traditions.",
            Collections.singletonList("Kanchipuram"),
            Collections.singletonList("Kanjivaram Saree"),
            Arrays.asList("Silk", "Zari"),
            Arrays.asList("#B71C1C", "#D4AF37", "#1E88E5"),
            Collections.singletonList("Kanjivaram Silk")
        ));
        addProfile(new StateProfile(
            "Kerala", "South India",
            "Kerala's elegant kasavu style balances minimal cotton bases with rich gold borders.",
            Collections.singletonList("Balaramapuram"),
            Collections.singletonList("Kasavu Saree"),
            Collections.singletonList("Cotton"),
            Arrays.asList("#FFFFFF", "#D4AF37"),
            Collections.singletonList("Kasavu")
        ));
        addProfile(new StateProfile(
            "Assam", "North East India",
            "Assam's muga silk weaving and mekhela-chador drape are pillars of the region's textile identity.",
            Collections.singletonList("Sualkuchi"),
            Arrays.asList("Mekhela Chador", "Gamocha Attire"),
            Arrays.asList("Muga Silk", "Cotton"),
            Arrays.asList("#B71C1C", "#FFFFFF", "#D4AF37"),
            Collections.singletonList("Muga Silk")
        ));
        addProfile(new StateProfile(
            "West Bengal", "East India",
            "West Bengal blends fine muslin legacies with jamdani and baluchari narrative weaving.",
            Arrays.asList("Murshidabad", "Shantipur"),
            Arrays.asList("Jamdani Saree", "Baluchari Saree"),
            Arrays.asList("Cotton", "Silk"),
            Arrays.asList("#FFFFFF", "#B71C1C"),
            Arrays.asList("Jamdani", "Baluchari")
        ));
        addProfile(new StateProfile(
            "Telangana", "South India",
            "Telangana's ikat artistry from Pochampally creates geometric sarees in vivid color contrasts.",
            Arrays.asList("Pochampally", "Gadwal"),
            Collections.singletonList("Pochampally Saree"),
            Arrays.asList("Silk", "Cotton"),
            Arrays.asList("#F06292", "#4DD0E1", "#9575CD"),
            Collections.singletonList("Pochampally Ikat")
        ));
        addProfile(new StateProfile(
            "Manipur", "North East India",
            "Manipur's phanek and shawl traditions are central to ceremonial and dance attire.",
            Collections.singletonList("Imphal"),
            Collections.singletonList("Phanek"),
            Arrays.asList("Cotton", "Silk"),
            Arrays.asList("#212121", "#C62828", "#2E7D32"),
            Collections.singletonList("Manipuri Shawl")
        ));
        addProfile(new StateProfile(
            "Odisha", "East India",
            "Odisha's ikat-rich handloom culture is visible in Sambalpuri and Bomkai weaving schools.",
            Arrays.asList("Sambalpur", "Sonepur"),
            Arrays.asList("Sambalpuri Saree", "Bomkai Saree"),
            Arrays.asList("Cotton", "Silk"),
            Arrays.asList("#C62828", "#212121", "#FFFFFF"),
            Arrays.asList("Sambalpuri", "Odisha Ikat")
        ));
    }

    private void addProfile(StateProfile profile) {
        profiles.put(profile.getStateName(), profile);
    }
}