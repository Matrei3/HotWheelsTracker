package com.example.hotwheelstracker.Controller;

import com.example.hotwheelstracker.Domain.Car;
import com.example.hotwheelstracker.Repository.Repository;

import java.util.List;
import java.util.regex.Pattern;

public class CarController {
    private final Repository<Integer, Car> carRepository;

    public CarController(Repository<Integer, Car> carRepository) {
        this.carRepository = carRepository;
    }
    public List<Car> filterCarsByName(String nameToFilter){
        String capitalizedNameToFilter = Pattern.compile("^.").matcher(nameToFilter).replaceFirst(m -> m.group().toUpperCase());//Cars have uppercase first letters, so make the string have uppercase words
        return carRepository.findSome(capitalizedNameToFilter);
    }
    public List<Car> allCars(){
        return carRepository.findAll();
    }
}
