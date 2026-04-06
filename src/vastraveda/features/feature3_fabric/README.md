# 🗓 Feature 3 — Ingredient Origins Tracker

## What This Feature Should Do

Show how ingredients originated and spread across regions using a simple timeline or list view.

---

## Expected UI Layout

```
┌──────────────────────────────────────────────┐
│  🌿 Ingredient Origins Tracker              │
│  Discover how ingredients spread            │
├──────────────────────────────────────────────┤
│  INGREDIENT LIST                            │
│  [Chili] [Rice] [Turmeric]                  │
├──────────────────────────────────────────────┤
│  DETAILS PANEL                              │
│  Ingredient: Chili                          │
│  Origin: Americas                           │
│  Spread: via trade routes                   │
│  Regions: India (all)                       │
└──────────────────────────────────────────────┘
```

---

## Minimum Requirements

Display at least 6 ingredients
Each ingredient must have:
Name
Origin
Description
Regions used
Clicking an ingredient shows details
Optional: timeline-style display
🌿 Ingredient Data (Feature3Service.java)
{ name, origin, description, regions }

---
## Code Structure Expected

```java
// Feature3Service.java
public class Feature3Service {
    public List<Ingredient> getAllIngredients() { ... }
}

// Feature3UI.java
public class Feature3UI extends BaseUI implements Feature {
    private void buildUI() {
        // Left: ingredient list
        // Right: details panel
    }
}
```

---

## UI Style Tips
Use list + detail layout
Highlight selected ingredient
Keep text readable
---

## 🔗 DataStore Methods to Use
```java
DataStore.getAllItems()
FilterUtils.filterByRegion("India")
```