package history;

/**
 * Representa un registro de medición de un componente.
 */
public class MeasurementRecord {

    private int componentId;
    private String type;
    private double value;

    public MeasurementRecord(int componentId, String type, double value) {
        this.componentId = componentId;
        this.type = type;
        this.value = value;
    }

    public int getComponentId() { return componentId; }
    public String getType() { return type; }
    public double getValue() { return value; }

    @Override
    public String toString() {
        return "MeasurementRecord{componentId=" + componentId + ", type=" + type + ", value=" + value + "}";
    }
}