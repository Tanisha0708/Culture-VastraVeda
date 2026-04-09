# 🥻 VastraVeda — वस्त्र-वेद
Problem Statement- India's diverse textile traditions — spanning handlooms, embroidery styles, regional costumes, and fabric origins across states — are largely undocumented in a unified digital space. There is no community-driven mobile app that helps people identify, explore, and preserve India's clothing culture across regions, communities, and generations.

### The Encyclopedia of Traditional Indian Clothing
> *A modular Java Swing application built for open-source collaborative events*

---

## 📋 Table of Contents
- [About](#about)
- [Features](#features)
- [Project Structure](#project-structure)
- [OOP Architecture](#oop-architecture)
- [Quick Start](#quick-start)
- [How to Contribute a Feature](#how-to-contribute-a-feature)
- [Core API Reference](#core-api-reference)
- [Merge Conflict Prevention](#merge-conflict-prevention)

---

## About

**VastraVeda** is a Java Swing desktop application that explores traditional Indian clothing — sarees, sherwanis, dupattas, and more — across different regions, fabrics, occasions, and historical periods.

It is architecturally designed so that **15 contributors can each implement one feature independently**, with **zero risk of merge conflicts**.

---

## Features

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 1 | 🗂 Regional Directory | Filter & browse all garments | ✅ Implemented |
| 2 | 🗺 Clothing Map | Click states to explore regions | ✅ Implemented |
| 3 | 🧵 Fabric Explorer | Deep dive into Indian textiles | ✅ Implemented |
| 4 | 🗓 Festival Calendar | Clothing for each festival | 🔧 Ready for contributor |
| 5 | 🎊 Occasion Guide | Dress right for every event | 🔧 Ready for contributor |
| 6 | ❓ Clothing Quiz | Test your textile knowledge | 🔧 Ready for contributor |
| 7 | 📜 Historical Timeline | Clothing through Indian history | 🔧 Ready for contributor |
| 8 | ⚖️ Compare Outfits | Side-by-side garment comparison | 🔧 Ready for contributor |
| 9 | 🖼 Visual Gallery | Browse beautiful garment styles | 🔧 Ready for contributor |
| 10 | 📖 Cultural Stories | Legends behind iconic garments | 🔧 Ready for contributor |
| 11 | 🧺 Care & Maintenance | Preserve your traditional wear | 🔧 Ready for contributor |
| 12 | 📚 Glossary | Textile & clothing terms A–Z | 🔧 Ready for contributor |
| 13 | ✂️ Featured Designers | Artisans keeping craft alive | 🔧 Ready for contributor |
| 14 | 🏛 State Profiles | Clothing of every Indian state | 🔧 Ready for contributor |
| 15 | 🌟 Modern Trends | Traditional meets contemporary | 🔧 Ready for contributor |

---

## Project Structure

```
VastraVeda/
│
├── src/vastraveda/
│   │
│   ├── main/
│   │   └── MainApp.java              ← App launcher (touch only to register feature)
│   │
│   ├── core/                         ← 🔒 LOCKED — DO NOT MODIFY
│   │   ├── models/
│   │   │   └── ClothingItem.java     ← Data model with getters/setters
│   │   ├── data/
│   │   │   └── DataStore.java        ← 15 preloaded clothing items
│   │   └── utils/
│   │       ├── Feature.java          ← Interface: render() contract
│   │       ├── BaseUI.java           ← Abstract JFrame base class
│   │       └── FilterUtils.java      ← Static filter/search methods
│   │
│   └── features/                     ← Each contributor works here ONLY
│       ├── feature1_directory/
│       │   ├── Feature1UI.java       ← Swing UI (extends BaseUI, implements Feature)
│       │   └── Feature1Service.java  ← Business logic
│       ├── feature2_map/
│       │   ├── Feature2UI.java
│       │   └── Feature2Service.java
│       ├── feature3_fabric/
│       │   ├── Feature3UI.java
│       │   └── Feature3Service.java
│       └── ... feature4 through feature15 ...
│
├── out/                  ← Compiled .class files (git-ignored)
├── VastraVeda.jar        ← Runnable JAR (git-ignored)
├── build.sh              ← Build script (Linux/Mac)
├── build.bat             ← Build script (Windows)
├── run.sh                ← Run script (Linux/Mac)
├── run.bat               ← Run script (Windows)
├── CONTRIBUTING.md       ← Contributor guide
└── README.md
```

---

## OOP Architecture

### 1. Encapsulation — `ClothingItem.java`
All fields are `private` with public getters/setters. Data is protected from direct access.

```java
private String name;
private String region;
public String getName() { return name; }
public void setName(String name) { this.name = name; }
```

### 2. Abstraction — `Feature.java` (Interface)
Defines the contract every feature must honour:

```java
public interface Feature {
    void render();   // Each feature implements this
}
```

### 3. Inheritance — `BaseUI.java` (Abstract Base Class)
All feature UIs extend `BaseUI`, which extends `JFrame`. Shared colours, fonts, and helper methods live here — written once, used by all.

```java
public abstract class BaseUI extends JFrame {
    public static final Color COLOR_PRIMARY = new Color(139, 69, 19);
    protected JPanel createHeader(String title, String subtitle) { ... }
    protected JButton createStyledButton(String text, Color bg, Color fg) { ... }
}
```

### 4. Polymorphism — `MainApp.java`
The launcher holds a `Feature` reference and calls `render()` on whichever feature the user clicks. Each feature's own implementation runs — classic runtime polymorphism.

```java
Feature feature = new Feature1UI();   // or Feature2UI, Feature3UI ...
feature.render();                      // polymorphic dispatch
```

---

## Quick Start

### Prerequisites
- **Java JDK 11 or higher** — [Download from Adoptium](https://adoptium.net/)
- No external libraries required

### Option A — Run the Pre-built JAR
```bash
java -jar VastraVeda.jar
```

### Option B — Build from Source (Linux/macOS)
```bash
git clone https://github.com/your-org/VastraVeda.git
cd VastraVeda
chmod +x build.sh run.sh
./build.sh
./run.sh
```

### Option C — Build from Source (Windows)
```bat
git clone https://github.com/your-org/VastraVeda.git
cd VastraVeda
build.bat
run.bat
```

### Option D — Manual Compile
```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out vastraveda.main.MainApp
```

---

## How to Contribute a Feature

### Step 1 — Claim your feature
Comment on the GitHub issue for your feature number (e.g., `Feature 7 — Historical Timeline`).

### Step 2 — Work ONLY inside your feature folder
If you are assigned **Feature 7**, you only touch:
```
src/vastraveda/features/feature7_timeline/Feature7UI.java
src/vastraveda/features/feature7_timeline/Feature7Service.java
```

**Never modify** any file in `core/`, `main/`, or another feature's folder.

### Step 3 — Implement your Feature
Your `Feature7UI.java` must:
1. Extend `BaseUI`
2. Implement `Feature`
3. Override `render()` to call `setVisible(true)`

```java
package vastraveda.features.feature7_timeline;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.data.DataStore;
import vastraveda.core.utils.FilterUtils;
import javax.swing.*;
import java.awt.*;

public class Feature7UI extends BaseUI implements Feature {

    public Feature7UI() {
        super("Historical Timeline");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        // Use createHeader(), createCard(), etc. from BaseUI
        add(createHeader("📜 Historical Timeline", "Clothing through Indian history"), BorderLayout.NORTH);

        // Use DataStore.getAllItems() to get clothing data
        // Use FilterUtils.filterByRegion(...) etc. to filter

        // Build your Swing UI here...
    }
}
```

### Step 4 — Submit a Pull Request
Since you only modified files inside your feature folder, there will be **zero merge conflicts** with other contributors' PRs.

---

## Core API Reference

### `DataStore`
```java
DataStore.getAllItems()      → List<ClothingItem>   // All 15 items
DataStore.getAllRegions()    → List<String>          // Distinct regions
DataStore.getAllFabrics()    → List<String>          // Distinct fabrics
DataStore.getAllGenders()    → List<String>          // Female, Male, Unisex
DataStore.getAllOccasions()  → List<String>          // Distinct occasions
```

### `FilterUtils`
```java
FilterUtils.filterByRegion("Punjab")       → List<ClothingItem>
FilterUtils.filterByFabric("Silk")         → List<ClothingItem>
FilterUtils.filterByGender("Female")       → List<ClothingItem>
FilterUtils.filterByOccasion("Wedding")    → List<ClothingItem>
FilterUtils.filterMulti(region, fabric, gender, occasion)  → List<ClothingItem>
FilterUtils.searchByName("Saree")          → List<ClothingItem>
```

### `ClothingItem` Fields
| Getter | Description |
|--------|-------------|
| `getName()` | Garment name |
| `getRegion()` | Indian region/state |
| `getFabricType()` | Fabric (Silk, Cotton, etc.) |
| `getOccasion()` | Wedding, Festival, Casual, etc. |
| `getGender()` | Female / Male / Unisex |
| `getDescription()` | Full description |
| `getImageIcon()` | Emoji icon |
| `getEra()` | Historical period |
| `getCareInstructions()` | Washing/care advice |

### `BaseUI` Helpers
```java
createHeader(title, subtitle)              → JPanel   // Styled dark header
createStyledButton(text, bgColor, fgColor) → JButton  // Styled button
createCard()                               → JPanel   // Card with border
createTextArea(text)                       → JTextArea // Editable=false
createComboBox(items[])                    → JComboBox<T>
showInfo(title, message)                   → void      // JOptionPane dialog
```

### `BaseUI` Color Constants
```java
BaseUI.COLOR_PRIMARY      // Sandalwood Brown  #8B4513
BaseUI.COLOR_SECONDARY    // Turmeric Gold     #D4A017
BaseUI.COLOR_ACCENT       // Sindoor Red       #B40000
BaseUI.COLOR_BG           // Cream             #FFF8EB
BaseUI.COLOR_BG_DARK      // Dark Walnut       #2D190A
BaseUI.COLOR_TEXT         // Deep Brown        #281405
BaseUI.COLOR_TEXT_LIGHT   // Cream text        #FFF8EB
BaseUI.COLOR_CARD         // Off-white card    #FFFFF5
BaseUI.COLOR_BORDER       // Warm border       #C8A064
```

---

## Merge Conflict Prevention

This project is architecturally designed to **eliminate merge conflicts**:

| Rule | Why it prevents conflicts |
|------|--------------------------|
| Each feature is in its own folder | No two contributors ever edit the same file |
| `core/` is locked | The shared foundation never changes mid-event |
| `MainApp.java` only gets 1 line added per feature | Registration is additive, not editable |
| Features are completely independent | No inter-feature dependencies |
| No shared mutable state | DataStore is read-only via `getAllItems()` |

**The only files that need coordination are:**
- `MainApp.java` — add one `import` and one `case` line per feature (can be done by event organiser)

---

## License

MIT License — Free to use, fork, and build upon.

---

