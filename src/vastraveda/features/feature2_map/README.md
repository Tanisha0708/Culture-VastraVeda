What This Feature Should Do

Allow users to click on regions of India and view recipes from that region.

Expected UI Layout
┌──────────────────────────────────────────────┐
│  🗺 Interactive Food Map                     │
│  Click a region to explore dishes            │
├──────────────────┬───────────────────────────┤
│   MAP PANEL      │   RECIPE PANEL            │
│                  │                           │
│  [Punjab]        │  Selected: Punjab         │
│  [Gujarat]       │  - Sarson da Saag         │
│  [Kerala]        │  - Makki di Roti          │
│                  │                           │
└──────────────────┴───────────────────────────┘
Minimum Requirements
Show at least 10 regions/states
Clicking a region:
Highlights it
Shows related recipes
Display:
Recipe name
Dietary tag
Community
Show placeholder when nothing selected
🗂 Region Data
{ name, x, y } // for positioning
💻 Code Structure Expected
// Feature2Service.java
public class Feature2Service {
    public List<Recipe> getRecipesByRegion(String region) { ... }
}

// Feature2UI.java
public class Feature2UI extends BaseUI implements Feature {
    private void buildUI() {
        // Left: map grid
        // Right: recipe panel
    }
}
 UI Style Tips
Use grid layout for map
Highlight selected region
Keep labels short
🔗 DataStore Methods
FilterUtils.filterByRegion("Punjab")