package com.components.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "active_component")
public class ActiveComponent extends ElectronicComponent {

    @Column(name = "gain_factor", nullable = false)
    private double gainFactor;

    @Column(name = "pin_names", length = 500)
    private String pinNamesRaw;

    public ActiveComponent() {}

    public ActiveComponent(int id, String brand, String packageType,
                            double voltage, double current,
                            double gainFactor, List<String> pinNames) {
        super(id, brand, packageType, voltage, current,
              pinNames != null ? pinNames.size() : 0);
        this.gainFactor = gainFactor;
        setPinNames(pinNames);
    }

    public double getGainFactor() { return gainFactor; }
    public void setGainFactor(double gainFactor) { this.gainFactor = gainFactor; }

    public List<String> getPinNames() {
        if (pinNamesRaw == null || pinNamesRaw.isBlank()) return List.of();
        return Arrays.asList(pinNamesRaw.split(","));
    }

    public void setPinNames(List<String> pinNames) {
        if (pinNames != null) {
            this.pinNamesRaw = String.join(",", pinNames);
            setPinCount(pinNames.size());
        }
    }
}
