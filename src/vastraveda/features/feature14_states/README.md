Problem Statement - India's diverse textile traditions — spanning handlooms, embroidery styles, regional costumes, and fabric origins across states — are largely undocumented in a unified digital space. There is no community-driven mobile app that helps people identify, explore, and preserve India's clothing culture across regions, communities, and generations.


# Feature 14 — State Profiles

## What This Feature Should Do
A detailed profile page for each Indian state showing its textile identity — signature garments, dominant fabrics, signature colours, and GI-tagged items.

---

## Expected UI Layout

```
┌──────────────────────────────────────────────────────────────┐
│  🏛  State Profiles                                           │
│  Explore the clothing identity of every Indian state          │
├───────────────────┬──────────────────────────────────────────┤
│  STATE SELECTOR   │  STATE PROFILE                           │
│                   │                                          │
│  [Assam       ▼]  │  🏛 PUNJAB                               │
│                   │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  │
│  OR: A-Z list:    │  📍 Region: North India                  │
│                   │  🎨 Signature Colours: Orange, Green     │
│  Andhra Pradesh   │  🏷 GI-Tagged Items: Phulkari            │
│  Assam            │                                          │
│  Bihar            │  Signature Garments:                     │
│  Goa              │  🧣 Phulkari Dupatta                     │
│  Gujarat          │  👗 Salwar Kameez                        │
│  Karnataka        │                                          │
│  Kerala           │  About Punjab's Textile Culture:        │
│  Manipur          │  Punjab is renowned for its vibrant     │
│  Punjab  ←        │  Phulkari embroidery...                  │
│  Rajasthan        │                                          │
│  Tamil Nadu       │  Famous Weaving Centres: Amritsar,      │
│  Telangana        │  Ludhiana                               │
│  Uttar Pradesh    │                                          │
└───────────────────┴──────────────────────────────────────────┘
```

---

## Minimum Requirements

1. **State list/dropdown** — show at least 12 Indian states
2. **State profile panel** with sections: Signature Colours, GI Items, Signature Garments, About, Weaving Centres
3. **Linked garments** — use `FilterUtils.filterByRegion(state)` to pull DataStore items
4. **Signature colours** — display as small colored squares (painted JPanels), not just text
5. **GI tag badge** — show a "🏷 GI Tagged" label for items with geographical indication

---

## 🗺 State Data to Include (hardcode in Feature14Service.java)

| State | Signature Garment | Sig. Colours | GI Items | Weaving Centres |
|-------|------------------|-------------|----------|-----------------|
| Punjab | Phulkari Dupatta | Orange, Green, Pink | Phulkari | Amritsar |
| Rajasthan | Bandhani Saree, Ghagra Choli | Red, Yellow, Orange | Bandhani, Leheriya | Jodhpur, Jaipur |
| Uttar Pradesh | Banarasi Saree | Red, Gold, Green | Banarasi Silk | Varanasi |
| Kashmir | Pashmina Shawl | White, Cream, Saffron | Pashmina, Kani Shawl | Srinagar |
| Gujarat | Bandhani, Ghagra | Red, Green, Yellow | Patola Silk | Patan, Surat |
| Tamil Nadu | Kanjivaram Saree | Red, Gold, Blue | Kanjivaram Silk | Kanchipuram |
| Kerala | Kasavu Saree | White, Gold | Kasavu | Balaramapuram |
| Assam | Mekhela Chador | Red, White, Gold | Muga Silk | Sualkuchi |
| West Bengal | Jamdani Saree | White, Red | Jamdani, Baluchari | Murshidabad |
| Telangana | Pochampally Saree | Multi-colour Ikat | Pochampally Ikat | Pochampally |
| Manipur | Phanek | Black, Red, Green | Manipuri Shawl | Imphal |
| Odisha | Sambalpuri Saree | Red, Black, White | Sambalpuri, Odisha Ikat | Sambalpur |

---

## Code Structure Expected

```java
// Feature14Service.java
public class Feature14Service {
    public static class StateProfile {
        public String stateName, about, weavingCentres;
        public String[] signatureColours; // hex codes
        public String[] giItems;
        // constructor
    }
    public List<StateProfile> getAllStates() { ... }
    public StateProfile getState(String name) { ... }
}
// Feature14UI.java — JList/JComboBox on left + profile JPanel on right
```

---

## UI Style Tips
- Colour swatches: small `JPanel` of size `24×24` with `setBackground(Color.decode(hex))`
- State list: `JList` with single selection, `ListSelectionListener` → load profile
- GI badge: small `JLabel` with gold border and "🏷 GI Tagged" text
- Divider between sections: thin `JSeparator` with warm color
