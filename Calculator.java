import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {

    private JFrame frame;
    private JTextField textField;

    private double num1, num2, result;
    private String operator;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        panel.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
                "C", " ", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                " ", "0", ".", "="
        };

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.addActionListener(new ButtonClickListener());
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String command = source.getText();

            switch (command) {
                case "=":
                    if (!textField.getText().isEmpty() && operator != null) {
                        num2 = Double
                                .parseDouble(textField.getText().substring(textField.getText().indexOf(operator) + 1));
                        calculate();
                        operator = null; // Reset operator after calculation
                    }
                    break;
                case ".":
                    if (!textField.getText().contains(".")) {
                        textField.setText(textField.getText() + command);
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    if (!textField.getText().isEmpty()) {
                        // Set num1 and operator when an operator button is clicked
                        num1 = Double.parseDouble(textField.getText());
                        operator = command;
                        textField.setText(textField.getText() + operator);
                    }
                    break;
                case "%":
                    if (!textField.getText().isEmpty()) {
                        // Calculate percentage and update the text field
                        double currentValue = Double.parseDouble(textField.getText());
                        double percentage = currentValue / 100.0;
                        textField.setText(String.valueOf(percentage));
                    }
                    break;
                case "C":
                    // Clear the entire text field
                    textField.setText("");
                    num1 = num2 = result = 0;
                    operator = null;
                    break;
                default:
                    textField.setText(textField.getText() + command);
                    break;
            }
        }

        private void calculate() {
            if (operator != null) {
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            // Handle division by zero error
                            textField.setText("Error");
                            return;
                        }
                        break;
                }
                textField.setText(num1 + operator + num2 + "=" + String.valueOf(result));
                num1 = result; // Store the result for further calculations
                num2 = 0; // Reset num2 for the next calculation
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
