package com.example.hotwheelstracker.Utils.Events;


import com.example.hotwheelstracker.Domain.Car;

public class CarChangeEvent implements Event {
    private ChangeEventType type;
    private final Car data;
    private Car oldData;

    public CarChangeEvent(ChangeEventType type, Car data) {
        this.type = type;
        this.data = data;
    }

    public CarChangeEvent(ChangeEventType type, Car data, Car oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Car getData() {
        return data;
    }

    public Car getOldData() {
        return oldData;
    }
}