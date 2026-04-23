package com.components.controller;

import com.components.model.ActiveComponent;
import com.components.service.ComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/active")
@CrossOrigin(origins = "*")
public class ActiveComponentController {

    private final ComponentService service;

    public ActiveComponentController(ComponentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ActiveComponent component) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.addActive(component));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ActiveComponent c = service.findActiveById(id);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String brand) {
        if (id != null) {
            ActiveComponent c = service.findActiveById(id);
            if (c == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(c);
        }
        if (brand != null) {
            ActiveComponent c = service.findActiveByBrand(brand);
            if (c == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(c);
        }
        return ResponseEntity.badRequest().body("Debe proporcionar id o brand");
    }

    @GetMapping
    public ResponseEntity<List<ActiveComponent>> findAll(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String packageType) {
        return ResponseEntity.ok(service.findActiveByFilters(brand, packageType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ActiveComponent updated) {
        ActiveComponent result = service.updateActive(id, updated);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ActiveComponent c = service.findActiveById(id);
        if (c == null) return ResponseEntity.notFound().build();
        service.deleteActive(id);
        return ResponseEntity.ok(c);
    }
}
