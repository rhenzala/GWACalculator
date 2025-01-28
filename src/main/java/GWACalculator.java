import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GWACalculator extends JFrame {
    private JTextField gradeField, unitField;
    private JButton addButton, calculateButton, resetButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel resultLabel;
    private ArrayList<Double> grades = new ArrayList<>();
    private ArrayList<Double> units = new ArrayList<>();

    public GWACalculator() {
        setTitle("GWA Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        inputPanel.add(new JLabel("Unit:"));
        unitField = new JTextField();
        inputPanel.add(unitField);

        addButton = new JButton("Add");
        inputPanel.add(addButton);

        tableModel = new DefaultTableModel(new String[]{"Grade", "Unit"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        calculateButton = new JButton("Calculate");
        resetButton = new JButton("Reset");
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);

        resultLabel = new JLabel("GWA: ", SwingConstants.CENTER);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(resultLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new AddAction());
        calculateButton.addActionListener(new CalculateAction());
        resetButton.addActionListener(new ResetAction());

        setVisible(true);
    }

    private class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double grade = Double.parseDouble(gradeField.getText());
                double weight = Double.parseDouble(unitField.getText());
                if (grade < 1 || grade > 100 || weight <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Grades must be 1-100 and weight > 0.");
                    return;
                }
                grades.add(grade);
                units.add(weight);
                tableModel.addRow(new Object[]{grade, weight});
                gradeField.setText("");
                unitField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
            }
        }
    }

    private class CalculateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (grades.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data to calculate!");
                return;
            }
            double sumGrades = 0, sumWeights = 0;
            for (int i = 0; i < grades.size(); i++) {
                sumGrades += grades.get(i) * units.get(i);
                sumWeights += units.get(i);
            }
            double gwa = sumGrades / sumWeights;
            resultLabel.setText("GWA: " + String.format("%.2f", gwa));
        }
    }

    private class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            grades.clear();
            units.clear();
            tableModel.setRowCount(0);
            resultLabel.setText("GWA: ");
        }
    }

    public static void main(String[] args) {
        new GWACalculator();
    }
}
