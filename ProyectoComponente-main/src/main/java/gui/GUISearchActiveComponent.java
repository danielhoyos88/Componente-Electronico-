package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ActiveComponent;

public class GUISearchActiveComponent extends JFrame {

    private ActiveComponent component;

    public GUISearchActiveComponent(String id, ActiveComponent comp) {
        this.component = comp;

        setTitle("Consultar Componente Activo - ID: " + id);
        setSize(450, 400);
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
        panel.setLayout(new GridLayout(10, 2, 5, 5));

        panel.add(new JLabel("ID:")); panel.add(field(String.valueOf(component.getId())));
        panel.add(new JLabel("Brand:")); panel.add(field(component.getBrand()));
        panel.add(new JLabel("Package:")); panel.add(field(component.getPackageType()));
        panel.add(new JLabel("Voltage:")); panel.add(field(String.valueOf(component.getVoltage())));
        panel.add(new JLabel("Current:")); panel.add(field(String.valueOf(component.getCurrent())));
        panel.add(new JLabel("Pin Count:")); panel.add(field(String.valueOf(component.getPinCount())));
        panel.add(new JLabel("Pin Names:")); panel.add(field(component.getPinLabels()));
        panel.add(new JLabel("Gain Factor:")); panel.add(field(String.valueOf(component.getGainFactor())));
        panel.add(new JLabel("Impedance:")); panel.add(field(String.valueOf(component.calculateImpedance())));
        panel.add(new JLabel("Power:")); panel.add(field(String.valueOf(component.calculatePower())));

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