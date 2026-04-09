package vastraveda.features.feature4_calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Feature 4 — Festival calendar data (name, month, region, description, occasion per row).
 */
public class Feature4Service {

    public static final String SPRING = "Spring";
    public static final String SUMMER = "Summer";
    public static final String AUTUMN = "Autumn";
    public static final String WINTER = "Winter";

    private static final Map<String, List<String[]>> BY_SEASON = new LinkedHashMap<>();

    static {
        List<String[]> spring = new ArrayList<>();
        spring.add(new String[]{
            "Holi", "March", "Rajasthan",
            "Spring festival of color; bright bandhani and ghagra mirror the joyful mood.",
            "Festival"
        });
        spring.add(new String[]{
            "Baisakhi", "April", "Punjab",
            "Harvest festival in Punjab; phulkari embroidery is a signature celebration look.",
            "Festival"
        });
        BY_SEASON.put(SPRING, Collections.unmodifiableList(spring));

        List<String[]> summer = new ArrayList<>();
        summer.add(new String[]{
            "Rath Yatra", "July", "Gujarat",
            "Grand processions and fair days; vibrant cotton and tie-dye festival silks fit the season.",
            "Festival"
        });
        summer.add(new String[]{
            "Onam", "August", "Kerala",
            "Harvest homecoming in Kerala; cream kasavu with gold border is the classic Onam attire.",
            "Festival"
        });
        BY_SEASON.put(SUMMER, Collections.unmodifiableList(summer));

        List<String[]> autumn = new ArrayList<>();
        autumn.add(new String[]{
            "Navratri", "October", "Gujarat",
            "Nine nights of garba; ghagra-choli and bandhani are staples on the dance floor.",
            "Festival"
        });
        autumn.add(new String[]{
            "Durga Puja", "October", "Assam",
            "Community pandals and feasts; mekhela chador in Assam silk honors tradition.",
            "Festival"
        });
        autumn.add(new String[]{
            "Diwali", "November", "Telangana",
            "Festival of lights; handloom ikat from Pochampally suits family gatherings and pujas.",
            "Festival"
        });
        BY_SEASON.put(AUTUMN, Collections.unmodifiableList(autumn));

        List<String[]> winter = new ArrayList<>();
        winter.add(new String[]{
            "Lohri", "January", "Punjab",
            "Winter bonfire festival; layered woolens with a phulkari dupatta is iconic.",
            "Festival"
        });
        winter.add(new String[]{
            "Magh Bihu", "January", "Assam",
            "Assamese harvest feasts; Muga and pat silks appear in mekhela chador ensembles.",
            "Festival"
        });
        BY_SEASON.put(WINTER, Collections.unmodifiableList(winter));
    }

    /**
     * Festivals for a season tab. Each row: festivalName, month, region, description, occasion.
     */
    public List<String[]> getFestivalsBySeason(String season) {
        if (season == null) {
            return Collections.emptyList();
        }
        List<String[]> list = BY_SEASON.get(season.trim());
        return list != null ? list : Collections.emptyList();
    }
}
