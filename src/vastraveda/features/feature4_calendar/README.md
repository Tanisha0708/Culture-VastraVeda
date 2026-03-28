# 🗓 Feature 4 — Festival Calendar

## What This Feature Should Do
Display a seasonal calendar showing which Indian festivals happen throughout the year, and what traditional clothing is worn during each festival.

---

## 📐 Expected UI Layout

```
┌─────────────────────────────────────────────────────────┐
│  🗓  Festival Calendar                                   │  ← createHeader()
│  Discover clothing worn during Indian festivals          │
├──────────────────┬──────────────────────────────────────┤
│  SEASON TABS     │  FESTIVAL LIST + CLOTHING PANEL       │
│  ┌────────────┐  │  ┌──────────────────────────────────┐│
│  │ 🌸 Spring  │  │  │ Festival: Holi                   ││
│  │ ☀️ Summer  │  │  │ Month: March                     ││
│  │ 🍂 Autumn  │  │  │ Region: North India              ││
│  │ ❄️ Winter  │  │  │ Clothing: White cotton kurta...  ││
│  └────────────┘  │  │                                  ││
│                  │  │ [Matching items from DataStore]  ││
│                  │  └──────────────────────────────────┘│
└──────────────────┴──────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **Season Tabs or Buttons** — at least 4 categories (Spring/Summer/Autumn/Winter OR Jan-Mar / Apr-Jun / Jul-Sep / Oct-Dec)
2. **Festival List** — show at least 8 festivals with: name, month, region, and 1-line description
3. **Clothing Panel** — for each festival, show relevant `ClothingItem` objects from `DataStore`
4. **Filter from DataStore** — use `FilterUtils.filterByOccasion("Festival")` to get festival-appropriate items

---

## 🗓 Festival Data to Use (hardcode in Feature4Service.java)

| Festival | Month | Region | Key Clothing |
|----------|-------|--------|-------------|
| Makar Sankranti | January | Pan-India | Cotton sarees, kite-flying casual wear |
| Pongal | January | Tamil Nadu | Kasavu saree, dhoti |
| Lohri | January | Punjab | Phulkari dupatta, bright salwar suits |
| Holi | March | North India | White cotton kurta (gets coloured!) |
| Baisakhi | April | Punjab | Phulkari, bright ghagra choli |
| Eid ul-Fitr | Varies | Pan-India | Pathani suit, sherwani, anarkali |
| Onam | August | Kerala | Kasavu saree (set mundu) |
| Navratri | October | Gujarat | Ghagra choli, chaniya choli |
| Diwali | October | Pan-India | Silk sarees, sherwani, lehenga |
| Durga Puja | October | West Bengal | Red-white saree (shakha pola) |
| Pushkar Mela | November | Rajasthan | Bandhani saree, angrakha |
| Christmas | December | Goa | Western-Indian fusion |

---

## 💻 Code Structure Expected

```java
// Feature4Service.java — store festival data here
public class Feature4Service {
    public List<String[]> getFestivalsBySeason(String season) { ... }
    // Each String[]: { festivalName, month, region, description, occasion }
}

// Feature4UI.java — build the Swing UI here
public class Feature4UI extends BaseUI implements Feature {
    private void buildUI() {
        // Left: season selector (JTabbedPane or JList)
        // Right: festival cards + matching ClothingItems
    }
}
```

---

## 🎨 UI Style Tips
- Use `BaseUI.COLOR_BG`, `COLOR_PRIMARY`, `COLOR_SECONDARY` for consistency
- Use `createHeader()` for the top bar
- Each festival card: accent color based on season (spring=green, summer=orange, autumn=red, winter=blue)
- Use `JTabbedPane` for seasons — it's clean and beginner-friendly

---

## 🔗 DataStore Methods to Use
```java
FilterUtils.filterByOccasion("Festival")   // gets festival clothing items
FilterUtils.filterByRegion("Kerala")        // gets region-specific items
DataStore.getAllItems()                      // browse everything
```
