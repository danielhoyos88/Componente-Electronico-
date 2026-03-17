package gui;

import javax.swing.*;
import model.PassiveComponent;
import model.UnitMeasurement;

public class GUIUpdatePassiveComponent extends JFrame {

    private final PassiveComponent component;
    private final JTextField txtBrand = new JTextField();
    private final JTextField txtPackage = new JTextField();
    private final JTextField txtVoltage = new JTextField();
    private final JTextField txtCurrent = new JTextField();
    private final JTextField txtTolerance = new JTextField();
    private final JTextField txtMagnitude = new JTextField();
    private final JTextField txtUnit = new JTextField();

    public GUIUpdatePassiveComponent(PassiveComponent component) {
        this.component = component;
        setTitle("Actualizar Componente Pasivo - ID: " + component.getId());
        setSize(400, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadData();
    }

    private void initUI() {
        JPanel panel = new JPanel(new java.awt.GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("Brand:")); panel.add(txtBrand);
        panel.add(new JLabel("Package:")); panel.add(txtPackage);
        panel.add(new JLabel("Voltage:")); panel.add(txtVoltage);
        panel.add(new JLabel("Current:")); panel.add(txtCurrent);
        panel.add(new JLabel("Tolerance (%):")); panel.add(txtTolerance);
        panel.add(new JLabel("Nominal Magnitude:")); panel.add(txtMagnitude);
        panel.add(new JLabel("Unit:")); panel.add(txtUnit);

        JButton btnSave = new JButton("Guardar");
        btnSave.addActionListener(e -> save());
        panel.add(new JLabel());
        panel.add(btnSave);

        setContentPane(panel);
    }

    private void loadData() {
        txtBrand.setText(component.getBrand());
        txtPackage.setText(component.getPackageType());
        txtVoltage.setText(String.valueOf(component.getVoltage()));
        txtCurrent.setText(String.valueOf(component.getCurrent()));
        txtTolerance.setText(String.valueOf(component.getTolerance()));
        txtMagnitude.setText(String.valueOf(component.getNominalValue().getMagnitude()));
        txtUnit.setText(component.getNominalValue().getUnit());
    }

    private void save() {
        try {
            component.setBrand(txtBrand.getText().trim());
            component.setPackageType(txtPackage.getText().trim());
            component.setVoltage(Double.parseDouble(txtVoltage.getText().trim()));
            component.setCurrent(Double.parseDouble(txtCurrent.getText().trim()));
            component.setTolerance(Double.parseDouble(txtTolerance.getText().trim()));
            component.getNominalValue().setMagnitude(Double.parseDouble(txtMagnitude.getText().trim()));

            JOptionPane.showMessageDialog(this, "Componente pasivo actualizado correctamente.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Campos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
