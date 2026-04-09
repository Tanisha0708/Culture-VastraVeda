package vastraveda.features.feature7_timeline;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import java.util.*;
import java.util.stream.Collectors;

public class Feature7Service {

    /** * Era Meta-data object to provide 'Deep-Dive' information 
     */
    public static class EraData {
        public String name, years, summary, innovation;
        public EraData(String n, String y, String s, String i) {
            name = n; years = y; summary = s; innovation = i;
        }
    }

    public List<EraData> getTimelineEras() {
        return List.of(
            new EraData("Ancient", "3300 BCE – 1200 CE", 
                "The era of the 'Antariya' and 'Uttariya'. Draped fabrics were a sign of purity.", 
                "First known cultivation of cotton and use of natural Indigo dyes."),
            new EraData("Medieval", "1200 CE – 1526 CE", 
                "Introduction of tailored garments and Persian-influenced silhouettes.", 
                "The spinning wheel (Charkha) revolutionized fabric production."),
            new EraData("Mughal", "1526 CE – 1857 CE", 
                "The golden age of embroidery, brocades, and transparent muslins.", 
                "Introduction of Zardozi (gold) and Chikankari (white-work) embroidery."),
            new EraData("Colonial", "1857 CE – 1947 CE", 
                "Fusion of Victorian styles with Indian drapes. Khadi becomes a symbol of resistance.", 
                "The rise of handloom as a political statement of self-reliance."),
            new EraData("Modern", "1947 CE – Present", 
                "Global recognition of Indian textiles and the revival of heritage weaves.", 
                "Sustainable fashion and contemporary fusion of traditional motifs.")
        );
    }

    /** * Matches items from DataStore by checking if the item's era 
     * contains the era name (e.g., "Ancient Manipuri" matches "Ancient").
     */
    public List<ClothingItem> getGarmentsForEra(String eraName) {
        return DataStore.getAllItems().stream()
            .filter(item -> item.getEra().toLowerCase().contains(eraName.toLowerCase()))
            .collect(Collectors.toList());
    }
}