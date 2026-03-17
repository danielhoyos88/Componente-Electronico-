package gui;

import model.ActiveComponent;
import com.mycompany.electroniccomponentsproject.service.ElectronicComponentService;

import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.List;

public class GUIAddActiveComponent extends javax.swing.JFrame {

    public GUIAddActiveComponent() {
        initComponents();
        setTitle("Añadir Componente Activo");
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblId = new javax.swing.JLabel("ID:");
        javax.swing.JLabel lblBrand = new javax.swing.JLabel("Brand:");
        javax.swing.JLabel lblPackage = new javax.swing.JLabel("Package Type:");
        javax.swing.JLabel lblVoltage = new javax.swing.JLabel("Voltage:");
        javax.swing.JLabel lblCurrent = new javax.swing.JLabel("Current:");
        javax.swing.JLabel lblGain = new javax.swing.JLabel("Gain Factor:");
        javax.swing.JLabel lblPins = new javax.swing.JLabel("Pin Names (separados por coma):");

        txtId = new javax.swing.JTextField();
        txtBrand = new javax.swing.JTextField();
        txtPackage = new javax.swing.JTextField();
        txtVoltage = new javax.swing.JTextField();
        txtCurrent = new javax.swing.JTextField();
        txtGain = new javax.swing.JTextField();
        txtPins = new javax.swing.JTextField();

        btnSave = new javax.swing.JButton("Guardar");

        btnSave.addActionListener(evt -> btnSaveActionPerformed());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblId)
                        .addComponent(lblBrand)
                        .addComponent(lblPackage)
                        .addComponent(lblVoltage)
                        .addComponent(lblCurrent)
                        .addComponent(lblGain)
                        .addComponent(lblPins))
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtId)
                        .addComponent(txtBrand)
                        .addComponent(txtPackage)
                        .addComponent(txtVoltage)
                        .addComponent(txtCurrent)
                        .addComponent(txtGain)
                        .addComponent(txtPins)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                    .addContainerGap(30, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblId)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBrand)
                        .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPackage)
                        .addComponent(txtPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblVoltage)
                        .addComponent(txtVoltage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCurrent)
                        .addComponent(txtCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblGain)
                        .addComponent(txtGain, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPins)
                        .addComponent(txtPins, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnSaveActionPerformed() {

        try {

            int id = Integer.parseInt(txtId.getText().trim());
            String brand = txtBrand.getText().trim();
            String packageType = txtPackage.getText().trim();
            double voltage = Double.parseDouble(txtVoltage.getText().trim());
            double current = Double.parseDouble(txtCurrent.getText().trim());
            double gain = Double.parseDouble(txtGain.getText().trim());
            String pinsText = txtPins.getText().trim();

            if (brand.isEmpty() || packageType.isEmpty() || pinsText.isEmpty()) {
                throw new IllegalArgumentException("No puede haber campos vacíos");
            }

            if (voltage <= 0 || current <= 0) {
                throw new IllegalArgumentException("Voltaje y corriente deben ser positivos");
            }

            if (gain <= 0) {
                throw new IllegalArgumentException("La ganancia debe ser positiva");
            }

            List<String> pinList = Arrays.asList(pinsText.split(","));

            ActiveComponent component = new ActiveComponent(
                    id,
                    brand,
                    packageType,
                    voltage,
                    current,
                    gain,
                    pinList
            );

            ElectronicComponentService.getInstance().add(component);

            JOptionPane.showMessageDialog(this, "Componente activo guardado correctamente");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Campos numéricos inválidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtBrand;
    private javax.swing.JTextField txtPackage;
    private javax.swing.JTextField txtVoltage;
    private javax.swing.JTextField txtCurrent;
    private javax.swing.JTextField txtGain;
    private javax.swing.JTextField txtPins;
    private javax.swing.JButton btnSave;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GUIAddActiveComponent().setVisible(true));
    }
}