package observer;

public interface ComponentSubject {

    void addObserver(ComponentObserver observer);

    void removeObserver(ComponentObserver observer);

    void notifyComponentAdded();

    void notifyComponentDeleted(int id);

}