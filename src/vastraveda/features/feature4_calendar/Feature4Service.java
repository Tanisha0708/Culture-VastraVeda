package vastraveda.features.feature4_calendar;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Festival calendar data. Each row: name, month (English name), day of month, region, description, occasion.
 * Dates are representative Gregorian anchors for the in-app calendar (actual lunar dates vary by year).
 */
public class Feature4Service {

    public static final String SPRING = "Spring";
    public static final String SUMMER = "Summer";
    public static final String AUTUMN = "Autumn";
    public static final String WINTER = "Winter";

    /** Index constants for String[] rows */
    public static final int IDX_NAME = 0;
    public static final int IDX_MONTH = 1;
    public static final int IDX_DAY = 2;
    public static final int IDX_REGION = 3;
    public static final int IDX_DESCRIPTION = 4;
    public static final int IDX_OCCASION = 5;

    private static final List<String[]> ALL_FESTIVALS = new ArrayList<>();
    private static final Map<String, List<String[]>> BY_SEASON = new LinkedHashMap<>();

    static {
        List<String[]> spring = new ArrayList<>();
        spring.add(f("Holi", "March", 14, "Rajasthan",
            "Spring festival of color; bright bandhani and ghagra mirror the joyful mood."));
        spring.add(f("Baisakhi", "April", 14, "Punjab",
            "Harvest festival in Punjab; phulkari embroidery is a signature celebration look."));
        BY_SEASON.put(SPRING, Collections.unmodifiableList(spring));

        List<String[]> summer = new ArrayList<>();
        summer.add(f("Rath Yatra", "July", 5, "Gujarat",
            "Grand processions and fair days; vibrant cotton and tie-dye festival silks fit the season."));
        summer.add(f("Onam", "August", 28, "Kerala",
            "Harvest homecoming in Kerala; cream kasavu with gold border is the classic Onam attire."));
        BY_SEASON.put(SUMMER, Collections.unmodifiableList(summer));

        List<String[]> autumn = new ArrayList<>();
        autumn.add(f("Navratri", "October", 3, "Gujarat",
            "Nine nights of garba; ghagra-choli and bandhani are staples on the dance floor."));
        autumn.add(f("Dussehra/Durga Puja", "October", 12, "West Bengal",
            "Pandals and sindoor khela; women often wear the white saree with red border (lal paar), men a crisp dhuti-kurta for pujo."));
        autumn.add(f("Diwali", "November", 8, "Telangana",
            "Festival of lights; handloom ikat from Pochampally suits family gatherings and pujas."));
        BY_SEASON.put(AUTUMN, Collections.unmodifiableList(autumn));

        List<String[]> winter = new ArrayList<>();
        winter.add(f("Lohri", "January", 13, "Punjab",
            "Winter bonfire festival; layered woolens with a phulkari dupatta is iconic."));
        winter.add(f("Magh Bihu", "January", 15, "Assam",
            "Assamese harvest feasts; Muga and pat silks appear in mekhela chador ensembles."));
        BY_SEASON.put(WINTER, Collections.unmodifiableList(winter));

        ALL_FESTIVALS.addAll(spring);
        ALL_FESTIVALS.addAll(summer);
        ALL_FESTIVALS.addAll(autumn);
        ALL_FESTIVALS.addAll(winter);
    }

    private static String[] f(String name, String month, int day, String region, String description) {
        return new String[]{name, month, String.valueOf(day), region, description, "Festival"};
    }

    public List<String[]> getFestivalsBySeason(String season) {
        if (season == null) {
            return Collections.emptyList();
        }
        List<String[]> list = BY_SEASON.get(season.trim());
        return list != null ? list : Collections.emptyList();
    }

    /** All festivals (chronological by month then day within the static data order). */
    public List<String[]> getAllFestivals() {
        return Collections.unmodifiableList(ALL_FESTIVALS);
    }

    /**
     * Festivals on a given calendar day (month 1–12). Year is ignored — repeating annual dates.
     */
    public List<String[]> getFestivalsOn(int month, int dayOfMonth) {
        List<String[]> out = new ArrayList<>();
        for (String[] row : ALL_FESTIVALS) {
            if (monthOf(row) == month && dayOf(row) == dayOfMonth) {
                out.add(row);
            }
        }
        return out;
    }

    public static int monthOf(String[] row) {
        return Month.valueOf(row[IDX_MONTH].toUpperCase(Locale.US)).getValue();
    }

    public static int dayOf(String[] row) {
        return Integer.parseInt(row[IDX_DAY].trim());
    }
}
