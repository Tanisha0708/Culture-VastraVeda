package vastraveda.features.feature5_occasions;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;

import java.util.List;

public class Feature5Service {

    public List<String> getAllOccasions() {
        return DataStore.getAllOccasions();
    }

    public List<ClothingItem> getByOccasion(String occasion) {
        return FilterUtils.filterByOccasion(occasion);
    }

    public String getOccasionDescription(String occasion) {
        switch (occasion) {
            case "Wedding":  return "Weddings are India's grandest celebrations. Opt for rich silks, heavy embroidery, and vibrant colours. Lehengas, sherwanis, and Banarasi sarees are classic choices.";
            case "Festival": return "Festivals call for bright, auspicious colours. Cotton and silk work beautifully. Choose regional specialities to honour the cultural roots of each celebration.";
            case "Casual":   return "Everyday traditional wear should balance comfort and style. Salwar kameez, kurta-pyjama, and light cotton sarees are perfect for daily use.";
            case "Formal":   return "Formal occasions require polished elegance. Stick to muted tones with subtle embellishments. A well-pressed kurta or a silk saree with minimal jewellery speaks volumes.";
            default:         return "Choose garments appropriate to the event's setting, season, and regional customs.";
        }
    }
}