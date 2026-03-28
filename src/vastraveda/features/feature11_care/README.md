# 🧺 Feature 11 — Care & Maintenance

## What This Feature Should Do
Teach users how to properly care for their traditional Indian garments, grouped by fabric type with washing symbols, storage tips, and do's & don'ts.

---

## 📐 Expected UI Layout

```
┌─────────────────────────────────────────────────────────────┐
│  🧺  Care & Maintenance                                      │
│  Preserve the beauty of your traditional garments           │
├──────────────┬──────────────────────────────────────────────┤
│ FABRIC LIST  │  CARE GUIDE PANEL                            │
│              │                                              │
│ 🐛 Silk      │  🐛 Silk — Care Guide                       │
│ 🌾 Cotton    │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ │
│ ❄️ Pashmina  │  ✅ DO:                                      │
│ ✨ Brocade   │    • Dry clean only                         │
│ 🌿 Linen     │    • Store in muslin/cotton cloth           │
│              │    • Keep away from direct sunlight         │
│              │                                             │
│              │  ❌ DON'T:                                   │
│              │    • Never machine wash                     │
│              │    • Avoid perfume contact                  │
│              │    • Don't hang — fold instead             │
│              │                                             │
│              │  📦 Storage Tip:                            │
│              │    Wrap in muslin, store flat               │
│              │                                             │
│              │  Garments using this fabric:               │
│              │  🥻 Banarasi Saree  🥻 Kanjivaram Saree    │
└──────────────┴──────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Fabric sidebar** — clickable list of fabrics (Silk, Cotton, Wool, Pashmina, Brocade, Linen)
2. **Care panel** — for each fabric show: Do's list, Don'ts list, Storage tip, Wash symbol
3. **Garment linkage** — show which DataStore items use that fabric (use FilterUtils.filterByFabric)
4. **Visual Do/Don't** — use ✅ and ❌ icons, green/red color coding
5. **Washing symbols** — show text-based laundry symbols (e.g., "🚫🫧 No machine wash")

---

## 🧼 Care Data to Include (hardcode in Feature11Service.java)

| Fabric | Wash | Do's | Don'ts | Storage |
|--------|------|------|--------|---------|
| Silk | Dry clean only | Store in muslin; fold don't hang | No machine wash; no direct sun; no perfume | Wrap in white muslin |
| Cotton | Machine wash cold | Iron while damp; air dry | Avoid bleach on colored | Fold or roll |
| Wool | Hand wash cold or dry clean | Lay flat to dry; use cedar balls | No tumble dry; don't wring | Fold with cedar balls |
| Pashmina | Dry clean only | Store flat; use mesh bag for travel | Never hang; no water | Wrap in tissue paper |
| Brocade | Dry clean only | Handle zari with care | No rubbing; no water | Store separately |
| Linen | Machine wash cool | Iron while slightly damp | Avoid over-drying | Hang or fold loosely |

---

## 💻 Code Structure Expected

```java
// Feature11Service.java
public class Feature11Service {
    public String[] getDos(String fabric) { ... }
    public String[] getDonts(String fabric) { ... }
    public String getStorageTip(String fabric) { ... }
    public String getWashSymbol(String fabric) { ... }
}
// Feature11UI.java — JSplitPane with fabric JList on left + care JPanel on right
```

---

## 🎨 UI Style Tips
- Do's section: light green background panel, ✅ prefix on each bullet
- Don'ts section: light red background panel, ❌ prefix on each bullet
- Storage tip: light blue background with 📦 icon
- Use BoxLayout(Y_AXIS) for the care panel with spacing between sections
