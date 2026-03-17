package com.mycompany.electroniccomponentsproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.ActiveComponent;
import model.ElectronicComponent;
import model.PassiveComponent;

import observer.ComponentObserver;
import history.MeasurementRecord;

public class ElectronicComponentService implements IElectronicComponentService {

    // ================= SINGLETON =================

    private static ElectronicComponentService instance;

    private ElectronicComponentService() {}

    public static ElectronicComponentService getInstance() {
        if (instance == null) {
            instance = new ElectronicComponentService();
        }
        return instance;
    }

    // ================= ATRIBUTOS =================

    private final List<ElectronicComponent> components = new ArrayList<>();
    private final List<ComponentObserver> observers = new ArrayList<>();
    private final List<MeasurementRecord> history = new ArrayList<>();

    // ================= OBSERVER MANAGEMENT =================

    @Override
    public void addObserver(ComponentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ComponentObserver observer) {
        observers.remove(observer);
    }

    private void notifyComponentAdded(ElectronicComponent component) {
        observers.forEach(o -> o.onComponentAdded(component));
    }

    private void notifyComponentDeleted(int id) {
        observers.forEach(o -> o.onComponentDeleted(id));
    }

    // ================= CRUD =================

    @Override
    public ElectronicComponent add(ElectronicComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }
        if (findComponentById(component.getId()) != null) {
            throw new IllegalArgumentException("Component with this ID already exists");
        }
        components.add(component);
        notifyComponentAdded(component);
        return component;
    }

    @Override
    public ElectronicComponent findComponentById(int id) {
        return components.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public PassiveComponent findPassiveById(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component == null) return null;
        if (component instanceof PassiveComponent) return (PassiveComponent) component;
        throw new IllegalArgumentException("Component found but is not Passive");
    }

    @Override
    public ActiveComponent findActiveById(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component == null) return null;
        if (component instanceof ActiveComponent) return (ActiveComponent) component;
        throw new IllegalArgumentException("Component found but is not Active");
    }

    @Override
    public boolean deleteComponent(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component == null) return false;
        boolean removed = components.remove(component);
        if (removed) notifyComponentDeleted(id);
        return removed;
    }

    @Override
    public boolean deleteById(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component != null) {
            components.remove(component);
            notifyComponentDeleted(id);
            return true;
        }
        return false;
    }

    @Override
    public <T extends ElectronicComponent> List<T> findComponentByType(Class<T> targetClass) {
        return components.stream()
                .filter(targetClass::isInstance)
                .map(targetClass::cast)
                .collect(Collectors.toList());
    }

    // ================= POLYMORPHIC METHODS =================

    @Override
    public double calculateImpedanceById(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component == null) throw new IllegalArgumentException("Component not found");
        double result = component.calculateImpedance();
        history.add(new MeasurementRecord(id, "Impedance", result));
        return result;
    }

    @Override
    public double calculatePowerById(int id) {
        ElectronicComponent component = findComponentById(id);
        if (component == null) throw new IllegalArgumentException("Component not found");
        double result = component.calculatePower();
        history.add(new MeasurementRecord(id, "Power", result));
        return result;
    }

    // ================= HISTORY =================

    public List<MeasurementRecord> getHistory() {
        return history;
    }
}
