package com.components.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "electronic_component")
public abstract class ElectronicComponent {

    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(name = "package_type", nullable = false, length = 50)
    private String packageType;

    @Column(nullable = false)
    private double voltage;

    @Column(nullable = false)
    private double current;

    @Column(name = "pin_count", nullable = false)
    private int pinCount;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

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

    public Fabricante getFabricante() { return fabricante; }
    public void setFabricante(Fabricante fabricante) { this.fabricante = fabricante; }
}
