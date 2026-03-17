package gui;

import javax.swing.JOptionPane;

import com.mycompany.electroniccomponentsproject.service.ElectronicComponentService;

import model.PassiveComponent;
import model.UnitMeasurement;

public class GUIAddPassiveComponent extends javax.swing.JFrame {

    public GUIAddPassiveComponent() {
        initComponents();
        setTitle("Añadir Componente Pasivo");
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblId = new javax.swing.JLabel("ID:");
        javax.swing.JLabel lblBrand = new javax.swing.JLabel("Brand:");
        javax.swing.JLabel lblPackage = new javax.swing.JLabel("Package Type:");
        javax.swing.JLabel lblVoltage = new javax.swing.JLabel("Voltage:");
        javax.swing.JLabel lblCurrent = new javax.swing.JLabel("Current:");
        javax.swing.JLabel lblTolerance = new javax.swing.JLabel("Tolerance (%):");
        javax.swing.JLabel lblMagnitude = new javax.swing.JLabel("Nominal Magnitude:");
        javax.swing.JLabel lblUnit = new javax.swing.JLabel("Unit:");

        txtId = new javax.swing.JTextField();
        txtBrand = new javax.swing.JTextField();
        txtPackage = new javax.swing.JTextField();
        txtVoltage = new javax.swing.JTextField();
        txtCurrent = new javax.swing.JTextField();
        txtTolerance = new javax.swing.JTextField();
        txtMagnitude = new javax.swing.JTextField();
        txtUnit = new javax.swing.JTextField();

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
                        .addComponent(lblTolerance)
                        .addComponent(lblMagnitude)
                        .addComponent(lblUnit))
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtId)
                        .addComponent(txtBrand)
                        .addComponent(txtPackage)
                        .addComponent(txtVoltage)
                        .addComponent(txtCurrent)
                        .addComponent(txtTolerance)
                        .addComponent(txtMagnitude)
                        .addComponent(txtUnit)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
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
                        .addComponent(lblTolerance)
                        .addComponent(txtTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMagnitude)
                        .addComponent(txtMagnitude, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUnit)
                        .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            double tolerance = Double.parseDouble(txtTolerance.getText().trim());
            double magnitude = Double.parseDouble(txtMagnitude.getText().trim());
            String unit = txtUnit.getText().trim();

            if (brand.isEmpty() || packageType.isEmpty() || unit.isEmpty()) {
                throw new IllegalArgumentException("No puede haber campos vacíos");
            }

            if (voltage <= 0 || current <= 0) {
                throw new IllegalArgumentException("Voltaje y corriente deben ser positivos");
            }

            if (tolerance < 0 || magnitude <= 0) {
                throw new IllegalArgumentException("Valores inválidos");
            }

            UnitMeasurement nominal = new UnitMeasurement(magnitude, unit);

            PassiveComponent component = new PassiveComponent(
                    id, brand, packageType, voltage, current, tolerance, nominal
            );

            ElectronicComponentService.getInstance().add(component);

            JOptionPane.showMessageDialog(this, "Componente pasivo guardado correctamente");
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
    private javax.swing.JTextField txtTolerance;
    private javax.swing.JTextField txtMagnitude;
    private javax.swing.JTextField txtUnit;
    private javax.swing.JButton btnSave;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GUIAddPassiveComponent().setVisible(true));
    }
}

