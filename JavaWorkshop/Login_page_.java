import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login_page_ extends JFrame implements ActionListener {
    private JTextField rollNumberField;
    private JButton searchButton;
    private JButton resetButton;
    private JTextArea detailsArea;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Login_page_() {
        setTitle("Student Information System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title label
        JLabel titleLabel = new JLabel("Student Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(150, 20, 200, 40);
        mainPanel.add(titleLabel);

        // Roll Number components
        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rollLabel.setBounds(50, 80, 100, 25);
        mainPanel.add(rollLabel);

        rollNumberField = new JTextField();
        rollNumberField.setBounds(150, 80, 200, 25);
        mainPanel.add(rollNumberField);

        // Buttons
        searchButton = new JButton("Search");
        searchButton.setBounds(360, 80, 90, 25);
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(this);
        mainPanel.add(searchButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(360, 115, 90, 25);
        resetButton.setBackground(new Color(180, 180, 180));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(this);
        mainPanel.add(resetButton);

        // Details Area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(50, 150, 400, 180);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String rollNumber = rollNumberField.getText().trim();
            if (!rollNumber.isEmpty()) {
                fetchStudentDetails(rollNumber);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a roll number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            rollNumberField.setText("");
            detailsArea.setText("");
        }
    }

    private void fetchStudentDetails(String rollNumber) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM user WHERE Roll_num = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, rollNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        StringBuilder details = new StringBuilder();
                        details.append("Student Details:\n\n");
                        details.append("ID: ").append(rs.getInt("ID")).append("\n");
                        details.append("Name: ").append(rs.getString("Name")).append("\n");
                        details.append("Roll Number: ").append(rs.getString("Roll_num")).append("\n");
                        details.append("Marks: ").append(rs.getDouble("Mark")).append("\n");
                        
                        detailsArea.setText(details.toString());
                    } else {
                        detailsArea.setText("No student found with Roll Number: " + rollNumber);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login_page_().setVisible(true);
        });
    }
}
