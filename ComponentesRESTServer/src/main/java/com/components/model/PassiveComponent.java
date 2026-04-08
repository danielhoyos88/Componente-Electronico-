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
}
