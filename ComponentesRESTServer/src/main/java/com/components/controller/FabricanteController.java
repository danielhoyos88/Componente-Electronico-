package com.components.controller;

import com.components.model.Fabricante;
import com.components.service.FabricanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fabricante")
@CrossOrigin(origins = "*")
public class FabricanteController {

    private final FabricanteService service;

    public FabricanteController(FabricanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Fabricante fabricante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.add(fabricante));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Fabricante f = service.findById(id);
        if (f == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(f);
    }

    @GetMapping
    public ResponseEntity<List<Fabricante>> findAll(
            @RequestParam(required = false) String pais) {
        if (pais != null) return ResponseEntity.ok(service.findByPais(pais));
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Fabricante updated) {
        try {
            Fabricante result = service.update(id, updated);
            if (result == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Fabricante f = service.findById(id);
        if (f == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok(f);
    }
}
