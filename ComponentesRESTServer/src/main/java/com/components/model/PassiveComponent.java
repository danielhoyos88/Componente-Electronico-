package com.components.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passive_component")
public class PassiveComponent extends ElectronicComponent {

    @Column(nullable = false)
    private double tolerance;

    @Column(name = "nominal_magnitude", nullable = false)
    private double nominalMagnitude;

    @Column(name = "nominal_unit", nullable = false, length = 20)
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
