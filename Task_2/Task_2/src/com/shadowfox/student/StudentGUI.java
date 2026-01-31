package com.shadowfox.student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
    // Model
    static class Student {
        int id;
        String name;
        int marks;
        double gpa;

        Student(int id, String name, int marks, double gpa) {
            this.id = id;
            this.name = name;
            this.marks = marks;
            this.gpa = gpa;
        }
    }

    // Data
    ArrayList<Student> students = new ArrayList<>();

    // UI Components
    JTextField idField, nameField, marksField;
    JTable table;
    DefaultTableModel tableModel;

    public StudentGUI() {

        setTitle("Student Information System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- TOP PANEL (INPUTS) ----------
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        inputPanel.add(marksField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        inputPanel.add(addBtn);
        inputPanel.add(updateBtn);
        inputPanel.add(deleteBtn);

        add(inputPanel, BorderLayout.NORTH);

        // ---------- TABLE ----------
        String[] columns = {"ID", "Name", "Marks", "GPA"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- ADD BUTTON ----------
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int marks = Integer.parseInt(marksField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty");
                    return;
                }

                double gpa = marks / 20.0;

                Student s = new Student(id, name, marks, gpa);
                students.add(s);

                tableModel.addRow(new Object[]{id, name, marks, gpa});

                clearFields();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID and Marks must be numbers");
            }
        });

        // ---------- UPDATE BUTTON ----------
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a row to update");
                return;
            }

            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int marks = Integer.parseInt(marksField.getText());
                double gpa = marks / 20.0;

                tableModel.setValueAt(id, row, 0);
                tableModel.setValueAt(name, row, 1);
                tableModel.setValueAt(marks, row, 2);
                tableModel.setValueAt(gpa, row, 3);

                Student s = students.get(row);
                s.id = id;
                s.name = name;
                s.marks = marks;
                s.gpa = gpa;

                clearFields();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        // ---------- DELETE BUTTON (CONFIRMATION) ----------
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(row);
                students.remove(row);
            }
        });

        // ---------- TABLE CLICK ----------
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                marksField.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        marksField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGUI().setVisible(true);
        });
    }
}
