package GUI;
import javax.swing.*;
import java.io.*;
import java.sql.*;

class UploadLectureForm extends JFrame {
    public UploadLectureForm() {
        setTitle("Upload Lecture");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton uploadButton = new JButton("Upload Lectures");
        uploadButton.addActionListener(e -> uploadVideo());

        JPanel panel = new JPanel();
        panel.add(uploadButton);
        add(panel);
    }

    private void uploadVideo() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtual_classroom", "root", "root");
                 FileInputStream fis = new FileInputStream(selectedFile)) {
                String query = "INSERT INTO videos (title, content) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, selectedFile.getName());
                stmt.setBinaryStream(2, fis, (int) selectedFile.length());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Video uploaded successfully!");
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error uploading video: " + ex.getMessage());
            }
        }
    }
}
