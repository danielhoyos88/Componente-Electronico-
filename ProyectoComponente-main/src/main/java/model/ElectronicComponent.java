/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel
 */

public abstract class ElectronicComponent {
    private int id;
    private int pinCount;
    private String brand;
    private String packageType;
    private double voltage;
    private double current;
    private final int DEFAULT_PIN_COUNT = 2;
    
    public ElectronicComponent() {
        this.pinCount = DEFAULT_PIN_COUNT;
    }
    
    public ElectronicComponent(int id, String brand, String packageType) {
        this.id = id;
        this.brand = brand;
        this.packageType = packageType;
        this.voltage = 0;
        this.current = 0;
        this.pinCount = DEFAULT_PIN_COUNT;
    }
    
    public ElectronicComponent(int id, String brand, String packageType, int pinCount) {
        this.id = id;
        this.brand = brand;
        this.packageType = packageType;
        this.voltage = 0;
        this.current = 0;
        this.pinCount = pinCount;
    }
    
    public ElectronicComponent(int id, String brand, String packageType, double voltage, double current) {
        this.id = id;
        this.brand = brand;
        this.packageType = packageType;
        this.voltage = voltage;
        this.current = current;
        this.pinCount = DEFAULT_PIN_COUNT;
    }
    
    public ElectronicComponent(int id, String brand, String packageType, double voltage, double current, int pinCount) {
        this.id = id;
        this.brand = brand;
        this.packageType = packageType;
        this.voltage = voltage;
        this.current = current;
        this.pinCount = pinCount;
    }
    
    public abstract double calculateImpedance();
    
    public abstract double calculatePower();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPinCount() {
        return pinCount;
    }

    public void setPinCount(int pinCount) {
        this.pinCount = pinCount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }
    
   @Override
public String toString() {
    return "ID: " + id +
           "\nMarca: " + brand +
           "\nEncapsulado: " + packageType +
           "\nVoltaje: " + voltage + " V" +
           "\nCorriente: " + current + " A" +
           "\nNúmero de pines: " + pinCount;
}
}
