# 🤝 Contributing to VastraVeda

Welcome to the VastraVeda open-source event! This guide will help you contribute your feature **without conflicts**.

---

## 🎯 Your Mission

Each participant owns exactly **one feature folder**. You implement the UI and logic inside it. You never touch anyone else's folder.

---

## ⚡ 5-Minute Setup

```bash
# 1. Fork the repo on GitHub, then clone your fork
git clone https://github.com/YOUR_USERNAME/VastraVeda.git
cd VastraVeda

# 2. Create your feature branch
git checkout -b feature/7-historical-timeline

# 3. Build & run to confirm everything works
./build.sh     # (or build.bat on Windows)
./run.sh       # You should see the main VastraVeda window

# 4. Open only YOUR feature files in your IDE
#    e.g. src/vastraveda/features/feature7_timeline/
```

---

## ✅ The Golden Rules

### Rule 1 — Only touch YOUR feature folder
```
✅ src/vastraveda/features/feature7_timeline/Feature7UI.java      ← YES
✅ src/vastraveda/features/feature7_timeline/Feature7Service.java  ← YES
❌ src/vastraveda/core/data/DataStore.java                         ← NEVER
❌ src/vastraveda/main/MainApp.java                                ← NEVER
❌ src/vastraveda/features/feature3_fabric/Feature3UI.java         ← NEVER
```

### Rule 2 — Extend BaseUI and implement Feature
```java
public class Feature7UI extends BaseUI implements Feature {
    @Override
    public void render() { setVisible(true); }
}
```

### Rule 3 — Use DataStore and FilterUtils for data
```java
// ✅ Correct — use core utilities
List<ClothingItem> items = DataStore.getAllItems();
List<ClothingItem> silk  = FilterUtils.filterByFabric("Silk");

// ❌ Wrong — don't create your own data lists
List<ClothingItem> myItems = new ArrayList<>();
myItems.add(new ClothingItem(...));
```

### Rule 4 — Use BaseUI constants for styling
```java
// ✅ Consistent styling
panel.setBackground(BaseUI.COLOR_BG);
label.setFont(BaseUI.FONT_TITLE);
JButton btn = createStyledButton("Go", BaseUI.COLOR_PRIMARY, BaseUI.COLOR_TEXT_LIGHT);

// ❌ Avoid hard-coded values
panel.setBackground(new Color(255, 248, 235));   // Don't do this
```

---

## 🏗 Feature Template

Copy this into your `FeatureXUI.java` and replace the placeholders:

```java
package vastraveda.features.featureX_yourname;   // ← change X and yourname

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;
import vastraveda.core.utils.FilterUtils;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║  FEATURE X — Your Feature Title                      ║
 * ║  CONTRIBUTOR: Your Name (@github_handle)             ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class FeatureXUI extends BaseUI implements Feature {   // ← change X

    private final FeatureXService service = new FeatureXService();  // ← change X

    public FeatureXUI() {
        super("Your Feature Title");   // ← change title
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        // Header — use the helper from BaseUI
        add(createHeader("🎯  Your Feature Title", "A short description"), BorderLayout.NORTH);

        // Main content panel
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(COLOR_BG);
        content.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // ── Build your UI here ──────────────────────────────────
        // Access data:
        List<ClothingItem> allItems = DataStore.getAllItems();
        List<ClothingItem> silkItems = FilterUtils.filterByFabric("Silk");

        // Example: show item count
        JLabel countLabel = new JLabel("Total items: " + allItems.size());
        countLabel.setFont(FONT_BODY);
        content.add(countLabel, BorderLayout.CENTER);
        // ───────────────────────────────────────────────────────

        add(content, BorderLayout.CENTER);
    }
}
```

---

## 📦 Submitting Your PR

```bash
# Stage only your feature files
git add src/vastraveda/features/featureX_yourname/

# Commit with a clear message
git commit -m "feat: implement Feature X — Your Feature Title

- Implemented FeatureXUI with [describe what you built]
- Added FeatureXService with [describe logic]
- Used DataStore.getAllItems() and FilterUtils"

# Push to your fork
git push origin feature/X-your-feature-title

# Open a Pull Request on GitHub
```

---

## 💡 Ideas for Your Feature

| Feature | Suggested Approach |
|---------|--------------------|
| Festival Calendar | `JTabbedPane` — one tab per season, show relevant items |
| Occasion Guide | Cards for Wedding / Casual / Religious / Formal |
| Clothing Quiz | 5 MCQ questions based on ClothingItem data |
| Historical Timeline | Vertical `JPanel` with era markers |
| Compare Outfits | `JSplitPane` with two item selectors |
| Visual Gallery | `GridLayout` of emoji + name cards |
| Cultural Stories | `JList` on left, story text on right |
| Care & Maintenance | Grouped list by fabric type |
| Glossary | Sorted `JList` + definition panel |
| Featured Designers | Imaginary artisan cards per region |
| State Profiles | `JComboBox` of states → filtered items |
| Modern Trends | Before/After comparison cards |

---

## 🆘 Getting Help

- Open a GitHub Discussion
- Tag `@maintainer` in your issue
- Review existing features (Feature1UI, Feature2UI, Feature3UI) for reference

---

*Happy coding! Let's celebrate Indian textile heritage together 🇮🇳*
