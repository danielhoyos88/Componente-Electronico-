package gui;

import com.mycompany.electroniccomponentsproject.service.ElectronicComponentService;

public class GUIMain extends javax.swing.JFrame {

    private static final String PROJECT_NAME = "Electronic Components Project";
    private static final String PROJECT_VERSION = "1.0 Beta";
    private static final String TEAM_NAMES = "Daniel Hoyos - 2220221002";

    private final ElectronicComponentService service = ElectronicComponentService.getInstance();

    public GUIMain() {
        initComponents();
        configureMenu();
        setTitle("Electronic Components Project");
        setLocationRelativeTo(null);
        setSize(600, 400);
        addMainPanel();
    }

    private void addMainPanel() {
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        mainPanel.setBackground(new java.awt.Color(240, 248, 255));
        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.JLabel titleLabel = new javax.swing.JLabel(PROJECT_NAME);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titleLabel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);

        javax.swing.JLabel versionLabel = new javax.swing.JLabel("Version " + PROJECT_VERSION);
        versionLabel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);

        javax.swing.JLabel teamLabel = new javax.swing.JLabel("Team: " + TEAM_NAMES);
        teamLabel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);

        mainPanel.add(javax.swing.Box.createVerticalStrut(40));
        mainPanel.add(titleLabel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(10));
        mainPanel.add(versionLabel);
        mainPanel.add(javax.swing.Box.createVerticalStrut(20));
        mainPanel.add(teamLabel);
        mainPanel.add(javax.swing.Box.createVerticalGlue());

        getContentPane().add(mainPanel);
    }

    private void configureMenu() {
        setJMenuBar(buildMenuBar());
    }

    private javax.swing.JMenuBar buildMenuBar() {
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();

        javax.swing.JMenu passiveMenu = new javax.swing.JMenu("Componente Pasivo");
        passiveMenu.add(createMenuItem("Añadir", this::openAddPassiveComponent));
        passiveMenu.add(createMenuItem("Consultar", this::openSearchPassiveComponent));
        passiveMenu.add(createMenuItem("Actualizar", this::openUpdatePassiveComponent));
        passiveMenu.add(createMenuItem("Eliminar", () -> deleteComponent("pasivo")));
        passiveMenu.add(createMenuItem("Listar", this::openListPassiveComponent));
        passiveMenu.add(new javax.swing.JSeparator());
        passiveMenu.add(createMenuItem("Calcular Impedancia", this::calculatePassiveImpedance));
        passiveMenu.add(createMenuItem("Calcular Potencia", this::calculatePassivePower));

        javax.swing.JMenu activeMenu = new javax.swing.JMenu("Componente Activo");
        activeMenu.add(createMenuItem("Añadir", this::openAddActiveComponent));
        activeMenu.add(createMenuItem("Consultar", this::openSearchActiveComponent));
        activeMenu.add(createMenuItem("Actualizar", this::openUpdateActiveComponent));
        activeMenu.add(createMenuItem("Eliminar", () -> deleteComponent("activo")));
        activeMenu.add(createMenuItem("Listar", this::openListActiveComponent));
        activeMenu.add(new javax.swing.JSeparator());
        activeMenu.add(createMenuItem("Calcular Impedancia", this::calculateActiveImpedance));
        activeMenu.add(createMenuItem("Calcular Potencia", this::calculateActivePower));

        javax.swing.JMenu unitMenu = new javax.swing.JMenu("Unidad de Medida");
        unitMenu.add(createMenuItem("Gestionar", this::openManageUnitMeasurement));

        javax.swing.JMenu helpMenu = new javax.swing.JMenu("Ayuda");
        helpMenu.add(createMenuItem("Ver Historial", this::showHistory));
        helpMenu.add(createMenuItem("Acerca de...", this::showAboutDialog));

        menuBar.add(passiveMenu);
        menuBar.add(activeMenu);
        menuBar.add(unitMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private javax.swing.JMenuItem createMenuItem(String text, Runnable action) {
        javax.swing.JMenuItem item = new javax.swing.JMenuItem(text);
        item.addActionListener(e -> action.run());
        return item;
    }

    // ==================== PASIVOS ====================

    private void openAddPassiveComponent() {
        new GUIAddPassiveComponent().setVisible(true);
    }

    private void openSearchPassiveComponent() {
        Integer id = requestValidId("pasivo");
        if (id == null) return;
        var component = service.findComponentById(id);
        if (component == null || !(component instanceof model.PassiveComponent)) {
            showError("Componente pasivo no encontrado.");
            return;
        }
        new GUISearchPassiveComponent(String.valueOf(id), (model.PassiveComponent) component).setVisible(true);
    }

    private void openUpdatePassiveComponent() {
        Integer id = requestValidId("pasivo");
        if (id == null) return;
        model.PassiveComponent comp = service.findPassiveById(id);
        if (comp == null) { showError("Componente pasivo no encontrado."); return; }
        new GUIUpdatePassiveComponent(comp).setVisible(true);
    }

    private void openListPassiveComponent() {
        new GUIListPassiveComponent().setVisible(true);
    }

    private void calculatePassiveImpedance() { calculateImpedance("pasivo"); }
    private void calculatePassivePower() { calculatePower("pasivo"); }

    // ==================== ACTIVOS ====================

    private void openAddActiveComponent() {
        new GUIAddActiveComponent().setVisible(true);
    }

    private void openSearchActiveComponent() {
        Integer id = requestValidId("activo");
        if (id == null) return;
        var component = service.findComponentById(id);
        if (component == null || !(component instanceof model.ActiveComponent)) {
            showError("Componente activo no encontrado.");
            return;
        }
        new GUISearchActiveComponent(String.valueOf(id), (model.ActiveComponent) component).setVisible(true);
    }

    private void openUpdateActiveComponent() {
        Integer id = requestValidId("activo");
        if (id == null) return;
        model.ActiveComponent comp = service.findActiveById(id);
        if (comp == null) { showError("Componente activo no encontrado."); return; }
        new GUIUpdateActiveComponent(comp).setVisible(true);
    }

    private void openListActiveComponent() {
        new GUIListActiveComponent().setVisible(true);
    }

    private void calculateActiveImpedance() { calculateImpedance("activo"); }
    private void calculateActivePower() { calculatePower("activo"); }

    // ==================== UNIDAD DE MEDIDA ====================

    private void openManageUnitMeasurement() {
        new GUIManageUnitMeasurement().setVisible(true);
    }

    // ==================== LÓGICA ====================

    private void calculateImpedance(String type) {
        Integer id = requestValidId(type);
        if (id == null) return;
        try {
            double result = service.calculateImpedanceById(id);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Impedancia calculada: " + result, "Resultado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { showError(e.getMessage()); }
    }

    private void calculatePower(String type) {
        Integer id = requestValidId(type);
        if (id == null) return;
        try {
            double result = service.calculatePowerById(id);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Potencia calculada: " + result, "Resultado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { showError(e.getMessage()); }
    }

    private void deleteComponent(String type) {
        Integer id = requestValidId(type);
        if (id == null) return;
        var component = service.findComponentById(id);
        if (component == null) { showError("Componente no encontrado."); return; }
        if (type.equals("pasivo") && !(component instanceof model.PassiveComponent)) {
            showError("El ID no corresponde a un componente pasivo."); return;
        }
        if (type.equals("activo") && !(component instanceof model.ActiveComponent)) {
            showError("El ID no corresponde a un componente activo."); return;
        }
        String message = "Información del componente:\n\n" + component.toString() + "\n\n¿Desea eliminar este componente?";
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, message,
                "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            boolean deleted = service.deleteById(id);
            if (deleted) {
                javax.swing.JOptionPane.showMessageDialog(this, "Componente eliminado correctamente.",
                        "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else { showError("No se pudo eliminar el componente."); }
        }
    }

    private Integer requestValidId(String type) {
        String input = javax.swing.JOptionPane.showInputDialog(this,
                "Ingrese el ID del componente " + type + ":");
        if (input == null) return null;
        try {
            int id = Integer.parseInt(input.trim());
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            showError("ID inválido. Debe ser entero positivo.");
            return null;
        }
    }

    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void showAboutDialog() {
        config.ApplicationConfig cfg = config.ApplicationConfig.getInstance();
        javax.swing.JOptionPane.showMessageDialog(this,
                cfg.getSystemName() + "\nVersion: " + cfg.getVersion()
                        + "\n\nIntegrantes: " + TEAM_NAMES,
                "Acerca de...", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHistory() {
        java.util.List<history.MeasurementRecord> hist = service.getHistory();
        if (hist.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay registros en el historial.",
                    "Historial", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        hist.forEach(r -> sb.append(r.toString()).append("\n"));
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(sb.toString());
        textArea.setEditable(false);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(450, 200));
        javax.swing.JOptionPane.showMessageDialog(this, scroll, "Historial",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GUIMain().setVisible(true));
    }
}
