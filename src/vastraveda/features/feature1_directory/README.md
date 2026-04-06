 Feature 1 — Recipe Encyclopedia
What This Feature Should Do

Display a structured list of recipes categorized by region, community, and dietary type, with filtering and pagination.

 Expected UI Layout
 ┌──────────────────────────────────────────────┐
│  🥗 Recipe Encyclopedia                      │  ← createHeader()
│  Explore traditional Indian recipes          │
├──────────────────────────────────────────────┤
│  FILTER BAR                                 │
│  [Region] [Diet]                            │
├──────────────────────────────────────────────┤
│  RECIPE GRID                                │
│  ┌────────────┐  ┌────────────┐             │
│  │ Rogan Josh │  │ Dosa       │             │
│  │ Kashmir    │  │ Tamil Nadu │             │
│  │ Non-Veg    │  │ Veg        │             │
│  └────────────┘  └────────────┘             │
├──────────────────────────────────────────────┤
│  PAGINATION (1 2 3 ...)                     │
└──────────────────────────────────────────────┘

✅ Minimum Requirements
Display at least 10 recipes
Add filters:
Region
Dietary Tag
Show recipe details:
Name, region, community, dietary tag, description
Add pagination (5–6 items per page)
Show:
Loading state
Empty state
🗂 Recipe Data (Feature1Service.java)

Each recipe:

{ id, name, region, community, dietaryTag, description }
💻 Code Structure Expected
// Feature1Service.java
public class Feature1Service {
    public List<Recipe> getAllRecipes() { ... }
}

// Feature1UI.java
public class Feature1UI extends BaseUI implements Feature {
    private void buildUI() {
        // Filter bar
        // Recipe grid
        // Pagination
    }
}
🎨 UI Style Tips
Use grid layout (2–3 columns)
Highlight active filters
Cards should be compact
🔗 DataStore Methods
FilterUtils.filterByRegion("Punjab")
FilterUtils.filterByDiet("Veg")
DataStore.getAllItems()