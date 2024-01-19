package com.example.hotwheelstracker.Utils.Observer;


import com.example.hotwheelstracker.Utils.Events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}