import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentDetailsApp extends JFrame implements ActionListener {
    
    private JTextField rollField = new JTextField(10);
    private JTextArea resultArea = new JTextArea(5, 30);
    private JButton searchBtn;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root", DB_PASS = "root";

    public StudentDetailsApp() {
        setTitle("Student Details Finder");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        searchBtn = new JButton("Search");
        JPanel top = new JPanel();
        top.add(new JLabel("Enter Roll No:"));
        top.add(rollField);
        top.add(searchBtn);

        resultArea.setEditable(false);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        searchStudent();
    }

    private void searchStudent() {
        String r = rollField.getText().trim();
        if (r.isEmpty()){
            showError("Please enter a roll number");
            return;
        }
        try {
            int roll = Integer.parseInt(r);
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement p = c.prepareStatement("SELECT * FROM students WHERE roll_no=?")) {
                p.setInt(1, roll);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    resultArea.setText("Roll No: " + roll +
                        "\nName: " + rs.getString("name") +
                        "\nAge: " + rs.getInt("age") +
                        "\nDepartment: " + rs.getString("department"));
                } else resultArea.setText("No student found with Roll No: " + roll);
            }
        } catch (NumberFormatException ex) {
            showError("Invalid Roll Number");
        } catch (SQLException ex) {
            showError("DB Error: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDetailsApp().setVisible(true));
    }
}