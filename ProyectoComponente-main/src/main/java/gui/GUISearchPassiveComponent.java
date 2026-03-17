package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.PassiveComponent;

public class GUISearchPassiveComponent extends JFrame {

    private PassiveComponent component;

    public GUISearchPassiveComponent(String id, PassiveComponent comp) {
        this.component = comp;

        setTitle("Consultar Componente Pasivo - ID: " + id);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        if (component == null) {
            JOptionPane.showMessageDialog(this,
                    "Componente no encontrado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("ID:")); panel.add(field(String.valueOf(component.getId())));
        panel.add(new JLabel("Brand:")); panel.add(field(component.getBrand()));
        panel.add(new JLabel("Package:")); panel.add(field(component.getPackageType()));
        panel.add(new JLabel("Voltage:")); panel.add(field(String.valueOf(component.getVoltage())));
        panel.add(new JLabel("Current:")); panel.add(field(String.valueOf(component.getCurrent())));
        panel.add(new JLabel("Tolerance:")); panel.add(field(component.getTolerance() + " %"));
        panel.add(new JLabel("Nominal Magnitude:")); panel.add(field(String.valueOf(component.getNominalValue().getMagnitude())));
        panel.add(new JLabel("Unit:")); panel.add(field(component.getNominalValue().getUnit()));

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private JTextField field(String value) {
        JTextField tf = new JTextField(value);
        tf.setEditable(false);
        return tf;
    }
}