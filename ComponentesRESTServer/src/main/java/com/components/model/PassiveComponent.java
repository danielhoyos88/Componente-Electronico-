package com.components.model;

public class PassiveComponent extends ElectronicComponent {

    private double tolerance;
    private double nominalMagnitude;
    private String nominalUnit;

    public PassiveComponent() {}

    public PassiveComponent(int id, String brand, String packageType,
                             double voltage, double current, int pinCount,
                             double tolerance, double nominalMagnitude, String nominalUnit) {
        super(id, brand, packageType, voltage, current, pinCount);
        this.tolerance = tolerance;
        this.nominalMagnitude = nominalMagnitude;
        this.nominalUnit = nominalUnit;
    }

    public double getTolerance() { return tolerance; }
    public void setTolerance(double tolerance) { this.tolerance = tolerance; }

    public double getNominalMagnitude() { return nominalMagnitude; }
    public void setNominalMagnitude(double nominalMagnitude) { this.nominalMagnitude = nominalMagnitude; }

    public String getNominalUnit() { return nominalUnit; }
    public void setNominalUnit(String nominalUnit) { this.nominalUnit = nominalUnit; }

    // ── Builder ──────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int id;
        private String brand, packageType, nominalUnit;
        private double voltage, current, tolerance, nominalMagnitude;
        private int pinCount;

        public Builder id(int id)                           { this.id = id; return this; }
        public Builder brand(String brand)                  { this.brand = brand; return this; }
        public Builder packageType(String packageType)      { this.packageType = packageType; return this; }
        public Builder voltage(double voltage)              { this.voltage = voltage; return this; }
        public Builder current(double current)              { this.current = current; return this; }
        public Builder pinCount(int pinCount)               { this.pinCount = pinCount; return this; }
        public Builder tolerance(double tolerance)          { this.tolerance = tolerance; return this; }
        public Builder nominalMagnitude(double nominalMagnitude) { this.nominalMagnitude = nominalMagnitude; return this; }
        public Builder nominalUnit(String nominalUnit)      { this.nominalUnit = nominalUnit; return this; }

        public PassiveComponent build() {
            return new PassiveComponent(id, brand, packageType, voltage, current,
                    pinCount, tolerance, nominalMagnitude, nominalUnit);
        }
    }
}
