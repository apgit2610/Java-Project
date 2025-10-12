package GUI;
import javax.swing.*;
import server.WhiteboardServer;

public class TeacherDashboard extends JFrame {
    public TeacherDashboard() {
        setTitle("Teacher Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton uploadVideoButton = new JButton("Upload Video");
        JButton whiteboardButton = new JButton("Open Whiteboard");
        JButton createQuizButton = new JButton("Create Quiz");
        JButton viewResultsButton = new JButton("View Results");

        uploadVideoButton.addActionListener(e -> new UploadLectureForm().setVisible(true));
        whiteboardButton.addActionListener(e -> new WhiteboardServer().setVisible(true));
        createQuizButton.addActionListener(e -> new CreateQuizForm().setVisible(true));
        viewResultsButton.addActionListener(e -> new ViewResultsForm().setVisible(true));
        
        
        JPanel panel = new JPanel();
        panel.add(uploadVideoButton);
        panel.add(whiteboardButton);
        panel.add(createQuizButton);
        panel.add(viewResultsButton);
        add(panel);
    }
}

