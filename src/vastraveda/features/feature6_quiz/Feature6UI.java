package vastraveda.features.feature6_quiz;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature6UI extends BaseUI implements Feature {

    private Feature6Service service = new Feature6Service();
    private List<Feature6Service.Question> questions;

    private int index = 0;
    private int score = 0;
    private boolean answered = false;

    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JButton nextButton;

    public Feature6UI() {
        super("Clothing Quiz");
        questions = service.getQuestions();
        buildUI();
        loadQuestion();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🧠 Clothing Quiz", "Test your knowledge"), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(COLOR_BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBackground(COLOR_BG);

        optionButtons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFocusPainted(false);
            int finalI = i;

            optionButtons[i].addActionListener(e -> checkAnswer(optionButtons[finalI]));

            optionsPanel.add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> nextQuestion());

        main.add(questionLabel, BorderLayout.NORTH);
        main.add(optionsPanel, BorderLayout.CENTER);
        main.add(nextButton, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);
    }

    private void loadQuestion() {
        if (index >= questions.size()) {
            showResult();
            return;
        }

        Feature6Service.Question q = questions.get(index);

        questionLabel.setText("Q" + (index + 1) + ": " + q.question);

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.options.get(i));
            optionButtons[i].setBackground(null);
            optionButtons[i].setEnabled(true);
        }

        answered = false;
        nextButton.setEnabled(false);
    }

    private void checkAnswer(JButton selected) {
        if (answered) return;

        answered = true;
        Feature6Service.Question q = questions.get(index);

        for (JButton btn : optionButtons) {
            btn.setEnabled(false);

            if (btn.getText().equals(q.answer)) {
                btn.setBackground(Color.GREEN);
            } else if (btn == selected) {
                btn.setBackground(Color.RED);
            }
        }

        if (selected.getText().equals(q.answer)) {
            score++;
        }

        nextButton.setEnabled(true);
    }

    private void nextQuestion() {
        index++;
        loadQuestion();
    }

    private void showResult() {
        int choice = JOptionPane.showOptionDialog(
                this,
                "Your Score: " + score + "/" + questions.size(),
                "Quiz Completed",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Close"},
                "Restart"
        );

        if (choice == 0) {
            index = 0;
            score = 0;
            loadQuestion();
        } else {
            dispose();
        }
    }
}