package com.components.model;

import java.util.List;

public class ActiveComponent extends ElectronicComponent {

    private double gainFactor;
    private List<String> pinNames;

    public ActiveComponent() {}

    public ActiveComponent(int id, String brand, String packageType,
                            double voltage, double current,
                            double gainFactor, List<String> pinNames) {
        super(id, brand, packageType, voltage, current,
              pinNames != null ? pinNames.size() : 0);
        this.gainFactor = gainFactor;
        this.pinNames = pinNames;
    }

    public double getGainFactor() { return gainFactor; }
    public void setGainFactor(double gainFactor) { this.gainFactor = gainFactor; }

    public List<String> getPinNames() { return pinNames; }
    public void setPinNames(List<String> pinNames) {
        this.pinNames = pinNames;
        if (pinNames != null) setPinCount(pinNames.size());
    }
}
