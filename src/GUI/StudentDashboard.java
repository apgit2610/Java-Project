package GUI;

import java.awt.*;
import javax.swing.*;
public class StudentDashboard extends JFrame {
    public StudentDashboard() {
        setTitle("Student Dashboard");
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewLecturesButton = new JButton("View Lectures");
        JButton openWhiteboardButton = new JButton("Open Whiteboard"); 
        JButton takeQuizButton = new JButton("Take Quiz");

        viewLecturesButton.addActionListener(e -> new ViewLecturesForm().setVisible(true));
        openWhiteboardButton.addActionListener(e -> new WhiteboardClient().setVisible(true));
        takeQuizButton.addActionListener(e -> new StudentQuizForm().setVisible(true));

        JPanel panel = new JPanel();
        panel.add(viewLecturesButton);
        panel.add(openWhiteboardButton);
        panel.add(takeQuizButton);
        add(panel);
    }
}
