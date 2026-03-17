package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mycompany.electroniccomponentsproject.service.ElectronicComponentService;
import model.ActiveComponent;
import model.ElectronicComponent;
import observer.ComponentObserver;

public class GUIListActiveComponent extends JFrame implements ComponentObserver {

    private JTable table;
    private DefaultTableModel tableModel;
    private final ElectronicComponentService service = ElectronicComponentService.getInstance();

    public GUIListActiveComponent() {
        setTitle("Listar Componentes Activos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadData();

        // Registrar como observer para actualizacion automatica
        service.addObserver(this);

        // Al cerrar, dejar de escuchar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                service.removeObserver(GUIListActiveComponent.this);
            }
        });
    }

    private void initUI() {
        String[] columns = {"ID", "Brand", "Package", "Voltage", "Current", "Gain Factor", "Pins"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<ActiveComponent> list = service.findComponentByType(ActiveComponent.class);
        for (ActiveComponent comp : list) {
            tableModel.addRow(new Object[]{
                comp.getId(), comp.getBrand(), comp.getPackageType(),
                comp.getVoltage(), comp.getCurrent(),
                comp.getGainFactor(), comp.getPinLabels()
            });
        }
    }

    // Observer: se llama automaticamente al agregar un componente
    @Override
    public void onComponentAdded(ElectronicComponent component) {
        loadData();
    }

    // Observer: se llama automaticamente al eliminar un componente
    @Override
    public void onComponentDeleted(int id) {
        loadData();
    }
}
