package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StudentQuizForm extends JFrame {
    private JLabel questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private JLabel timerLabel;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String selectedAnswer = "";
    private int timeRemaining; // in seconds
    private Timer quizTimer;
    private String studentName;


    public StudentQuizForm() {
        setTitle("Student Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        String studentName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (studentName == null || studentName.trim().isEmpty()) {
            studentName = "Anonymous";
        }
        this.studentName = studentName;


        // Load questions
        if (!loadQuizFromFile("quiz.txt")) {
            JOptionPane.showMessageDialog(this, "Failed to load quiz.");
            return;
        }

        // Top - Timer
        timerLabel = new JLabel("Time Left: " + timeRemaining + " sec", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.NORTH);

        // Center - Question and Options
        JPanel questionPanel = new JPanel(new GridLayout(5, 1));
        questionLabel = new JLabel("", SwingConstants.LEFT);

        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        questionPanel.add(questionLabel);
        questionPanel.add(optionA);
        questionPanel.add(optionB);
        questionPanel.add(optionC);
        questionPanel.add(optionD);

        add(questionPanel, BorderLayout.CENTER);

        // Bottom - Next button
        nextButton = new JButton("Next");
        add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> handleNext());

        // Start timer
        startTimer();

        // Show first question
        showQuestion();

        setVisible(true);
    }

    private void showQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            submitQuiz();
            return;
        }

        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + q.text);
        optionA.setText("A. " + q.optionA);
        optionB.setText("B. " + q.optionB);
        optionC.setText("C. " + q.optionC);
        optionD.setText("D. " + q.optionD);
        optionsGroup.clearSelection();
    }

    private void handleNext() {
        String selected = getSelectedOption();
        if (selected != null) {
            Question current = questions.get(currentQuestionIndex);
            if (selected.equals(current.correctAnswer)) {
                score++;
            }
        }
        currentQuestionIndex++;
        showQuestion();
    }

    private String getSelectedOption() {
        if (optionA.isSelected()) return "A";
        if (optionB.isSelected()) return "B";
        if (optionC.isSelected()) return "C";
        if (optionD.isSelected()) return "D";
        return null;
    }

    private void startTimer() {
        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeRemaining--;
                    timerLabel.setText("Time Left: " + timeRemaining + " sec");
                    if (timeRemaining <= 0) {
                        quizTimer.cancel();
                        submitQuiz();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void submitQuiz() {
        if (quizTimer != null) {
            quizTimer.cancel();
        }

        // Calculate percentage score
        double percentage = (double) score / questions.size() * 100;
        String scoreMessage = String.format(
            "Quiz submitted!\n\n" +
            "Student Name: %s\n" +
            "Score: %d/%d\n" +
            "Percentage: %.2f%%",
            studentName, score, questions.size(), percentage
        );

        // Save results first
        saveResultToFile(studentName, score, questions.size());

        // Show score in a more prominent way
        JOptionPane.showMessageDialog(
            this, 
            scoreMessage, 
            "Quiz Results", 
            JOptionPane.INFORMATION_MESSAGE
        );

        // Only dispose after the message is shown
        this.dispose();
    }

    private void saveResultToFile(String name, int score, int total) {
        try (FileWriter writer = new FileWriter("results.txt", true)) {
            writer.write(name + " - " + score + "/" + total + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean loadQuizFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Question q = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TimeLimit:")) {
                    timeRemaining = Integer.parseInt(line.substring(10).trim()); // set timer once here
                } else if (line.startsWith("Q")) {
                    q = new Question();
                    q.text = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("A.")) {
                    q.optionA = line.substring(2).trim();
                } else if (line.startsWith("B.")) {
                    q.optionB = line.substring(2).trim();
                } else if (line.startsWith("C.")) {
                    q.optionC = line.substring(2).trim();
                } else if (line.startsWith("D.")) {
                    q.optionD = line.substring(2).trim();
                } else if (line.startsWith("Correct:")) {
                    q.correctAnswer = line.substring(8).trim();
                    questions.add(q);
                }
            }
            return !questions.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private int getTimeLimitFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int time = 60; // default
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TimeLimit:")) {
                    time = Integer.parseInt(line.substring(10).trim());
                    break;
                }
            }
            return time;
        } catch (IOException e) {
            return 60;
        }
    }

    // Helper class for storing questions
    private static class Question {
        String text;
        String optionA;
        String optionB;
        String optionC;
        String optionD;
        String correctAnswer;
    }
}
