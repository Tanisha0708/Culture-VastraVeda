# Feature 7 — Historical Timeline

## What This Feature Should Do
Display a vertical scrollable timeline showing how Indian clothing evolved from the Vedic period to modern day, with relevant garments from `DataStore` linked to each era.

---

## Expected UI Layout

```
┌─────────────────────────────────────────────────────────┐
│  📜  Historical Timeline                                  │
│  Clothing through Indian history                          │
├───────────────┬─────────────────────────────────────────┤
│  ERA SELECTOR │  TIMELINE PANEL (scrollable)             │
│               │                                          │
│  ○ Ancient    │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━          │
│  ○ Vedic      │  🔵 Ancient India (3000 BCE – 600 CE)    │
│  ○ Mughal     │     Unstitched draped garments...        │
│  ○ Colonial   │     Items: Dhoti, Kasavu Saree           │
│  ○ Modern     │                                          │
│               │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━          │
│               │  🟡 Mughal Era (1526 – 1857)             │
│               │     Introduction of stitched garments... │
│               │     Items: Sherwani, Banarasi Saree      │
│               │                                          │
│               │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━          │
│               │  🟠 Colonial Period (1858 – 1947)        │
│               │     Fusion of Indian & Western styles... │
└───────────────┴─────────────────────────────────────────┘
```

---

## Minimum Requirements

1. **At least 5 historical eras** displayed on the timeline
2. **Era selector** on the left (JList or buttons) — clicking jumps to that era
3. **Timeline entries** — each era shows: time period, brief historical context, and linked clothing items
4. **ClothingItem linkage** — match items from `DataStore` using `item.getEra()` or keyword match
5. **Scrollable panel** — the timeline should be inside a `JScrollPane`
6. **Visual timeline line** — a vertical colored line connecting the eras (draw using `paintComponent` or use a thin `JPanel` strip)

---

## 🏛 Eras to Include (hardcode in Feature7Service.java)

| Era | Time Period | Key Development | Colour |
|-----|------------|-----------------|--------|
| Ancient / Vedic | 3000 BCE – 600 CE | Unstitched draped garments; cotton cultivation in Indus Valley | `#4CAF50` |
| Classical | 600 CE – 1200 CE | Regional styles emerge; silk weaving begins | `#2196F3` |
| Mughal Era | 1526 – 1857 | Stitched garments introduced; zari/brocade flourishes; Banarasi silk | `#9C27B0` |
| Colonial Period | 1858 – 1947 | Western influence; Khadi movement; fusion styles | `#FF9800` |
| Post-Independence | 1947 – 1990 | National dress identity; handloom promotion | `#F44336` |
| Modern Era | 1990 – Present | Designer fusion; global Indian fashion; bridal couture | `#00BCD4` |

---

## Code Structure Expected

```java
// Feature7Service.java
public class Feature7Service {
    public String[][] getEras() {
        // { eraName, timePeriod, description, colorHex }
    }
    public List<ClothingItem> getItemsForEra(String eraKeyword) {
        // filter DataStore items whose getEra() contains the keyword
        List<ClothingItem> result = new ArrayList<>();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getEra().toLowerCase().contains(eraKeyword.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }
}

// Feature7UI.java
public class Feature7UI extends BaseUI implements Feature {
    private void buildTimeline() {
        // For each era: create a JPanel "card" with era info + matching items
        // Stack them vertically with Box.createRigidArea() spacing
    }
}
```

---

## 🎨 UI Style Tips
- Timeline line: a 4px-wide `JPanel` strip on the left side of each card with era's accent color
- Era dot: a colored circle (paint a filled oval in a custom `JPanel`)
- Cards: use `createCard()` from `BaseUI`, add `BorderFactory.createMatteBorder(0, 5, 0, 0, eraColor)`
- Scroll to selected era: use `JScrollPane.getVerticalScrollBar().setValue(position)`
