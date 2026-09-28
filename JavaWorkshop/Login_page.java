import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login_page extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;

    public Login_page() {
        setTitle("Login System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title label
        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(140, 20, 150, 40);
        mainPanel.add(titleLabel);

        // Username components
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(50, 80, 80, 25);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 80, 200, 25);
        mainPanel.add(usernameField);

        // Password components
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(50, 120, 80, 25);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 120, 200, 25);
        mainPanel.add(passwordField);

        // Buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(130, 170, 90, 30);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        mainPanel.add(loginButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(240, 170, 90, 30);
        resetButton.setBackground(new Color(180, 180, 180));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(this);
        mainPanel.add(resetButton);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Changed validation to check for "admin" as both username and password
            if (username.equals("admin") && password.equals("admin")) {
                // Create a success dialog with custom styling
                JDialog successDialog = new JDialog(this, "Success", true);
                successDialog.setSize(300, 150);
                successDialog.setLocationRelativeTo(this);

                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel.setBackground(new Color(240, 255, 240));

                JLabel messageLabel = new JLabel("Login Successful!", SwingConstants.CENTER);
                messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
                messageLabel.setForeground(new Color(0, 128, 0));

                JButton okButton = new JButton("OK");
                okButton.setBackground(new Color(70, 130, 180));
                okButton.setForeground(Color.WHITE);
                okButton.addActionListener(evt -> successDialog.dispose());

                panel.add(messageLabel, BorderLayout.CENTER);
                panel.add(okButton, BorderLayout.SOUTH);

                successDialog.add(panel);
                successDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login_page().setVisible(true);
        });
    }
}
