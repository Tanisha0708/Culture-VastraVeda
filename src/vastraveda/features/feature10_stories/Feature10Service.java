package vastraveda.features.feature10_stories;

import java.util.HashMap;
import java.util.Map;

/**
 * Feature 10 — Cultural Stories Service (FINAL)
 */
public class Feature10Service {

    private final Map<String, String> stories = new HashMap<>();

    public Feature10Service() {

        // ================= EXISTING =================
        stories.put("Banarasi Saree",
                "The Banarasi Saree, originating from Varanasi in Uttar Pradesh, is one of the most luxurious textiles in India.\n\n" +
                "Dating back to the Mughal era, it blends Persian and Indian artistry with intricate zari work.\n\n" +
                "These sarees are known for floral motifs, paisleys, and rich gold weaving.\n\n" +
                "Did you know? Authentic Banarasi sarees are still handwoven on traditional looms."
        );

        stories.put("Phulkari Dupatta",
                "Phulkari, meaning 'flower work', is a vibrant embroidery tradition from Punjab.\n\n" +
                "Traditionally crafted by women, it carries emotional and cultural value.\n\n" +
                "Did you know? Patterns are embroidered from the reverse side."
        );

        stories.put("Dhoti",
                "The Dhoti is one of India's oldest garments, dating back to the Vedic period.\n\n" +
                "It symbolizes simplicity, tradition, and comfort.\n\n" +
                "Did you know? Mahatma Gandhi popularized it during the freedom movement."
        );

        stories.put("Kanjivaram Saree",
                "The Kanjivaram Saree from Tamil Nadu is famous for rich silk and temple motifs.\n\n" +
                "Worn by brides, it represents prosperity and heritage.\n\n" +
                "Did you know? Its zari contains real silver coated with gold."
        );

        stories.put("Pashmina Shawl",
                "Pashmina from Kashmir is among the finest textiles globally.\n\n" +
                "Made from Changthangi goat wool, it is incredibly soft and warm.\n\n" +
                "Did you know? It can pass through a ring."
        );

        stories.put("Bandhani Saree",
                "Bandhani is a 5000-year-old tie-dye technique from Gujarat and Rajasthan.\n\n" +
                "Thousands of knots create intricate patterns.\n\n" +
                "Did you know? Some sarees have over 20,000 knots."
        );

        stories.put("Angrakha",
                "The Angrakha originated in Rajasthan as attire for warriors.\n\n" +
                "Its wrap design allowed flexibility and protection.\n\n" +
                "Did you know? It means 'body protector'."
        );

        stories.put("Kasavu Saree",
                "Kasavu Saree is Kerala’s traditional attire worn during Onam.\n\n" +
                "Its golden border symbolizes prosperity.\n\n" +
                "Did you know? It reflects simplicity and elegance."
        );

        // ================= MISSING ITEMS =================

        stories.put("Sherwani",
                "The Sherwani is a royal garment worn by men in North India.\n\n" +
                "It became popular during the Mughal era and is now widely used in weddings.\n\n" +
                "Did you know? It evolved from Persian court attire."
        );

        stories.put("Mekhela Chador",
                "Mekhela Chador is a traditional Assamese attire made of silk.\n\n" +
                "It consists of two elegant draped pieces.\n\n" +
                "Did you know? Muga silk becomes shinier over time."
        );

        stories.put("Ghagra Choli",
                "Ghagra Choli is a colorful outfit from Rajasthan and Gujarat.\n\n" +
                "It includes a flared skirt, blouse, and dupatta.\n\n" +
                "Did you know? Mirror work is a signature design."
        );

        stories.put("Pathani Suit",
                "The Pathani Suit is known for comfort and loose fit.\n\n" +
                "Popular during Eid and casual wear.\n\n" +
                "Did you know? It reflects Pashtun cultural influence."
        );

        stories.put("Pochampally Saree",
                "Pochampally Saree from Telangana uses the Ikat technique.\n\n" +
                "Threads are dyed before weaving patterns.\n\n" +
                "Did you know? It is also called Ikat saree."
        );

        stories.put("Churidar",
                "Churidar is a tightly fitted trouser worn with kurtas.\n\n" +
                "It gathers at the ankle forming folds.\n\n" +
                "Did you know? It became popular in the Mughal era."
        );

        stories.put("Manipuri Dress (Phanek)",
                "Phanek is a traditional wrap skirt from Manipur.\n\n" +
                "It is worn in daily life and cultural dances.\n\n" +
                "Did you know? It is central to Ras Lila performances."
        );
    }

    /**
     * Fetch story safely
     */
    public String getStory(String itemName) {

        if (itemName == null) return "No story available.";

        // Direct match
        if (stories.containsKey(itemName)) {
            return stories.get(itemName);
        }

        // Case-insensitive fallback
        for (String key : stories.keySet()) {
            if (key.equalsIgnoreCase(itemName)) {
                return stories.get(key);
            }
        }

        return "No cultural story available for this item yet.";
    }
}