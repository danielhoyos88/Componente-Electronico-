package com.components.observer;

import java.util.List;

public class ComponentListLogger implements IComponentObserver {
    @Override
    public void onListRequested(String type, List<?> results) {
        System.out.println("[OBSERVADOR] Lista solicitada -> type: " + type + " | count: " + results.size());
    }
}
