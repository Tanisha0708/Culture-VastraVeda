package vastraveda.core.data;

import vastraveda.core.models.ClothingItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 🔒 CORE DATA — DO NOT MODIFY
 * Central in-memory data store for all clothing items.
 * Contributors: Call DataStore.getAllItems() to access data.
 */
public class DataStore {

    private static final List<ClothingItem> items = new ArrayList<>();

    static {
        items.add(new ClothingItem(
            "Banarasi Saree", "Uttar Pradesh", "Silk",
            "Wedding", "Female",
            "A luxurious silk saree woven in Varanasi, known for its gold and silver brocade work (zari). A staple for brides across India.",
            "🥻", "Mughal Era (16th century)", "Dry clean only"
        ));
        items.add(new ClothingItem(
            "Phulkari Dupatta", "Punjab", "Cotton",
            "Festival", "Female",
            "A vibrant embroidered dupatta from Punjab featuring floral motifs stitched with silk threads on cotton base. Worn during Lohri and Baisakhi.",
            "🧣", "18th century", "Hand wash gently"
        ));
        items.add(new ClothingItem(
            "Dhoti", "Pan-India", "Cotton",
            "Casual/Religious", "Male",
            "A traditional unstitched garment worn by men across India. Draped around the waist and legs, it varies in style by region.",
            "👘", "Ancient (Vedic Period)", "Machine wash cold"
        ));
        items.add(new ClothingItem(
            "Kanjivaram Saree", "Tamil Nadu", "Silk",
            "Wedding", "Female",
            "A heavyweight silk saree from Kanchipuram, Tamil Nadu. Known for its contrasting borders, temple motifs, and rich gold zari work.",
            "🥻", "17th century", "Dry clean only"
        ));
        items.add(new ClothingItem(
            "Sherwani", "North India", "Silk/Brocade",
            "Wedding", "Male",
            "An elegant long coat-like garment worn by grooms and at formal occasions. Typically paired with churidar and dupatta.",
            "🥼", "Mughal Era", "Dry clean only"
        ));
        items.add(new ClothingItem(
            "Mekhela Chador", "Assam", "Silk (Muga/Pat)",
            "Festival", "Female",
            "Traditional two-piece attire from Assam. The mekhela wraps the lower body while the chador drapes over the upper body. Made from Assam's prized Muga silk.",
            "🥻", "Ancient Assamese Era", "Hand wash"
        ));
        items.add(new ClothingItem(
            "Ghagra Choli", "Rajasthan/Gujarat", "Cotton/Silk",
            "Festival/Wedding", "Female",
            "A full flared skirt (ghagra) paired with a blouse (choli) and dupatta. Decorated with mirror work, embroidery, and vibrant colors.",
            "👗", "Medieval Period", "Hand wash cold"
        ));
        items.add(new ClothingItem(
            "Pathani Suit", "North-West India", "Cotton/Linen",
            "Casual/Eid", "Male",
            "A loose, comfortable salwar-kameez style suit originating from the Pashtun culture. Popular across North India during Eid and casual wear.",
            "👔", "19th century", "Machine wash"
        ));
        items.add(new ClothingItem(
            "Kasavu Saree", "Kerala", "Cotton",
            "Onam/Festival", "Female",
            "A cream-white cotton saree with a distinctive golden border. The traditional attire of Kerala women during Onam festival.",
            "🥻", "Ancient Kerala Tradition", "Hand wash"
        ));
        items.add(new ClothingItem(
            "Pashmina Shawl", "Kashmir", "Pashmina Wool",
            "Winter/Formal", "Unisex",
            "A fine luxury shawl made from Changthangi or Pashmina goat wool. Known worldwide for its extraordinary softness and warmth.",
            "🧣", "15th century", "Dry clean only"
        ));
        items.add(new ClothingItem(
            "Bandhani Saree", "Gujarat/Rajasthan", "Silk/Cotton",
            "Festival", "Female",
            "A tie-dye saree featuring intricate dot patterns created by tying thousands of tiny points of fabric before dyeing.",
            "🥻", "5000+ years old", "Hand wash cold"
        ));
        items.add(new ClothingItem(
            "Angrakha", "Rajasthan", "Cotton/Silk",
            "Cultural/Festival", "Male",
            "An ancient asymmetric wrap-around tunic. The name means 'body protector'. Worn by Rajput warriors and now a cultural symbol.",
            "👔", "Ancient Rajput Era", "Dry clean"
        ));
        items.add(new ClothingItem(
            "Pochampally Saree", "Telangana", "Silk/Cotton",
            "Casual/Festival", "Female",
            "A saree made using the Ikat weaving technique from Pochampally village. Features geometric patterns in vibrant colors.",
            "🥻", "18th century", "Dry clean"
        ));
        items.add(new ClothingItem(
            "Churidar", "Pan-India", "Cotton/Lycra",
            "Casual/Formal", "Unisex",
            "Close-fitting trousers gathered in pleats at the ankles, creating a bangle (churis) effect. Paired with kameez or kurta.",
            "👖", "Mughal Period", "Machine wash"
        ));
        items.add(new ClothingItem(
            "Manipuri Dress (Phanek)", "Manipur", "Cotton/Silk",
            "Dance/Cultural", "Female",
            "A wraparound skirt worn by Meitei women of Manipur. Essential for Ras Lila dance performances and cultural ceremonies.",
            "👘", "Ancient Manipuri Tradition", "Hand wash"
        ));
        items.add(new ClothingItem(
            "Lal Paar Saree", "West Bengal", "Cotton/Silk",
            "Festival/Durga Puja", "Female",
            "The iconic white saree with a red (lal) border worn during Durga Puja and festive mornings in Bengal.",
            "🥻", "Bengal Tradition", "Hand wash cold"
        ));
        items.add(new ClothingItem(
            "Dhuti", "West Bengal", "Cotton",
            "Festival/Religious", "Male",
            "The Bengali drape of the unstitched lower garment, worn with kurta for pujo, weddings, and formal occasions.",
            "👘", "Bengal Tradition", "Machine wash cold"
        ));
    }

    /** Returns an unmodifiable view of all clothing items. */
    public static List<ClothingItem> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    /** Returns a distinct list of all regions in the data set. */
    public static List<String> getAllRegions() {
        List<String> regions = new ArrayList<>();
        for (ClothingItem item : items) {
            if (!regions.contains(item.getRegion())) {
                regions.add(item.getRegion());
            }
        }
        Collections.sort(regions);
        return regions;
    }

    /** Returns a distinct list of all fabric types. */
    public static List<String> getAllFabrics() {
        List<String> fabrics = new ArrayList<>();
        for (ClothingItem item : items) {
            String fabric = item.getFabricType().split("/")[0].trim();
            if (!fabrics.contains(fabric)) {
                fabrics.add(fabric);
            }
        }
        Collections.sort(fabrics);
        return fabrics;
    }

    /** Returns a distinct list of all genders. */
    public static List<String> getAllGenders() {
        return Arrays.asList("Female", "Male", "Unisex");
    }

    /** Returns a distinct list of all occasions. */
    public static List<String> getAllOccasions() {
        List<String> occasions = new ArrayList<>();
        for (ClothingItem item : items) {
            String occ = item.getOccasion().split("/")[0].trim();
            if (!occasions.contains(occ)) {
                occasions.add(occ);
            }
        }
        Collections.sort(occasions);
        return occasions;
    }
}
