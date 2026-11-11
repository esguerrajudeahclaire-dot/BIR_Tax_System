#BIR Tax Calculator - Updated
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

class TaxCalculator {
    private double salary;
    private double deductions;

    public TaxCalculator(double salary, double deductions) {
        this.salary = salary;
        this.deductions = deductions;
    }

    public double calculateTaxableIncome() {
        return Math.max(0, salary - deductions);
    }

    public double calculateAnnualTax() {
        double income = calculateTaxableIncome();

        if (income <= 250000) {
            return 0;
        } else if (income <= 400000) {
            return (income - 250000) * 0.15;
        } else if (income <= 800000) {
            return 22500 + (income - 400000) * 0.20;
        } else if (income <= 2000000) {
            return 102500 + (income - 800000) * 0.25;
        } else if (income <= 8000000) {
            return 402500 + (income - 2000000) * 0.30;
        } else {
            return 2202500 + (income - 8000000) * 0.35;
        }
    }
}

public class BIRTaxCalculator extends JFrame {
    private JTextField nameField;
    private JTextField salaryField;
    private JTextField deductionsField;
    private JTextArea resultArea;
    private final DecimalFormat money = new DecimalFormat("#,##0.00");

    public BIRTaxCalculator() {
        setTitle("BIR Income Tax Calculator - Java OOP");
        setSize(550, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(239, 246, 240));

        JLabel title = new JLabel("BIR INCOME TAX CALCULATOR", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(40, 85, 55));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 8, 5));
        formPanel.setBackground(new Color(239, 246, 240));

        formPanel.add(new JLabel("Government Employee Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Annual Gross Salary (PHP):"));
        salaryField = new JTextField();
        formPanel.add(salaryField);

        formPanel.add(new JLabel("Annual Allowable Deductions (PHP):"));
        deductionsField = new JTextField();
        formPanel.add(deductionsField);

        JButton calculateButton = new JButton("Calculate Tax");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(239, 246, 240));
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        resultArea = new JTextArea(8, 35);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.setBackground(new Color(239, 246, 240));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);

        calculateButton.addActionListener(e -> calculateTax());
        clearButton.addActionListener(e -> clearFields());
    }

    private void calculateTax() {
        try {
            String name = nameField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            double deductions = Double.parseDouble(
                deductionsField.getText().trim()
            );

            if (name.isEmpty() || salary < 0 || deductions < 0
                    || deductions > salary) {
                JOptionPane.showMessageDialog(
                    this,
                    "Enter a name and valid amounts. Deductions cannot exceed salary.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            TaxCalculator calculator = new TaxCalculator(salary, deductions);
            double taxableIncome = calculator.calculateTaxableIncome();
            double annualTax = calculator.calculateAnnualTax();

            resultArea.setText(
                "BIR INCOME TAX SUMMARY\n" +
                "-----------------------------------\n" +
                "Employee: " + name + "\n" +
                "Gross Annual Salary: PHP " + money.format(salary) + "\n" +
                "Allowable Deductions: PHP " + money.format(deductions) + "\n" +
                "Taxable Income: PHP " + money.format(taxableIncome) + "\n" +
                "Annual Income Tax: PHP " + money.format(annualTax) + "\n" +
                "Monthly Tax Estimate: PHP " +
                    money.format(annualTax / 12) + "\n"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter valid numbers for salary and deductions.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        nameField.setText("");
        salaryField.setText("");
        deductionsField.setText("");
        resultArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BIRTaxCalculator().setVisible(true);
        });
    }
}
