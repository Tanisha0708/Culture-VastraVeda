# 🎊 Feature 5 — Occasion Guide

## What This Feature Should Do
Help users find the right outfit for any occasion — weddings, religious ceremonies, festivals, casual wear, formal events, and dance performances.

---

## 📐 Expected UI Layout

```
┌─────────────────────────────────────────────────────────┐
│  🎊  Occasion Guide                                      │  ← createHeader()
│  Find the right outfit for every event                   │
├─────────────────────────────────────────────────────────┤
│  [💍 Wedding] [🕌 Religious] [🎉 Festival] [👔 Formal]  │  ← Occasion buttons
│  [👕 Casual]  [💃 Dance]    [❄️ Winter]                 │
├──────────────────────────────────────────────────────────┤
│  Occasion: WEDDING                                        │
│  ┌─────────────────────────────────────────────────────┐ │
│  │ 🥻 Banarasi Saree    │ 🥼 Sherwani                  │ │
│  │ Uttar Pradesh · Silk │ North India · Silk/Brocade   │ │
│  │ [Details]            │ [Details]                    │ │
│  └─────────────────────────────────────────────────────┘ │
│  💡 Tip: "For weddings, brides traditionally wear red..." │
└──────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Occasion Buttons** — at least 6 clickable occasion categories
2. **Filtered Results** — show `ClothingItem` cards filtered by occasion using `FilterUtils`
3. **Styling Tip Panel** — a short text tip for each occasion (hardcode in `Feature5Service`)
4. **Item Cards** — show icon, name, region, fabric for each result
5. **"No items" message** — if filter returns empty, show a friendly message

---

## 🎯 Occasion Categories & Tips (hardcode in Feature5Service.java)

| Occasion | Icon | Styling Tip |
|----------|------|-------------|
| Wedding | 💍 | "Brides traditionally wear red or maroon silk. Grooms opt for sherwani with dupatta." |
| Festival | 🎉 | "Choose bright colours! Avoid black and white during most Hindu festivals." |
| Religious | 🕌 | "Prefer modest, covered clothing. Cotton or silk in muted tones is ideal." |
| Formal | 👔 | "Structured kurta or saree with minimal embellishment works for professional events." |
| Casual | 👕 | "Light cotton kurtas, churidars, and simple salwar suits are everyday staples." |
| Dance | 💃 | "Classical dance forms have specific costumes — Bharatanatyam uses silk with gold border." |
| Winter | ❄️ | "Layer a Pashmina shawl over your outfit. Woollen angrakhas work for North India winters." |

---

## 💻 Code Structure Expected

```java
// Feature5Service.java
public class Feature5Service {
    public String getTipForOccasion(String occasion) { ... }
    public Color getAccentColor(String occasion) { ... }
}

// Feature5UI.java
public class Feature5UI extends BaseUI implements Feature {
    private void showOccasion(String occasion) {
        // Filter DataStore, display cards + tip
        List<ClothingItem> items = FilterUtils.filterByOccasion(occasion);
    }
}
```

---

## 🎨 UI Style Tips
- Use `JPanel` with `GridLayout(0, 2)` for the item cards grid
- Occasion buttons row: `FlowLayout` with colored buttons
- Tip box: light yellow background (`new Color(255, 253, 220)`), italic font
- Wrap results in `JScrollPane`
