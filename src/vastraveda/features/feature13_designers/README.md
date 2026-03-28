# ✂️ Feature 13 — Featured Designers & Artisans

## What This Feature Should Do
Spotlight the master weavers, artisans, and designers who keep Indian textile traditions alive. Each profile card includes the artisan's craft, region, and the clothing item they're known for.

---

## 📐 Expected UI Layout

```
┌──────────────────────────────────────────────────────────────┐
│  ✂️  Featured Designers & Artisans                           │
│  The hands that weave India's textile heritage               │
├──────────────────────────────────────────────────────────────┤
│  Filter by Region: [All ▼]   Filter by Craft: [All ▼]       │
├──────────────────────────────────────────────────────────────┤
│  ┌───────────────────┐   ┌───────────────────┐              │
│  │  👨‍🎨  Ramesh Lal    │   │  👩‍🎨  Fatima Begum  │              │
│  │  Master Weaver     │   │  Zari Artisan      │              │
│  │  Varanasi, UP      │   │  Varanasi, UP      │              │
│  │  ───────────────── │   │  ───────────────── │              │
│  │  Craft: Banarasi   │   │  Craft: Zardozi    │              │
│  │  silk weaving for  │   │  embroidery; 3rd   │              │
│  │  35 years          │   │  generation artisan│              │
│  │  [View Garments]   │   │  [View Garments]   │              │
│  └───────────────────┘   └───────────────────┘              │
└──────────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Profile cards** in a `GridLayout(0, 2)` or `(0, 3)` — at least 8 artisan profiles
2. **Filter by region** dropdown — shows artisans from selected state
3. **Filter by craft** dropdown — Weaving, Embroidery, Dyeing, Knitting
4. **"View Garments" button** — shows DataStore items related to that artisan's craft/region
5. **Each card shows**: avatar emoji, name, title, region, years of experience, short bio

---

## 👨‍🎨 Artisan Profiles to Create (hardcode in Feature13Service.java)

| Name | Title | Region | Craft | Bio |
|------|-------|--------|-------|-----|
| Ramesh Kumar Lal | Master Weaver | Varanasi, UP | Banarasi silk weaving | 40-year veteran who learned from his father; known for intricate temple border designs |
| Fatima Begum | Zari Artisan | Varanasi, UP | Zardozi embroidery | Third-generation zari artisan; her work has been exhibited in Paris |
| Gurpreet Kaur | Phulkari Embroideress | Amritsar, Punjab | Phulkari embroidery | Preserving the ancient phulkari technique through a women's cooperative |
| Kanchana Devi | Kanjivaram Weaver | Kanchipuram, TN | Silk weaving | National Award winner; weaves 6-yard masterpieces using 4,000 threads |
| Abdul Hamid | Kashmiri Shawl Maker | Srinagar, Kashmir | Pashmina weaving | Creates kani shawls that take 18 months each; UNESCO recognition |
| Lakshmi Bai | Bandhani Artist | Jamnagar, Gujarat | Tie-dye | Can tie 700 knots per hour; taught 200 village women the craft |
| Ratan Chandra | Pochampally Ikat Weaver | Pochampally, Telangana | Ikat weaving | GI-tag holder community leader; revived 15 dying Ikat patterns |
| Meera Devi | Muga Silk Weaver | Sualkuchi, Assam | Muga silk weaving | Specialises in Muga-Pat fusion; sells through government emporium |

---

## 💻 Code Structure Expected

```java
// Feature13Service.java
public class Feature13Service {
    public static class ArtisanProfile {
        public String name, title, region, craft, bio, avatar;
        // constructor
    }
    public List<ArtisanProfile> getAllArtisans() { ... }
    public List<ArtisanProfile> filterByRegion(String region) { ... }
    public List<ArtisanProfile> filterByCraft(String craft) { ... }
    public List<ClothingItem> getRelatedItems(ArtisanProfile artisan) {
        // use FilterUtils.filterByRegion(artisan.region)
    }
}
// Feature13UI.java — filter bar + GridLayout of artisan cards
```

---

## 🎨 UI Style Tips
- Avatar: large emoji (👨‍🎨 or 👩‍🎨) in 32pt font, center-aligned at top of card
- Card: fixed size `280 × 200` px, cream background, left border accent by craft type
- Craft color coding: Weaving=gold, Embroidery=red, Dyeing=blue, Knitting=green
- "View Garments" button: small, styled with COLOR_SECONDARY
