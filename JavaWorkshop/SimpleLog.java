import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleLog extends JFrame {
    public SimpleLog() {
        setTitle("Login Page");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel with simple layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));

        // Username
        panel.add(new JLabel("Username:"));
        JTextField userField = new JTextField();
        panel.add(userField);

        // Password
        panel.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField();
        panel.add(passField);

        // Message label
        JLabel message = new JLabel("", SwingConstants.CENTER);
        panel.add(message);

        // Login button
        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        add(panel);

        // ===== Explicit ActionListener =====
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if ("admin".equals(username) && "password".equals(password)) {
                    message.setText("✅ Login successful!");
                    message.setForeground(Color.GREEN);
                } else {
                    message.setText("❌ Invalid credentials!");
                    message.setForeground(Color.RED);
                }
            }
        });
        // ==================================

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleLog::new);
    }
}