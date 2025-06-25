package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ViewResultsForm extends JFrame {
    private JTextArea resultsArea;

    public ViewResultsForm() {
        setTitle("Quiz Results");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        add(scrollPane, BorderLayout.CENTER);

        loadResults();
    }

    private void loadResults() {
        try (BufferedReader br = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultsArea.append(line + "\n");
            }
        } catch (IOException e) {
            resultsArea.setText("No results found.");
        }
    }
}

