package com.components.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.components.model.ActiveComponent;
import com.components.model.ElectronicComponent;
import com.components.model.PassiveComponent;
import com.components.observer.IComponentObserver;

@Service
public class ComponentService {

    private static ComponentService instance;

    private final List<ElectronicComponent> components = new ArrayList<>();
    private final List<IComponentObserver> observers = new ArrayList<>();

    // Singleton
    public static ComponentService getInstance() {
        if (instance == null) instance = new ComponentService();
        return instance;
    }

    // Observer management
    public void addObserver(IComponentObserver o) { observers.add(o); }
    public void removeObserver(IComponentObserver o) { observers.remove(o); }
    private void notifyList(String type, List<?> results) {
        observers.forEach(o -> o.onListRequested(type, results));
    }

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
        return c instanceof PassiveComponent ? (PassiveComponent) c : null;
    }

    public PassiveComponent findPassiveByBrand(String brand) {
        return components.stream()
                .filter(c -> c instanceof PassiveComponent && c.getBrand().equalsIgnoreCase(brand))
                .map(c -> (PassiveComponent) c)
                .findFirst().orElse(null);
    }

    public List<PassiveComponent> findAllPassive() {
        List<PassiveComponent> result = components.stream()
                .filter(c -> c instanceof PassiveComponent)
                .map(c -> (PassiveComponent) c)
                .collect(Collectors.toList());
        notifyList("PASSIVE", result);
        return result;
    }

    public List<PassiveComponent> findPassiveByFilters(String brand, String packageType) {
        List<PassiveComponent> result = findAllPassive().stream()
                .filter(c -> brand == null || c.getBrand().equalsIgnoreCase(brand))
                .filter(c -> packageType == null || c.getPackageType().equalsIgnoreCase(packageType))
                .collect(Collectors.toList());
        notifyList("PASSIVE_FILTERED", result);
        return result;
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
        return c instanceof ActiveComponent ? (ActiveComponent) c : null;
    }

    public ActiveComponent findActiveByBrand(String brand) {
        return components.stream()
                .filter(c -> c instanceof ActiveComponent && c.getBrand().equalsIgnoreCase(brand))
                .map(c -> (ActiveComponent) c)
                .findFirst().orElse(null);
    }

    public List<ActiveComponent> findAllActive() {
        List<ActiveComponent> result = components.stream()
                .filter(c -> c instanceof ActiveComponent)
                .map(c -> (ActiveComponent) c)
                .collect(Collectors.toList());
        notifyList("ACTIVE", result);
        return result;
    }

    public List<ActiveComponent> findActiveByFilters(String brand, String packageType) {
        List<ActiveComponent> result = findAllActive().stream()
                .filter(c -> brand == null || c.getBrand().equalsIgnoreCase(brand))
                .filter(c -> packageType == null || c.getPackageType().equalsIgnoreCase(packageType))
                .collect(Collectors.toList());
        notifyList("ACTIVE_FILTERED", result);
        return result;
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
