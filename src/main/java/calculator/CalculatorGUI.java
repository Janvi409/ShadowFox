package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;


    JTextField tf;
    double num1, num2;
    String op = "";

    CalculatorService service = new CalculatorService();

    String[] buttons = {
        "7","8","9","÷",
        "4","5","6","×",
        "1","2","3","−",
        "0",".","=","+",
        "√","xʸ","C","⌫",
        "°C→°F","°F→°C","INR→USD","USD→INR"
    };

    public CalculatorGUI() {
        setTitle("ShadowFox Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tf = new JTextField();
        tf.setFont(new Font("Arial", Font.BOLD, 22));
        tf.setHorizontalAlignment(JTextField.RIGHT);
        add(tf, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));

        for (String b : buttons) {
            JButton btn = new JButton(b);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            tf.setText(tf.getText() + cmd);
        }

        else if ("+−×÷".contains(cmd)) {
            num1 = Double.parseDouble(tf.getText());
            op = cmd;
            tf.setText("");
        }

        else if (cmd.equals("=")) {
            num2 = Double.parseDouble(tf.getText());
            double result = 0;

            switch (op) {
                case "+": result = service.add(num1, num2); break;
                case "−": result = service.sub(num1, num2); break;
                case "×": result = service.mul(num1, num2); break;
                case "÷": result = service.div(num1, num2); break;
            }
            tf.setText(String.valueOf(result));
        }

        else if (cmd.equals("√")) {
            double n = Double.parseDouble(tf.getText());
            tf.setText(String.valueOf(service.squareRoot(n)));
        }

        else if (cmd.equals("xʸ")) {
            num1 = Double.parseDouble(tf.getText());
            op = "pow";
            tf.setText("");
        }

        else if (cmd.equals("C")) {
            tf.setText("");
        }

        else if (cmd.equals("⌫")) {
            String s = tf.getText();
            if (!s.isEmpty())
                tf.setText(s.substring(0, s.length() - 1));
        }

        else if (cmd.equals("°C→°F")) {
            double c = Double.parseDouble(tf.getText());
            tf.setText(String.valueOf(service.celsiusToFahrenheit(c)));
        }

        else if (cmd.equals("°F→°C")) {
            double f = Double.parseDouble(tf.getText());
            tf.setText(String.valueOf(service.fahrenheitToCelsius(f)));
        }

        else if (cmd.equals("INR→USD")) {
            double inr = Double.parseDouble(tf.getText());
            tf.setText(String.format("%.2f", service.inrToUsd(inr)));
        }

        else if (cmd.equals("USD→INR")) {
            double usd = Double.parseDouble(tf.getText());
            tf.setText(String.format("%.2f", service.usdToInr(usd)));
        }
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }
}
