# 🖼 Feature 9 — Visual Gallery

## What This Feature Should Do
A visually rich card gallery showing all clothing items as large cards with their emoji icon, name, region tag, and quick stats. Clicking a card opens a full detail view.

---

## 📐 Expected UI Layout

```
┌──────────────────────────────────────────────────────────────┐
│  🖼  Visual Gallery                                           │
│  Browse traditional Indian clothing styles                    │
├──────────────────────────────────────────────────────────────┤
│  View: [🔲 Grid]  [≡ List]      Filter: [All ▼]  [Search🔍] │
├──────────────────────────────────────────────────────────────┤
│  ┌────────────┐  ┌────────────┐  ┌────────────┐             │
│  │     🥻     │  │     🧣     │  │     👘     │             │
│  │            │  │            │  │            │             │
│  │  Banarasi  │  │  Phulkari  │  │   Dhoti    │             │
│  │   Saree    │  │  Dupatta   │  │            │             │
│  │            │  │            │  │            │             │
│  │ Uttar Prad │  │   Punjab   │  │  Pan-India │             │
│  │ 🏷 Silk    │  │ 🏷 Cotton  │  │ 🏷 Cotton  │             │
│  │ 💍 Wedding │  │ 🎉 Festival│  │ 🙏 Casual  │             │
│  └────────────┘  └────────────┘  └────────────┘             │
└──────────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Grid view** — `GridLayout(0, 3)` showing all items as cards — this is the default view
2. **List view** — toggle to show items as horizontal rows (like Feature 1's table, but styled)
3. **Filter dropdown** — filter by gender (`All`, `Female`, `Male`, `Unisex`)
4. **Search bar** — live search by name as user types
5. **Clickable cards** — clicking opens a full detail dialog (`JOptionPane` or a new `JDialog`)
6. **Card design** — each card shows: large emoji icon, name, region, fabric tag, occasion tag

---

## 💻 Code Structure Expected

```java
// Feature9Service.java
public class Feature9Service {
    public String buildDetailText(ClothingItem item) {
        // Returns a formatted multi-line string for the detail dialog
    }
}

// Feature9UI.java
public class Feature9UI extends BaseUI implements Feature {
    private JPanel galleryPanel;
    private boolean isGridView = true;

    private void loadGallery(List<ClothingItem> items) {
        galleryPanel.removeAll();
        if (isGridView) {
            galleryPanel.setLayout(new GridLayout(0, 3, 12, 12));
            for (ClothingItem item : items) {
                galleryPanel.add(buildCard(item));
            }
        } else {
            galleryPanel.setLayout(new BoxLayout(galleryPanel, BoxLayout.Y_AXIS));
            for (ClothingItem item : items) {
                galleryPanel.add(buildListRow(item));
            }
        }
        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

    private JPanel buildCard(ClothingItem item) {
        // Icon (large), name, region, tags
        // Add MouseListener for click → showDetail(item)
    }
}
```

---

## 🎨 UI Style Tips
- Card size: fixed `200 × 220` px using `setPreferredSize(new Dimension(200, 220))`
- Card background: alternate between `COLOR_CARD` and `new Color(255, 252, 240)`
- Icon font size: `new Font("Serif", Font.PLAIN, 42)` — big and central
- Tag pills: small colored `JLabel` with rounded border using `BorderFactory.createLineBorder`
- Hover: darken card background slightly in `mouseEntered` / `mouseExited`
- Use `JTextField` with `DocumentListener` for live search (updates gallery on every keystroke)
