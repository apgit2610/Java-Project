package GUI;
import java.awt.Desktop;
import java.io.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author anant
 */

class ViewLecturesForm extends JFrame {
    public ViewLecturesForm() {
        setTitle("View Lectures");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewVideoButton = new JButton("View Videos");
        viewVideoButton.addActionListener(e -> viewVideos());

        JPanel panel = new JPanel();
        panel.add(viewVideoButton);
        add(panel);
    }

    private void viewVideos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtual_classroom", "root", "0000")) {
            String query = "SELECT title, content FROM videos";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            ArrayList<String> videoTitles = new ArrayList<>();
            ArrayList<byte[]> videoData = new ArrayList<>();
            
            while (rs.next()) {
                videoTitles.add(rs.getString("title"));
                videoData.add(rs.getBytes("content"));
            }
            
            if (!videoTitles.isEmpty()) {
                String selectedVideo = (String) JOptionPane.showInputDialog(this, "Select a video:", "View Video",
                        JOptionPane.QUESTION_MESSAGE, null, videoTitles.toArray(), videoTitles.get(0));
                
                if (selectedVideo != null) {
                    int index = videoTitles.indexOf(selectedVideo);
                    byte[] videoBytes = videoData.get(index);
                    playVideo(videoBytes,selectedVideo); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "No videos available.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void playVideo(byte[] videoBytes, String videoTitle) {
        try {
        File tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + videoTitle);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(videoBytes);
        fos.close();

        
        Desktop.getDesktop().open(tempFile);
    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error playing video: " + ex.getMessage());
    }
    }
}