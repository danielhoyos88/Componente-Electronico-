package com.components.service;

import com.components.dto.FabricanteConComponentesDTO;
import com.components.model.ActiveComponent;
import com.components.model.Fabricante;
import com.components.model.PassiveComponent;
import com.components.observer.IComponentObserver;
import com.components.repository.ActiveComponentRepository;
import com.components.repository.FabricanteRepository;
import com.components.repository.PassiveComponentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentService {

    private final ActiveComponentRepository activeRepo;
    private final PassiveComponentRepository passiveRepo;
    private final FabricanteRepository fabricanteRepo;

    // Observer pattern — lista de observadores (no de componentes)
    private final List<IComponentObserver> observers = new ArrayList<>();

    public ComponentService(ActiveComponentRepository activeRepo,
                             PassiveComponentRepository passiveRepo,
                             FabricanteRepository fabricanteRepo) {
        this.activeRepo = activeRepo;
        this.passiveRepo = passiveRepo;
        this.fabricanteRepo = fabricanteRepo;
    }

    public void addObserver(IComponentObserver o) { observers.add(o); }
    public void removeObserver(IComponentObserver o) { observers.remove(o); }
    private void notifyList(String type, List<?> results) {
        observers.forEach(o -> o.onListRequested(type, results));
    }

    // ===== PASSIVE =====

    public PassiveComponent addPassive(PassiveComponent c) {
        if (passiveRepo.existsById(c.getId()) || activeRepo.existsById(c.getId()))
            throw new IllegalArgumentException("Ya existe un componente con ID " + c.getId());
        validate(c.getBrand(), c.getPackageType(), c.getVoltage(), c.getCurrent());
        if (c.getTolerance() < 0)
            throw new IllegalArgumentException("La tolerancia no puede ser negativa.");
        if (c.getNominalMagnitude() <= 0)
            throw new IllegalArgumentException("La magnitud nominal debe ser positiva.");
        if (c.getNominalUnit() == null || c.getNominalUnit().isBlank())
            throw new IllegalArgumentException("La unidad nominal es obligatoria.");
        c.setRegistrationDate(LocalDateTime.now());
        if (c.getFabricante() != null) {
            Fabricante fab = fabricanteRepo.findById(c.getFabricante().getId()).orElse(null);
            c.setFabricante(fab);
        }
        return passiveRepo.save(c);
    }

    public PassiveComponent findPassiveById(int id) {
        return passiveRepo.findById(id).orElse(null);
    }

    public PassiveComponent findPassiveByBrand(String brand) {
        return passiveRepo.findByBrandIgnoreCase(brand).orElse(null);
    }

    public List<PassiveComponent> findAllPassive() {
        List<PassiveComponent> result = passiveRepo.findAll();
        notifyList("PASSIVE", result);
        return result;
    }

    public List<PassiveComponent> findPassiveByFilters(String brand, String packageType) {
        List<PassiveComponent> result;
        if (brand != null && packageType != null)
            result = passiveRepo.findByBrandIgnoreCaseAndPackageTypeIgnoreCase(brand, packageType);
        else if (brand != null)
            result = passiveRepo.findByBrandIgnoreCase(brand).map(List::of).orElse(List.of());
        else if (packageType != null)
            result = passiveRepo.findByPackageTypeIgnoreCase(packageType);
        else
            result = passiveRepo.findAll();
        notifyList("PASSIVE_FILTERED", result);
        return result;
    }

    public PassiveComponent updatePassive(int id, PassiveComponent updated) {
        PassiveComponent existing = passiveRepo.findById(id).orElse(null);
        if (existing == null) return null;
        validate(updated.getBrand(), updated.getPackageType(), updated.getVoltage(), updated.getCurrent());
        existing.setBrand(updated.getBrand());
        existing.setPackageType(updated.getPackageType());
        existing.setVoltage(updated.getVoltage());
        existing.setCurrent(updated.getCurrent());
        existing.setPinCount(updated.getPinCount());
        existing.setTolerance(updated.getTolerance());
        existing.setNominalMagnitude(updated.getNominalMagnitude());
        existing.setNominalUnit(updated.getNominalUnit());
        if (updated.getFabricante() != null) {
            Fabricante fab = fabricanteRepo.findById(updated.getFabricante().getId()).orElse(null);
            existing.setFabricante(fab);
        }
        return passiveRepo.save(existing);
    }

    public boolean deletePassive(int id) {
        if (!passiveRepo.existsById(id)) return false;
        passiveRepo.deleteById(id);
        return true;
    }

    // ===== ACTIVE =====

    public ActiveComponent addActive(ActiveComponent c) {
        if (activeRepo.existsById(c.getId()) || passiveRepo.existsById(c.getId()))
            throw new IllegalArgumentException("Ya existe un componente con ID " + c.getId());
        validate(c.getBrand(), c.getPackageType(), c.getVoltage(), c.getCurrent());
        if (c.getGainFactor() <= 0)
            throw new IllegalArgumentException("El factor de ganancia debe ser positivo.");
        c.setRegistrationDate(LocalDateTime.now());
        if (c.getFabricante() != null) {
            Fabricante fab = fabricanteRepo.findById(c.getFabricante().getId()).orElse(null);
            c.setFabricante(fab);
        }
        return activeRepo.save(c);
    }

    public ActiveComponent findActiveById(int id) {
        return activeRepo.findById(id).orElse(null);
    }

    public ActiveComponent findActiveByBrand(String brand) {
        return activeRepo.findByBrandIgnoreCase(brand).orElse(null);
    }

    public List<ActiveComponent> findAllActive() {
        List<ActiveComponent> result = activeRepo.findAll();
        notifyList("ACTIVE", result);
        return result;
    }

    public List<ActiveComponent> findActiveByFilters(String brand, String packageType) {
        List<ActiveComponent> result;
        if (brand != null && packageType != null)
            result = activeRepo.findByBrandIgnoreCaseAndPackageTypeIgnoreCase(brand, packageType);
        else if (brand != null)
            result = activeRepo.findByBrandIgnoreCase(brand).map(List::of).orElse(List.of());
        else if (packageType != null)
            result = activeRepo.findByPackageTypeIgnoreCase(packageType);
        else
            result = activeRepo.findAll();
        notifyList("ACTIVE_FILTERED", result);
        return result;
    }

    public ActiveComponent updateActive(int id, ActiveComponent updated) {
        ActiveComponent existing = activeRepo.findById(id).orElse(null);
        if (existing == null) return null;
        validate(updated.getBrand(), updated.getPackageType(), updated.getVoltage(), updated.getCurrent());
        existing.setBrand(updated.getBrand());
        existing.setPackageType(updated.getPackageType());
        existing.setVoltage(updated.getVoltage());
        existing.setCurrent(updated.getCurrent());
        existing.setGainFactor(updated.getGainFactor());
        existing.setPinNames(updated.getPinNames());
        if (updated.getFabricante() != null) {
            Fabricante fab = fabricanteRepo.findById(updated.getFabricante().getId()).orElse(null);
            existing.setFabricante(fab);
        }
        return activeRepo.save(existing);
    }

    public boolean deleteActive(int id) {
        if (!activeRepo.existsById(id)) return false;
        activeRepo.deleteById(id);
        return true;
    }

    // ===== CONSULTA ESPECIAL: Fabricante + sus activos + sus pasivos =====

    public FabricanteConComponentesDTO findComponentesByFabricante(int fabricanteId) {
        Fabricante fab = fabricanteRepo.findById(fabricanteId).orElse(null);
        if (fab == null) return null;
        List<ActiveComponent> activos = activeRepo.findByFabricanteId(fabricanteId);
        List<PassiveComponent> pasivos = passiveRepo.findByFabricanteId(fabricanteId);
        notifyList("CONSULTA_FABRICANTE_ACTIVOS", activos);
        notifyList("CONSULTA_FABRICANTE_PASIVOS", pasivos);
        return new FabricanteConComponentesDTO(fab, activos, pasivos);
    }

    // ===== VALIDACIONES =====

    private void validate(String brand, String packageType, double voltage, double current) {
        if (brand == null || brand.isBlank())
            throw new IllegalArgumentException("La marca es obligatoria.");
        if (packageType == null || packageType.isBlank())
            throw new IllegalArgumentException("El encapsulado es obligatorio.");
        if (voltage <= 0)
            throw new IllegalArgumentException("El voltaje debe ser positivo.");
        if (current <= 0)
            throw new IllegalArgumentException("La corriente debe ser positiva.");
    }
}
