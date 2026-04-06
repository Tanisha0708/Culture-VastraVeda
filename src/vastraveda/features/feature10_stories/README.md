# Feature 10 — Cultural Stories

## What This Feature Should Do
Display the rich cultural stories, legends, and historical origin tales behind iconic Indian garments. A reading-focused feature with a story list on the left and a reading pane on the right.

---

## Expected UI Layout

```
┌─────────────────────────────────────────────────────────────┐
│  📖  Cultural Stories                                         │
│  The legends and history behind iconic Indian garments        │
├───────────────────┬─────────────────────────────────────────┤
│  STORY LIST       │  READING PANE                            │
│                   │                                          │
│  🥻 Banarasi...   │  🥻 The Legend of the Banarasi Saree    │
│  🧣 Phulkari...   │  ─────────────────────────────────────  │
│  👘 The Dhoti...  │  The city of Varanasi has been a        │
│  🥼 Sherwani...   │  weaving centre for over 2,000 years.   │
│  🧣 Pashmina...   │  According to legend, the zari weaving  │
│  🥻 Kasavu...     │  technique was brought by...            │
│                   │                                          │
│                   │  📍 Region: Uttar Pradesh               │
│                   │  🧵 Fabric: Silk with gold zari         │
│                   │  👗 Worn for: Weddings, festivals       │
└───────────────────┴─────────────────────────────────────────┘
```

---

## Minimum Requirements

1. **Story list** on the left — JList with item names and icons
2. **Reading pane** on the right — JTextArea (non-editable, word-wrapped) with the story
3. **At least 7 stories** — one per major ClothingItem in DataStore
4. **Story metadata** — below the story, show region, fabric, and occasion
5. **Reading-friendly styling** — larger font (14pt Serif), generous padding

---

## Stories to Include (hardcode in Feature10Service.java)

| Item | Story Angle |
|------|------------|
| Banarasi Saree | 2000-year weaving history in Varanasi; Mughal influence on zari |
| Phulkari Dupatta | Punjabi women stitched these for daughters trousseaus |
| Pashmina Shawl | Changthangi goats of Ladakh; Marco Polo accounts |
| Kanjivaram Saree | Sage Markanda wove the first Kanjivaram from lotus fibres |
| Mekhela Chador | Muga silk unique to Brahmaputra valley |
| Bandhani | 5000-year-old technique found in Indus Valley artifacts |
| Kasavu Saree | Cultural identity of Kerala women; Onam and Mahabali |

---

## Code Structure Expected

```java
// Feature10Service.java
public class Feature10Service {
    private static final Map<String, String> STORIES = new LinkedHashMap<>();
    static {
        STORIES.put("Banarasi Saree", "The city of Varanasi has been a silk weaving hub for over 2,000 years...");
    }
    public List<String> getStoryTitles() { return new ArrayList<>(STORIES.keySet()); }
    public String getStory(String title) { return STORIES.getOrDefault(title, "Story coming soon..."); }
}
// Feature10UI.java — JSplitPane with JList on left + JTextArea on right
```

---

## UI Style Tips
- JSplitPane(HORIZONTAL_SPLIT) with divider at 220px
- Reading pane font: new Font("Serif", Font.PLAIN, 14)
- Background: new Color(255, 252, 240) — warm parchment feel
- Custom ListCellRenderer to show emoji + name in the list
