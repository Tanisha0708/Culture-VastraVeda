package vastraveda.features.feature14_states;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.*;

public class Feature14Service {

    public List<String> getAllStates() {
        List<String> states = new ArrayList<>(DataStore.getAllRegions());
        Collections.sort(states);
        return states;
    }

    public List<ClothingItem> getGarmentsByState(String state) {
        return FilterUtils.filterByRegion(state);
    }

    public String getStateFact(String state) {
        Map<String, String> facts = new HashMap<>();
        facts.put("Punjab",      "Punjab is renowned for Phulkari embroidery — vivid floral patterns on shawls and dupattas. The craft is UNESCO-listed and passed down through generations of women artisans.");
        facts.put("Rajasthan",   "Rajasthan's bandhani tie-dye and leheriya wave-stripe techniques produce some of India's most vibrant textiles. Jaipur's block-printing heritage dates back over 400 years.");
        facts.put("Gujarat",     "Gujarat is famous for Patola silk — double-ikat weave so intricate that one saree can take six months to weave. Kutch embroidery with mirror-work is another pride of the state.");
        facts.put("Maharashtra", "Maharashtra's Paithani saree, woven with silk and gold zari, originates from Paithan on the Godavari river. The peacock motif is its signature.");
        facts.put("West Bengal", "West Bengal produces the finest Muslin and Tant cotton sarees. Baluchari silk sarees feature mythological scenes woven into the pallu.");
        facts.put("Tamil Nadu",  "Kanchipuram silk sarees are Tamil Nadu's crown jewel — heavyweight silk with contrasting borders and zari work, traditionally gifted at weddings.");
        facts.put("Kerala",      "Kerala's Kasavu sarees feature pure white cotton with gold zari borders — a symbol of elegance worn during Onam and weddings.");
        facts.put("Karnataka",   "Mysore silk and Ilkal sarees are Karnataka's textile treasures. The Ilkal saree's art-silk pallu contrasts beautifully with the cotton body.");
        facts.put("Andhra Pradesh", "Pochampally Ikat from Andhra Pradesh is a UNESCO Geographical Indication product — intricate geometric patterns created by resist-dyeing the yarn before weaving.");
        facts.put("Odisha",      "Odisha's Sambalpuri and Bomkai sarees use ikat weaving techniques. Pattachitra motifs inspired by temple art adorn many traditional textiles.");
        facts.put("Assam",       "Assam produces the world's rarest silk — Muga, a golden wild silk unique to the Brahmaputra valley. Mekhela Chador is the traditional two-piece garment.");
        facts.put("Manipur",     "Manipur's Moirang Phi fabric features intricate patterns woven on loin-loom. The traditional Innaphi shawl is essential in Meitei ceremonial dress.");
        facts.put("Rajasthan/Gujarat", "This region shares a rich tradition of mirror-work embroidery, bandhani dyeing, and block-printed cottons worn across both states.");
        facts.put("Gujarat/Rajasthan", "Known for ghagra choli and heavily embroidered fabrics, this region blends Gujarati mirror-work with Rajasthani tie-dye traditions.");
        facts.put("Telangana",   "Telangana is home to Pochampally Ikat and Gadwal sarees — both celebrated for their geometric patterns and rich silk-cotton blends.");
        facts.put("Jammu & Kashmir", "Kashmir is world-famous for Pashmina shawls — the finest wool in the world — and the intricate Kani weave that can take years to complete.");
        return facts.getOrDefault(state,
                state + " has a rich tradition of handloom weaving and regional textile arts that reflect its unique cultural heritage and history.");
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
}