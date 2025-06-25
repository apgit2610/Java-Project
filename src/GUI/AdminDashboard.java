package GUI;
import java.awt.*;
import javax.swing.*;
import virtualclassroom2.VirtualClassroom;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Label
        JLabel headerLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10 , 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 20)); // 1x2 Grid
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 50, 10));

        // Register Button
        JButton registerButton = createStyledButton("Register User", "📝");
        registerButton.setBackground(new Color(59, 89, 182));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> new RegistrationForm().setVisible(true));

        // Logout Button
        JButton logoutButton = createStyledButton("Logout", "🚪");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            dispose(); // Close Admin Dashboard
            new VirtualClassroom().setVisible(true); // Open Login Page
        });

        // Adding buttons to panel
        buttonPanel.add(registerButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Helper method to create a styled button with an emoji
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
