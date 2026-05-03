package com.components.dto;

import com.components.model.ActiveComponent;
import com.components.model.Fabricante;
import com.components.model.PassiveComponent;

import java.util.List;

public class FabricanteConComponentesDTO {

    private Fabricante fabricante;
    private List<ActiveComponent> componentesActivos;
    private List<PassiveComponent> componentesPasivos;

    public FabricanteConComponentesDTO(Fabricante fabricante,
                                        List<ActiveComponent> componentesActivos,
                                        List<PassiveComponent> componentesPasivos) {
        this.fabricante = fabricante;
        this.componentesActivos = componentesActivos;
        this.componentesPasivos = componentesPasivos;
    }

    public Fabricante getFabricante() { return fabricante; }
    public List<ActiveComponent> getComponentesActivos() { return componentesActivos; }
    public List<PassiveComponent> getComponentesPasivos() { return componentesPasivos; }
}
