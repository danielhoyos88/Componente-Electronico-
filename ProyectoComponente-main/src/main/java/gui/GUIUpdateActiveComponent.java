package gui;

import javax.swing.*;
import model.ActiveComponent;

public class GUIUpdateActiveComponent extends JFrame {

    private final ActiveComponent component;
    private final JTextField txtBrand = new JTextField();
    private final JTextField txtPackage = new JTextField();
    private final JTextField txtVoltage = new JTextField();
    private final JTextField txtCurrent = new JTextField();
    private final JTextField txtGain = new JTextField();

    public GUIUpdateActiveComponent(ActiveComponent component) {
        this.component = component;
        setTitle("Actualizar Componente Activo - ID: " + component.getId());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadData();
    }

    private void initUI() {
        JPanel panel = new JPanel(new java.awt.GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Brand:")); panel.add(txtBrand);
        panel.add(new JLabel("Package:")); panel.add(txtPackage);
        panel.add(new JLabel("Voltage:")); panel.add(txtVoltage);
        panel.add(new JLabel("Current:")); panel.add(txtCurrent);
        panel.add(new JLabel("Gain Factor:")); panel.add(txtGain);

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
        txtGain.setText(String.valueOf(component.getGainFactor()));
    }

    private void save() {
        try {
            component.setBrand(txtBrand.getText().trim());
            component.setPackageType(txtPackage.getText().trim());
            component.setVoltage(Double.parseDouble(txtVoltage.getText().trim()));
            component.setCurrent(Double.parseDouble(txtCurrent.getText().trim()));
            component.setGainFactor(Double.parseDouble(txtGain.getText().trim()));

            JOptionPane.showMessageDialog(this, "Componente activo actualizado correctamente.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Campos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
