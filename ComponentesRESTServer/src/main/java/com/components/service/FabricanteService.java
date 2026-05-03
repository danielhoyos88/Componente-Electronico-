package com.components.service;

import com.components.model.Fabricante;
import com.components.repository.FabricanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricanteService {

    private final FabricanteRepository repo;

    public FabricanteService(FabricanteRepository repo) {
        this.repo = repo;
    }

    public Fabricante add(Fabricante f) {
        if (repo.existsById(f.getId()))
            throw new IllegalArgumentException("Ya existe un fabricante con ID " + f.getId());
        if (f.getNombre() == null || f.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del fabricante es obligatorio.");
        if (f.getPais() == null || f.getPais().isBlank())
            throw new IllegalArgumentException("El país del fabricante es obligatorio.");
        return repo.save(f);
    }

    public Fabricante findById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Fabricante> findAll() {
        return repo.findAll();
    }

    public List<Fabricante> findByPais(String pais) {
        return repo.findByPais(pais);
    }

    public Fabricante update(int id, Fabricante updated) {
        Fabricante existing = repo.findById(id).orElse(null);
        if (existing == null) return null;
        if (updated.getNombre() == null || updated.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del fabricante es obligatorio.");
        if (updated.getPais() == null || updated.getPais().isBlank())
            throw new IllegalArgumentException("El país del fabricante es obligatorio.");
        existing.setNombre(updated.getNombre());
        existing.setPais(updated.getPais());
        return repo.save(existing);
    }

    public boolean delete(int id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
