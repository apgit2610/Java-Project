package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateQuizForm extends JFrame {
    private JTextField questionField, optionAField, optionBField, optionCField, optionDField, correctField;
    private JButton nextButton;

    private ArrayList<String> questions = new ArrayList<>();
    private int questionCount = 0;
    private final int MAX_QUESTIONS = 2;

    public CreateQuizForm() {
        setTitle("Create Quiz (Max " + MAX_QUESTIONS + " Questions)");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        questionField = new JTextField();
        optionAField = new JTextField();
        optionBField = new JTextField();
        optionCField = new JTextField();
        optionDField = new JTextField();
        correctField = new JTextField();

        add(new JLabel("Question:"));
        add(questionField);
        add(new JLabel("Option A:"));
        add(optionAField);
        add(new JLabel("Option B:"));
        add(optionBField);
        add(new JLabel("Option C:"));
        add(optionCField);
        add(new JLabel("Option D:"));
        add(optionDField);
        add(new JLabel("Correct Option (A/B/C/D):"));
        add(correctField);

        nextButton = new JButton("Add Question");
        add(nextButton);

        nextButton.addActionListener(e -> handleNext());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true); // show the form
    }

    private void handleNext() {
        String question = questionField.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();
        String correct = correctField.getText().trim().toUpperCase();

        if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || correct.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!correct.matches("[ABCD]")) {
            JOptionPane.showMessageDialog(this, "Correct answer must be A, B, C, or D.");
            return;
        }

        // Store question in memory
        String formatted = "Q" + (questionCount + 1) + ": " + question + "\n"
                + "A. " + optionA + "\n"
                + "B. " + optionB + "\n"
                + "C. " + optionC + "\n"
                + "D. " + optionD + "\n"
                + "Correct:" + correct + "\n";
        questions.add(formatted);

        questionCount++;

        if (questionCount == MAX_QUESTIONS) {
            askAndSaveQuiz(); // Ask for time limit after all questions
            return;
        }

        // Clear fields for next question
        questionField.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        correctField.setText("");

        JOptionPane.showMessageDialog(this, "Question " + questionCount + " added. Add next.");
    }

    private void askAndSaveQuiz() {
        try {
            // Spinner for time limit
            SpinnerNumberModel model = new SpinnerNumberModel(60, 10, 300, 10); // default 60s
            JSpinner spinner = new JSpinner(model);
            int option = JOptionPane.showConfirmDialog(this, spinner, "Set Quiz Time Limit (seconds)", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                int timeLimit = (int) spinner.getValue();

                try (FileWriter writer = new FileWriter("quiz.txt")) {
                    for (String q : questions) {
                        writer.write(q + "\n");
                    }
                    writer.write("TimeLimit:" + timeLimit + "\n");
                }

                JOptionPane.showMessageDialog(this, "Quiz saved with time limit: " + timeLimit + " seconds");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Quiz not saved. Time limit was not set.");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving quiz: " + ex.getMessage());
        }
    }
}
