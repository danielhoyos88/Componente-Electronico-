package observer;

import model.ElectronicComponent;

/**
 * Observer que registra eventos del sistema.
 * Se activa cuando se agregan o eliminan componentes.
 */
public class ComponentLogger implements ComponentObserver {

    @Override
    public void onComponentAdded(ElectronicComponent component) {

        System.out.println(
                "[LOG] Component added -> ID: "
                + component.getId()
                + " | Brand: "
                + component.getBrand()
        );
    }

    @Override
    public void onComponentDeleted(int id) {

        System.out.println(
                "[LOG] Component deleted -> ID: " + id
        );
    }
}