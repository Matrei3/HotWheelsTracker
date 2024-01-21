package com.example.hotwheelstracker.Repository;

import com.example.hotwheelstracker.Domain.Entity;

public abstract class FileRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

}
