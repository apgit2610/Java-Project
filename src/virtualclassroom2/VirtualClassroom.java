package virtualclassroom2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import GUI.*;
/**
 *
 * 
 * @author anant
 */

public class VirtualClassroom extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public VirtualClassroom() {
        

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        setTitle("Virtual Classroom Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtual_classroom", "root", "0000");
            String query = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login successful as " + role);
                openDashboard(role);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void openDashboard(String role) {
        if (role.equals("admin")) {
            new AdminDashboard().setVisible(true);
        } else if (role.equals("teacher")) {
            new TeacherDashboard().setVisible(true);
        } else if (role.equals("student")) {
            
           try{
               new StudentDashboard().setVisible(true);
           }
           catch(Exception e){
               System.out.println("Hello");
           }
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VirtualClassroom().setVisible(true));
    }
}