package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import model.UnitMeasurement;

public class GUIManageUnitMeasurement extends JFrame {

    private final List<UnitMeasurement> units = new ArrayList<>();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtMagnitude = new JTextField();
    private final JTextField txtUnit = new JTextField();

    public GUIManageUnitMeasurement() {
        setTitle("Gestionar Unidades de Medida");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"#", "Magnitud", "Unidad"}, 0);
        table = new JTable(tableModel);

        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new java.awt.GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("Magnitud:")); formPanel.add(txtMagnitude);
        formPanel.add(new JLabel("Unidad:")); formPanel.add(txtUnit);

        JButton btnAdd = new JButton("Agregar");
        JButton btnUpdate = new JButton("Actualizar");
        JButton btnDelete = new JButton("Eliminar");

        btnAdd.addActionListener(e -> addUnit());
        btnUpdate.addActionListener(e -> updateUnit());
        btnDelete.addActionListener(e -> deleteUnit());

        formPanel.add(btnAdd); formPanel.add(btnUpdate);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnDelete);

        JScrollPane scroll = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(e -> loadSelected());

        setLayout(new java.awt.BorderLayout(5, 5));
        add(formPanel, java.awt.BorderLayout.NORTH);
        add(scroll, java.awt.BorderLayout.CENTER);
        add(btnPanel, java.awt.BorderLayout.SOUTH);
    }

    private void addUnit() {
        try {
            double mag = Double.parseDouble(txtMagnitude.getText().trim());
            String unit = txtUnit.getText().trim();
            if (unit.isEmpty()) throw new IllegalArgumentException("La unidad no puede estar vacía");
            UnitMeasurement um = new UnitMeasurement(mag, unit);
            units.add(um);
            tableModel.addRow(new Object[]{units.size(), mag, unit});
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Magnitud inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUnit() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione una unidad."); return; }
        try {
            double mag = Double.parseDouble(txtMagnitude.getText().trim());
            String unit = txtUnit.getText().trim();
            if (unit.isEmpty()) throw new IllegalArgumentException("La unidad no puede estar vacía");
            units.get(row).setMagnitude(mag);
            tableModel.setValueAt(mag, row, 1);
            tableModel.setValueAt(unit, row, 2);
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Magnitud inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUnit() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione una unidad."); return; }
        units.remove(row);
        tableModel.removeRow(row);
        clearFields();
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        txtMagnitude.setText(tableModel.getValueAt(row, 1).toString());
        txtUnit.setText(tableModel.getValueAt(row, 2).toString());
    }

    private void clearFields() {
        txtMagnitude.setText("");
        txtUnit.setText("");
    }
}
