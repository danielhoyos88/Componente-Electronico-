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

    // ── Builder ──────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int id;
        private String brand, packageType;
        private double voltage, current, gainFactor;
        private List<String> pinNames;

        public Builder id(int id)                      { this.id = id; return this; }
        public Builder brand(String brand)             { this.brand = brand; return this; }
        public Builder packageType(String packageType) { this.packageType = packageType; return this; }
        public Builder voltage(double voltage)         { this.voltage = voltage; return this; }
        public Builder current(double current)         { this.current = current; return this; }
        public Builder gainFactor(double gainFactor)   { this.gainFactor = gainFactor; return this; }
        public Builder pinNames(List<String> pinNames) { this.pinNames = pinNames; return this; }

        public ActiveComponent build() {
            return new ActiveComponent(id, brand, packageType, voltage, current, gainFactor, pinNames);
        }
    }
}
