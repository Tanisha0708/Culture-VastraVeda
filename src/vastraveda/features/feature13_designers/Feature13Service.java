package vastraveda.features.feature13_designers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Feature13Service {

    public static class Artisan {
        public String avatar, name, title, region, craft, bio, quote;
        public Artisan(String avatar, String name, String title,
                       String region, String craft, String bio, String quote) {
            this.avatar = avatar; this.name = name; this.title = title;
            this.region = region; this.craft = craft;
            this.bio = bio; this.quote = quote;
        }
    }

    public List<Artisan> getAllArtisans() {
        List<Artisan> list = new ArrayList<>();
        list.add(new Artisan("🧵", "Roshanara Devi",
            "Master Weaver",
            "Varanasi, Uttar Pradesh", "Banarasi Silk Weaving",
            "With over 40 years of experience on the handloom, Roshanara has woven over 3,000 Banarasi sarees. Her intricate zari work is recognized by the Craft Council of India.",
            "Every thread carries the prayer of the weaver."));

        list.add(new Artisan("🪡", "Baldev Singh Chhabra",
            "Phulkari Embroidery Artist",
            "Patiala, Punjab", "Phulkari Embroidery",
            "Baldev has dedicated 30 years to preserving the ancient Phulkari tradition of Punjab. He trains rural women artisans and has exhibited in Paris and Tokyo.",
            "Phulkari is not just embroidery — it is a woman's story told in flowers."));

        list.add(new Artisan("🎨", "Meenakshi Parekh",
            "Patola Weaver",
            "Patan, Gujarat", "Patola Double Ikat",
            "One of only a handful of artisans who still practice the double-ikat Patola technique. A single saree takes Meenakshi six months to complete on a traditional loom.",
            "Patola weaving is meditation in motion."));

        list.add(new Artisan("✨", "Irfan Khan Khatri",
            "Bandhani Master",
            "Kutch, Gujarat", "Bandhani Tie-Dye",
            "Irfan is the fifth generation of the Khatri family to practice Bandhani. His natural dye work using indigo and turmeric has won three National Craft Awards.",
            "True color comes from the earth, not a factory."));

        list.add(new Artisan("🌸", "Lakshmi Narayan",
            "Kanjivaram Silk Weaver",
            "Kanchipuram, Tamil Nadu", "Kanjivaram Silk Weaving",
            "Lakshmi weaves traditional Kanjivaram sarees with pure mulberry silk and 24-carat zari. Her temple border designs are inspired by the sculptures of Brihadeeswarar.",
            "A Kanjivaram saree is a temple you can wear."));

        list.add(new Artisan("🪢", "Surekha Jagdale",
            "Paithani Weaver",
            "Aurangabad, Maharashtra", "Paithani Silk Weaving",
            "Surekha revived the near-extinct Bangdi Mor (bangle-peacock) motif in Paithani weaving. Her work is part of the permanent collection at the Crafts Museum, Delhi.",
            "The peacock in my saree dances only for those who look closely."));

        list.add(new Artisan("🌿", "Bijoy Chandra Das",
            "Muga Silk Artisan",
            "Sualkuchi, Assam", "Muga Silk Weaving",
            "Bijoy works with Assam's rare golden Muga silk — found nowhere else in the world. He has trained over 200 weavers in sustainable sericulture and natural dyeing.",
            "Muga is Assam's gift to the world. We must never let it disappear."));

        list.add(new Artisan("🔶", "Savitri Devi Meghwal",
            "Kutch Embroidery Artist",
            "Bhuj, Gujarat", "Kutch Mirror Work Embroidery",
            "Savitri's mirror-work embroidery on traditional blouses and wall hangings has been featured in Vogue India. She runs a cooperative employing 50 women artisans in Bhuj.",
            "Each mirror reflects the sky. We embroider the universe."));

        list.add(new Artisan("🏺", "Ravi Shankar Prasad",
            "Kalamkari Artist",
            "Srikalahasti, Andhra Pradesh", "Kalamkari Pen Work",
            "Using only a bamboo pen and natural dyes, Ravi creates intricate Kalamkari panels depicting scenes from the Ramayana. His work sells internationally through craft NGOs.",
            "My pen is my brush, my fabric is my canvas, mythology is my muse."));

        list.add(new Artisan("💠", "Angom Tombi Devi",
            "Manipuri Weaver",
            "Imphal, Manipur", "Moirang Phi Weaving",
            "Tombi weaves the sacred Moirang Phi fabric on a traditional loin-loom. She is a recipient of the State Award for Handicrafts and teaches weaving at the local cultural center.",
            "The loom is alive. It breathes with the weaver."));

        return list;
    }

    public List<String> getAllRegions(List<Artisan> artisans) {
        List<String> regions = artisans.stream()
            .map(a -> a.region.contains(",") ? a.region.split(",")[1].trim() : a.region)
            .distinct().sorted().collect(Collectors.toList());
        regions.add(0, "All Regions");
        return regions;
    }

    public List<String> getAllCrafts(List<Artisan> artisans) {
        List<String> crafts = artisans.stream()
            .map(a -> a.craft).distinct().sorted().collect(Collectors.toList());
        crafts.add(0, "All Crafts");
        return crafts;
    }

    public List<Artisan> filter(List<Artisan> all, String region, String craft) {
        return all.stream()
            .filter(a -> region.equals("All Regions") || a.region.contains(region))
            .filter(a -> craft.equals("All Crafts") || a.craft.equals(craft))
            .collect(Collectors.toList());
    }
}