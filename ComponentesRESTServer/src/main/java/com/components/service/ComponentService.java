package com.components.service;

import com.components.model.ActiveComponent;
import com.components.model.ElectronicComponent;
import com.components.model.PassiveComponent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    private final List<ElectronicComponent> components = new ArrayList<>();

    // ===== PASSIVE =====

    public PassiveComponent addPassive(PassiveComponent c) {
        if (findById(c.getId()) != null)
            throw new IllegalArgumentException("Ya existe un componente con ID " + c.getId());
        c.setRegistrationDate(LocalDateTime.now());
        components.add(c);
        return c;
    }

    public PassiveComponent findPassiveById(int id) {
        ElectronicComponent c = findById(id);
        if (c instanceof PassiveComponent) return (PassiveComponent) c;
        return null;
    }

    public List<PassiveComponent> findAllPassive() {
        return components.stream()
                .filter(c -> c instanceof PassiveComponent)
                .map(c -> (PassiveComponent) c)
                .collect(Collectors.toList());
    }

    public List<PassiveComponent> findPassiveByFilters(String brand, String packageType) {
        return findAllPassive().stream()
                .filter(c -> brand == null || c.getBrand().equalsIgnoreCase(brand))
                .filter(c -> packageType == null || c.getPackageType().equalsIgnoreCase(packageType))
                .collect(Collectors.toList());
    }

    public PassiveComponent updatePassive(int id, PassiveComponent updated) {
        PassiveComponent existing = findPassiveById(id);
        if (existing == null) return null;
        existing.setBrand(updated.getBrand());
        existing.setPackageType(updated.getPackageType());
        existing.setVoltage(updated.getVoltage());
        existing.setCurrent(updated.getCurrent());
        existing.setPinCount(updated.getPinCount());
        existing.setTolerance(updated.getTolerance());
        existing.setNominalMagnitude(updated.getNominalMagnitude());
        existing.setNominalUnit(updated.getNominalUnit());
        return existing;
    }

    public boolean deletePassive(int id) {
        PassiveComponent c = findPassiveById(id);
        if (c == null) return false;
        components.remove(c);
        return true;
    }

    // ===== ACTIVE =====

    public ActiveComponent addActive(ActiveComponent c) {
        if (findById(c.getId()) != null)
            throw new IllegalArgumentException("Ya existe un componente con ID " + c.getId());
        c.setRegistrationDate(LocalDateTime.now());
        components.add(c);
        return c;
    }

    public ActiveComponent findActiveById(int id) {
        ElectronicComponent c = findById(id);
        if (c instanceof ActiveComponent) return (ActiveComponent) c;
        return null;
    }

    public List<ActiveComponent> findAllActive() {
        return components.stream()
                .filter(c -> c instanceof ActiveComponent)
                .map(c -> (ActiveComponent) c)
                .collect(Collectors.toList());
    }

    public List<ActiveComponent> findActiveByFilters(String brand, String packageType) {
        return findAllActive().stream()
                .filter(c -> brand == null || c.getBrand().equalsIgnoreCase(brand))
                .filter(c -> packageType == null || c.getPackageType().equalsIgnoreCase(packageType))
                .collect(Collectors.toList());
    }

    public ActiveComponent updateActive(int id, ActiveComponent updated) {
        ActiveComponent existing = findActiveById(id);
        if (existing == null) return null;
        existing.setBrand(updated.getBrand());
        existing.setPackageType(updated.getPackageType());
        existing.setVoltage(updated.getVoltage());
        existing.setCurrent(updated.getCurrent());
        existing.setGainFactor(updated.getGainFactor());
        existing.setPinNames(updated.getPinNames());
        return existing;
    }

    public boolean deleteActive(int id) {
        ActiveComponent c = findActiveById(id);
        if (c == null) return false;
        components.remove(c);
        return true;
    }

    // ===== UTIL =====

    private ElectronicComponent findById(int id) {
        return components.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
}
