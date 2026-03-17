package com.mycompany.electroniccomponentsproject.service;

import java.util.List;

import model.ActiveComponent;
import model.ElectronicComponent;
import model.PassiveComponent;
import observer.ComponentObserver;

public interface IElectronicComponentService {

    ElectronicComponent add(ElectronicComponent component);

    ElectronicComponent findComponentById(int id);

    PassiveComponent findPassiveById(int id);

    ActiveComponent findActiveById(int id);

    boolean deleteComponent(int id);

    boolean deleteById(int id);

    <T extends ElectronicComponent> List<T> findComponentByType(Class<T> targetClass);

    double calculateImpedanceById(int id);

    double calculatePowerById(int id);

    void addObserver(ComponentObserver observer);

    void removeObserver(ComponentObserver observer);
}
