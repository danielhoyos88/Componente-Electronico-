package com.components.controller;

import com.components.dto.FabricanteConComponentesDTO;
import com.components.service.ComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consulta")
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ComponentService service;

    public ConsultaController(ComponentService service) {
        this.service = service;
    }

    // Consulta especial: retorna fabricante + sus componentes activos + sus componentes pasivos
    @GetMapping("/fabricante/{id}/componentes")
    public ResponseEntity<?> getComponentesByFabricante(@PathVariable int id) {
        FabricanteConComponentesDTO result = service.findComponentesByFabricante(id);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }
}
