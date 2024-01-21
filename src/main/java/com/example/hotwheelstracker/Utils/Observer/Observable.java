package com.example.hotwheelstracker.Utils.Observer;


import com.example.hotwheelstracker.Utils.Events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
