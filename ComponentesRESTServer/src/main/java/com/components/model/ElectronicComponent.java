@Daniel

package com.components.model;

import java.time.LocalDateTime;

public abstract class ElectronicComponent {

    private int id;
    private String brand;
    private String packageType;
    private double voltage;
    private double current;
    private int pinCount;
    private LocalDateTime registrationDate;

    public ElectronicComponent() {}

    public ElectronicComponent(int id, String brand, String packageType,
                                double voltage, double current, int pinCount) {
        this.id = id;
        this.brand = brand;
        this.packageType = packageType;
        this.voltage = voltage;
        this.current = current;
        this.pinCount = pinCount;
        this.registrationDate = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getPackageType() { return packageType; }
    public void setPackageType(String packageType) { this.packageType = packageType; }

    public double getVoltage() { return voltage; }
    public void setVoltage(double voltage) { this.voltage = voltage; }

    public double getCurrent() { return current; }
    public void setCurrent(double current) { this.current = current; }

    public int getPinCount() { return pinCount; }
    public void setPinCount(int pinCount) { this.pinCount = pinCount; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}
