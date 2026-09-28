import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator;
    private double num1, num2, result;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Modify text field appearance
        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setPreferredSize(new Dimension(300, 60));
        textField.setBackground(new Color(230, 230, 230));
        add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5)); // Added gaps between buttons
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            // Style the buttons
            button.setFont(new Font("Arial", Font.BOLD, 18));
            if ("/*-+=".contains(text)) {
                button.setBackground(new Color(255, 158, 47));
                button.setForeground(Color.WHITE);
            } else if ("C".equals(text)) {
                button.setBackground(new Color(255, 59, 48));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(241, 241, 241));
            }
            button.setFocusPainted(false);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        
        // Set minimum size for the calculator
        setMinimumSize(new Dimension(300, 400));
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789".contains(cmd)) {
            textField.setText(textField.getText() + cmd);
        } else if ("/*-+".contains(cmd)) {
            num1 = Double.parseDouble(textField.getText());
            operator = cmd;
            textField.setText("");
        } else if ("=".equals(cmd)) {
            num2 = Double.parseDouble(textField.getText());
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": result = num1 / num2; break;
            }
            textField.setText(String.valueOf(result));
        } else if ("C".equals(cmd)) {
            textField.setText("");
            num1 = num2 = result = 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}
