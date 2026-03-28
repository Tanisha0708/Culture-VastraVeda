# ⚖️ Feature 8 — Compare Outfits

## What This Feature Should Do
Allow users to select two clothing items side-by-side and compare them across all attributes — fabric, region, occasion, era, care, and description.

---

## 📐 Expected UI Layout

```
┌─────────────────────────────────────────────────────────────┐
│  ⚖️  Compare Outfits                                         │
│  Select two garments to compare side by side                 │
├─────────────────┬──────────────┬───────────────────────────┤
│  LEFT GARMENT   │    VS        │  RIGHT GARMENT             │
│  [Dropdown ▼]   │      ⚖️      │  [Dropdown ▼]              │
├─────────────────┴──────────────┴───────────────────────────┤
│  Attribute      │  Banarasi Saree    │  Kanjivaram Saree    │
│  ─────────────────────────────────────────────────────────  │
│  Icon           │       🥻           │       🥻              │
│  Region         │  Uttar Pradesh     │  Tamil Nadu          │
│  Fabric         │  Silk              │  Silk         ✓ same │
│  Occasion       │  Wedding           │  Wedding      ✓ same │
│  Gender         │  Female            │  Female       ✓ same │
│  Era            │  Mughal Era        │  17th century        │
│  Care           │  Dry clean only    │  Dry clean only      │
│  Description    │  A luxurious...    │  A heavyweight...    │
└─────────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Two dropdowns** — each populated with all `ClothingItem` names from `DataStore`
2. **Compare button** — triggers the comparison table to render
3. **Comparison table** — rows for: Icon, Name, Region, Fabric, Occasion, Gender, Era, Care, Description
4. **Highlight matching values** — if both items share the same value for an attribute, mark it (✓ or green background)
5. **Highlight differences** — different values shown in a contrasting color
6. **Reset button** — clears both selections

---

## 💻 Code Structure Expected

```java
// Feature8Service.java
public class Feature8Service {
    public boolean valuesMatch(String val1, String val2) {
        return val1 != null && val1.equalsIgnoreCase(val2);
    }
    public String[][] buildComparisonRows(ClothingItem a, ClothingItem b) {
        // Returns: { { "Attribute", "Value from A", "Value from B" }, ... }
        return new String[][] {
            { "Icon",        a.getImageIcon(),        b.getImageIcon() },
            { "Region",      a.getRegion(),           b.getRegion() },
            { "Fabric",      a.getFabricType(),        b.getFabricType() },
            { "Occasion",    a.getOccasion(),          b.getOccasion() },
            { "Gender",      a.getGender(),            b.getGender() },
            { "Era",         a.getEra(),               b.getEra() },
            { "Care",        a.getCareInstructions(),  b.getCareInstructions() },
            { "Description", a.getDescription(),       b.getDescription() }
        };
    }
}

// Feature8UI.java
public class Feature8UI extends BaseUI implements Feature {
    private JComboBox<String> leftPicker, rightPicker;
    private JPanel comparePanel;

    private void compare() {
        // Get selected items from DataStore by name
        // Build comparison rows using Feature8Service
        // Render as a styled JTable or custom panel
    }
}
```

---

## 🎨 UI Style Tips
- Use `JSplitPane` or a `GridLayout(1, 3)` for left / center / right layout
- Matching rows: light green background `new Color(232, 245, 233)`
- Different rows: light orange background `new Color(255, 243, 224)`
- Description row: wrap text using `JTextArea` inside the cell
- Use `JTable` with a custom `DefaultTableCellRenderer` to colour rows, OR build custom `JPanel` rows
- The "VS" center panel should be bold, large font, centered
