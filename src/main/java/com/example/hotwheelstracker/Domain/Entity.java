package com.example.hotwheelstracker.Domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
