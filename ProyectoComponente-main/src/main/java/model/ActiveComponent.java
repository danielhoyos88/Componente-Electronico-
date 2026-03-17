/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class ActiveComponent extends ElectronicComponent implements IAplicable {
    
    private List <String> pinNames;
    private double gainFactor;  

    public ActiveComponent() {
        super();
    }

    public ActiveComponent(int id, String brand, String packageType, double voltage, double current, double gainFactor, List <String> pinNames) throws Exception {
        super(id, brand, packageType, voltage, current);
        this.gainFactor = gainFactor;
        this.pinNames = pinNames;
        
        // Actualizamos el conteo de pines de la clase principal
        if (pinNames != null && !pinNames.isEmpty()) {
            this.setPinCount(pinNames.size());
        } else {
            throw new Exception("Un componente activo debe tener pines");
        }
    }


    @Override
    public double calculateImpedance() {
        /* Lógica polimórfica: A diferencia del pasivo, aquí la impedancia 
           es afectada por la ganancia y el número de terminales activos.
        */
        if (this.getCurrent() == 0) return 0.0;
        
        double baseImpedance = this.getVoltage() / this.getCurrent();
        // La impedancia equivalente depende de la ganancia de la red
        return (baseImpedance * gainFactor);
    }
    
    public double calculateImpedance(int numberPins) throws Exception{          
        if (numberPins <= 0 || numberPins > this.getPinCount()) {
            throw new Exception ("Numero de pines invalido");
        }
        
        double baseImpedance = this.calculateImpedance();
        // Simulamos que a más pines, la impedancia equivalente se distribuye
        return baseImpedance / numberPins;
    }
    
    @Override
    public double calculatePower() {
        /* Lógica polimórfica para componentes activos:
           La potencia aquí representa la capacidad de transferencia o amplificación.
           Multiplicamos la potencia de entrada (V * I) por el factor de ganancia.
        */
        double inputPower = this.getVoltage() * this.getCurrent();
        double outputPower = inputPower * this.gainFactor;

        /* Añadimos una lógica basada en la estructura física (pines):
           Simulamos que si el componente tiene más de 2 pines (como un transistor o IC),
           hay una pérdida de eficiencia del 5% por cada terminal adicional.
        */
        if (this.getPinCount() > 2) {
            double efficiencyLoss = (this.getPinCount() - 2) * 0.05;
            outputPower = outputPower * (1 - efficiencyLoss);
        }
        
        return outputPower;
    }

    @Override
    public void adjustOperationPoint(double factor) {
        // Implementación de la interfaz: ajusta la corriente de operación
        double nuevaCorriente = this.getCurrent() * factor;
        this.setCurrent(nuevaCorriente);
    }

    public String getPinLabels() {
        if (pinNames == null || pinNames.isEmpty()) {
            return "No asignados";
        }
        
        String res = "";
        int count = 1;
        for (String pinName : pinNames) {
            res += pinName;
            if (count < pinNames.size()) {
                res += ", ";
            }
             
            count++;
                
        }
        return res;
    }

    public double getGainFactor() {
        return gainFactor;
    }

    public void setGainFactor(double gainFactor) {
        this.gainFactor = gainFactor;
    }

   public List<String> getPinNames() {
    return pinNames;
}

/* No incluimos setPinNames para evitar cambiar la estructura física 
   del componente una vez creado, manteniendo la integridad.
*/

@Override
public String toString() {
    return super.toString()
            + "\nTipo: Activo"
            + "\nFactor de ganancia: " + gainFactor
            + "\nPines: " + getPinLabels();
}

}