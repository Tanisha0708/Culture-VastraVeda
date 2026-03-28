# ❓ Feature 6 — Clothing Quiz

## What This Feature Should Do
An interactive multiple-choice quiz that tests the user's knowledge about Indian traditional clothing using data from `DataStore`.

---

## 📐 Expected UI Layout

```
┌─────────────────────────────────────────────────────────┐
│  ❓  Clothing Quiz                                        │
│  Test your knowledge about Indian textile heritage        │
├─────────────────────────────────────────────────────────┤
│                Question 2 of 5                            │
│  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (progress bar)         │
│                                                           │
│  🥻  Which saree is known for its gold zari              │
│       brocade and is woven in Varanasi?                   │
│                                                           │
│   [ A ] Kasavu Saree                                      │
│   [ B ] Banarasi Saree        ← correct answer           │
│   [ C ] Pochampally Saree                                 │
│   [ D ] Bandhani Saree                                    │
│                                                           │
│              [ Next Question → ]                          │
├─────────────────────────────────────────────────────────┤
│  Score: 1 / 1                                             │
└─────────────────────────────────────────────────────────┘
```

---

## ✅ Minimum Requirements

1. **At least 8 questions** — hardcoded in `Feature6Service`
2. **4 options per question** — one correct, three wrong
3. **Visual feedback** — correct answer turns green, wrong turns red after selection
4. **Score tracker** — show current score at bottom
5. **Progress indicator** — "Question X of Y" label or progress bar
6. **Result screen** — after last question, show final score + a fun message
7. **Restart button** — let user play again

---

## 📝 Questions to Include (hardcode in Feature6Service.java)

```java
// Format: { question, optionA, optionB, optionC, optionD, correctOption("A"/"B"/"C"/"D"), icon }
{
  "Which saree is woven in Varanasi with gold zari?",
  "Kasavu Saree", "Banarasi Saree", "Pochampally Saree", "Bandhani Saree",
  "B", "🥻"
},
{
  "Phulkari embroidery originates from which state?",
  "Rajasthan", "Gujarat", "Punjab", "Assam",
  "C", "🧣"
},
{
  "What fabric is a Pashmina shawl made from?",
  "Sheep Wool", "Cotton", "Silk", "Cashmere Goat Wool",
  "D", "🧣"
},
{
  "Mekhela Chador is the traditional dress of which state?",
  "Manipur", "Assam", "West Bengal", "Odisha",
  "B", "🥻"
},
{
  "Which saree features tie-dye dot patterns?",
  "Kanjivaram", "Kasavu", "Bandhani", "Pochampally",
  "C", "🥻"
},
{
  "What is a Dhoti?",
  "A stitched trouser", "An unstitched draped garment", "A type of shawl", "A blouse",
  "B", "👘"
},
{
  "Kasavu saree is the traditional dress worn during which Kerala festival?",
  "Diwali", "Baisakhi", "Onam", "Pongal",
  "C", "🥻"
},
{
  "What does 'Angrakha' literally mean?",
  "Royal garment", "Body protector", "Flowing robe", "Silk coat",
  "B", "👔"
}
```

---

## 💻 Code Structure Expected

```java
// Feature6Service.java
public class Feature6Service {
    public String[][] getQuestions() { return QUESTIONS; }
    public boolean checkAnswer(int questionIndex, String selectedOption) { ... }
    public String getResultMessage(int score, int total) {
        // e.g. score >= 7: "🏆 Textile Expert!", score >= 4: "👍 Good knowledge!", else: "📚 Keep learning!"
    }
}

// Feature6UI.java
public class Feature6UI extends BaseUI implements Feature {
    private int currentQuestion = 0;
    private int score = 0;
    private void loadQuestion(int index) { ... }
    private void handleAnswer(String selected) { ... }  // show green/red, update score
    private void showResults() { ... }
}
```

---

## 🎨 UI Style Tips
- Answer buttons: neutral color → green (`new Color(56, 142, 60)`) if correct, red (`new Color(198, 40, 40)`) if wrong
- Disable all 4 buttons after an answer is selected
- Use `JProgressBar` or a simple label for progress
- Results screen: big emoji + score + message + "Play Again" button
