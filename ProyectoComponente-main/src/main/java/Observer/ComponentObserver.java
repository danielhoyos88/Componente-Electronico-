package observer;

import model.ElectronicComponent;

public interface ComponentObserver {

    void onComponentAdded(ElectronicComponent component);

    void onComponentDeleted(int id);

}