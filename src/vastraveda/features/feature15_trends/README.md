# 🌟 Feature 15 — Modern Trends

## What This Feature Should Do
Showcase how traditional Indian clothing has influenced contemporary fashion — from Bollywood to global runways. Show "traditional vs modern" comparisons and fusion trend cards.

---

## 📐 Expected UI Layout

```
┌──────────────────────────────────────────────────────────────┐
│  🌟  Modern Trends                                            │
│  Where tradition meets contemporary Indian fashion           │
├──────────────────────────────────────────────────────────────┤
│  [🏛 Traditional Roots] [✨ Modern Interpretations] [🌐 Global]│
├──────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────────┐│
│  │  TREND: Saree in the Boardroom                          ││
│  │  ─────────────────────────────────────────────────────  ││
│  │  TRADITIONAL         →        MODERN INTERPRETATION     ││
│  │  🥻 Banarasi Saree             Crepe saree with blazer  ││
│  │  6-yard silk drape             Structured shoulder      ││
│  │  Temple borders                Minimal prints          ││
│  │                                                         ││
│  │  🔥 Trending in: Corporate wear, Business formals       ││
│  │  🌍 Global moment: Sunita Williams, Kamala Harris       ││
│  └──────────────────────────────────────────────────────────┘│
│  [Next Trend →]  [← Prev]     Trend 2 of 8                  │
└──────────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **3 category tabs** — Traditional Roots / Modern Interpretations / Global Influence
2. **At least 8 trend cards** — each linking a traditional item to its modern avatar
3. **Navigation** — Previous / Next buttons to flip through trend cards
4. **Traditional item link** — each trend references a `ClothingItem` from `DataStore`
5. **Global moment** — one line about where this trend appeared globally

---

## 🌟 Trends to Include (hardcode in Feature15Service.java)

| Trend Title | Traditional Root | Modern Interpretation | Global Moment |
|-------------|-----------------|----------------------|---------------|
| Saree in the Boardroom | Banarasi Saree | Silk saree with structured blazer | Worn by Indian diplomats globally |
| Kurta Couture | Dhoti/Kurta | Fitted kurta with palazzo pants | Seen at Cannes Film Festival |
| Pashmina Power | Pashmina Shawl | Oversized pashmina as statement wrap | Paris Fashion Week staple |
| Bandhani Beats | Bandhani Saree | Tie-dye co-ord sets, crop tops | Global tie-dye trend 2020-2024 |
| Phulkari Pop | Phulkari Dupatta | Phulkari jackets, sneakers, bags | Worn by Priyanka Chopra in NYC |
| Sherwani Chic | Sherwani | Sherwani-inspired long jackets | Indo-western fusion groom wear |
| Ikat Everything | Pochampally Saree | Ikat print dresses, furniture | Japanese and Italian ikat revival |
| Ghagra Glam | Ghagra Choli | Contemporary lehenga with crop top | Bollywood red carpet staple |

---

## 💻 Code Structure Expected

```java
// Feature15Service.java
public class Feature15Service {
    public static class Trend {
        public String title, traditionalRoot, modernInterpretation, globalMoment, category;
        public String linkedItemName; // matches a ClothingItem name in DataStore
    }
    public List<Trend> getAllTrends() { ... }
    public List<Trend> getTrendsByCategory(String category) { ... }
    public ClothingItem getLinkedItem(Trend trend) {
        // Search DataStore for item by name
    }
}
// Feature15UI.java — JTabbedPane for categories + card navigator panel
```

---

## 🎨 UI Style Tips
- Trend card: large panel with a two-column layout (Traditional left | Modern right)
- Separator arrow "→" in the center: big bold font, COLOR_SECONDARY
- Tab categories: use JTabbedPane — Traditional (warm brown), Modern (teal), Global (purple)
- Navigation: "← Prev" and "Next →" buttons at the bottom with current index display
- "Trending in" tag: pill-shaped JLabel with rounded border, orange background
