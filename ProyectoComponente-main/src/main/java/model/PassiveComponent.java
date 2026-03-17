/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel
 */
public class PassiveComponent extends ElectronicComponent {
    private double tolerance;
    private UnitMeasurement nominalValue;
    
    public PassiveComponent() {
        super();
    }
    
    public PassiveComponent(int id, String brand, String packageType, double tolerance, UnitMeasurement nominalValue) {
        super(id, brand, packageType);
        this.tolerance = tolerance;
        this.nominalValue = nominalValue;
    }
    
    public PassiveComponent(int id, String brand, String packageType, double voltage, double current, double tolerance, UnitMeasurement nominalValue) {
        super(id, brand, packageType, voltage, current);
        this.tolerance = tolerance;
        this.nominalValue = nominalValue;
    }
    
    /*
    * Obtiene la unidad de medida.
    * Nota: Esta clase no implementa setter para 'unit' para garantizar 
    * que la naturaleza de la medida sea inmutable tras su creación.
    */
    public UnitMeasurement getNominalValue () {
        return this.nominalValue;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
    
    @Override
    public double calculateImpedance() {
        double baseValue;

        // 1. Determinamos el valor base
        if (this.getCurrent() > 0) {
            baseValue = this.getVoltage() / this.getCurrent();
        } else {
            baseValue = this.getNominalValue().getMagnitude();
        }

        // 2. Aplicamos la lógica de tolerancia aleatoria
        // Generamos un número entre -1.0 y 1.0
        double randomDirection = (Math.random() * 2) - 1; 

        // Calculamos el error: (valor * %tolerancia * dirección aleatoria)
        double variation = baseValue * (this.getTolerance() / 100.0) * randomDirection;

        return baseValue + variation;
    }
    
    @Override
    public double calculatePower() {
        // 1. Cálculo teórico base: P = V * I
        double basePower = this.getVoltage() * this.getCurrent();

        // 2. Aplicamos la lógica de tolerancia aleatoria
        // Generamos un factor entre -1.0 y 1.0 para simular la variación real
        double randomVariation = (Math.random() * 2) - 1;

        // La potencia real fluctúa según la tolerancia del material
        double powerWithTolerance = basePower * (1 + (this.getTolerance() / 100.0) * randomVariation);

        return powerWithTolerance;
    }
    
@Override
public String toString() {
    return super.toString() +
           "\nTipo: Pasivo" +
           "\nValor nominal: " + nominalValue.getMagnitude() + " " + nominalValue.getUnit() +
           "\nTolerancia: ±" + tolerance + "%";
}
   
}
 
 