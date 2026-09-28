import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class register extends JFrame implements ActionListener {
    private JTextField nameField, rollField, marksField;
    private JButton registerButton, resetButton;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public register() {
        setTitle("Student Registration");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Register Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(110, 20, 200, 30);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(50, 70, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 70, 200, 25);
        panel.add(nameField);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rollLabel.setBounds(50, 110, 80, 25);
        panel.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(140, 110, 200, 25);
        panel.add(rollField);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        marksLabel.setBounds(50, 150, 80, 25);
        panel.add(marksLabel);

        marksField = new JTextField();
        marksField.setBounds(140, 150, 200, 25);
        panel.add(marksField);

        registerButton = new JButton("Register");
        registerButton.setBounds(140, 200, 90, 30);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(250, 200, 90, 30);
        resetButton.setBackground(new Color(180, 180, 180));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(this);
        panel.add(resetButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String marksText = marksField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || marksText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double marks;
            try {
                marks = Double.parseDouble(marksText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = getConnection()) {
                String query = "INSERT INTO user (Name, Roll_num, Mark) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, roll);
                    pstmt.setDouble(3, marks);

                    int result = pstmt.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        nameField.setText("");
                        rollField.setText("");
                        marksField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(this, "Roll number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            nameField.setText("");
            rollField.setText("");
            marksField.setText("");
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new register().setVisible(true);
        });
    }
}
