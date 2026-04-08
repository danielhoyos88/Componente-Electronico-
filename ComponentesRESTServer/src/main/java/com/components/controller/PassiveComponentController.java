package com.components.controller;

import com.components.model.PassiveComponent;
import com.components.service.ComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passive")
@CrossOrigin(origins = "*")
public class PassiveComponentController {

    private final ComponentService service;

    public PassiveComponentController(ComponentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PassiveComponent component) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.addPassive(component));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PassiveComponent c = service.findPassiveById(id);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @GetMapping
    public ResponseEntity<List<PassiveComponent>> findAll(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String packageType) {
        return ResponseEntity.ok(service.findPassiveByFilters(brand, packageType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody PassiveComponent updated) {
        PassiveComponent result = service.updatePassive(id, updated);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        PassiveComponent c = service.findPassiveById(id);
        if (c == null) return ResponseEntity.notFound().build();
        service.deletePassive(id);
        return ResponseEntity.ok(c);
    }
}
