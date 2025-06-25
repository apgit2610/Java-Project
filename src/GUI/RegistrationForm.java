package GUI;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
/**
 *
 * @author anant
 */

class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton registerButton;

    public RegistrationForm() {
        setTitle("Register User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 2));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role:");
        roleBox = new JComboBox<>(new String[]{"teacher", "student"});
        registerButton = new JButton("Register");
        
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(new JLabel());
        panel.add(registerButton);
        
        add(panel);
        
        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtual_classroom", "root", "0000");
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User registered successfully!");
            conn.close();
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}  