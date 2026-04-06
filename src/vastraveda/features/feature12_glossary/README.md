# Feature 12 — Glossary

## What This Feature Should Do
An A–Z dictionary of Indian textile and clothing terms. Users can browse alphabetically, search by keyword, or click a term to see its full definition.

---

## Expected UI Layout

```
┌─────────────────────────────────────────────────────────────┐
│  📚  Glossary                                                 │
│  Indian textile & clothing terms from A to Z                 │
├─────────────────────────────────────────────────────────────┤
│  🔍 [Search terms...              ]                          │
│  [A] [B] [C] [D] [E] [F] [G] [H] [I] [J] [K] [L] [M]      │
│  [N] [O] [P] [Q] [R] [S] [T] [U] [V] [W] [X] [Y] [Z]      │
├───────────────────┬─────────────────────────────────────────┤
│  TERM LIST        │  DEFINITION PANEL                        │
│                   │                                          │
│  Angrakha         │  Zari                                    │
│  Bandhani         │  ─────────────────────────────────────  │
│  Brocade          │  Metallic thread (gold or silver)       │
│  Choli            │  woven or embroidered into fabric.      │
│  Dupatta          │  Used extensively in Banarasi and       │
│  Ghagra           │  Kanjivaram sarees.                     │
│  ...              │                                          │
│  Zari ←selected   │  Related items: Banarasi Saree,        │
│                   │  Kanjivaram Saree, Sherwani             │
└───────────────────┴─────────────────────────────────────────┘
```

---

## Minimum Requirements

1. **A–Z alphabet bar** — clicking a letter filters the list to terms starting with that letter
2. **Search bar** — live search filters the term list as user types
3. **Term list** — `JList` on the left, sorted alphabetically, at least 25 terms
4. **Definition panel** — shows full definition + example usage when a term is selected
5. **Related items** — if the term relates to a DataStore item, show it linked at the bottom

---

## Terms to Include (hardcode in Feature12Service.java — at least 25)

| Term | Definition |
|------|-----------|
| Angrakha | Asymmetric wrap tunic worn in Rajasthan |
| Bandhani | Tie-dye technique creating dot patterns |
| Brocade | Richly woven fabric with raised patterns |
| Choli | Short blouse worn with saree or lehenga |
| Dupatta | Long scarf/veil worn over salwar or saree |
| Ghagra | Full flared skirt worn in Rajasthan/Gujarat |
| Ikat | Resist-dyeing technique used in Pochampally |
| Jamdani | Fine muslin with woven patterns from Bengal |
| Kameez | Long tunic worn in salwar-kameez |
| Khadi | Hand-spun, hand-woven cloth; symbol of independence |
| Kurta | Collarless shirt worn by men and women |
| Lehenga | Long embroidered skirt for weddings |
| Mekhela | Lower garment of Assamese two-piece dress |
| Muga | Golden silk unique to Assam |
| Pallu | Decorative end of a saree draped over shoulder |
| Pashmina | Luxury wool from Changthangi goats |
| Phulkari | Floral embroidery on cotton from Punjab |
| Salwar | Loose trousers in salwar-kameez |
| Saree | 5–9 yards of unstitched draping fabric |
| Sherwani | Long formal coat worn by men |
| Silk | Natural protein fibre from silkworm cocoons |
| Tilla | Silver thread embroidery from Kashmir |
| Zardozi | Heavy gold embroidery using metallic threads |
| Zari | Metallic gold/silver thread used in weaving |
| Churidar | Close-fitting trousers gathered at ankles |

---

## Code Structure Expected

```java
// Feature12Service.java
public class Feature12Service {
    private static final Map<String, String> GLOSSARY = new TreeMap<>(); // TreeMap = auto-sorted A-Z
    public Map<String, String> getAll() { return GLOSSARY; }
    public Map<String, String> filterByLetter(char letter) { ... }
    public Map<String, String> search(String query) { ... }
}
// Feature12UI.java — alphabet bar + JSplitPane(JList, definition JTextArea)
```

---

## UI Style Tips
- Alphabet bar: `FlowLayout` row of small `JButton`s, each 28×28px
- Active letter button: highlighted with `COLOR_PRIMARY` background
- Term list: `JList<String>` with `ListSelectionListener` → update definition panel
- Definition panel: Serif font, large padding, warm background
