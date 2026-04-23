package com.components.observer;

import java.util.List;

public interface IComponentObserver {
    void onListRequested(String type, List<?> results);
}
