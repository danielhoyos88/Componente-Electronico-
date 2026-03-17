/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel
 */
public final class UnitMeasurement {
    private double magnitude;
    private String unit;
    
    public UnitMeasurement() {}

    public UnitMeasurement(double magnitude, String unit) {
        this.magnitude = magnitude;
        this.unit = unit;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getUnit() {
        return unit;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    @Override
    public String toString() {
        return magnitude + " " + unit;
    }
}
