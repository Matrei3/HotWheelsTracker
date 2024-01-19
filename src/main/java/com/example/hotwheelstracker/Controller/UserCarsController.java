package com.example.hotwheelstracker.Controller;

import com.example.hotwheelstracker.Domain.Car;
import com.example.hotwheelstracker.Repository.FileRepository;
import com.example.hotwheelstracker.Repository.UserCarsFileRepository;
import com.example.hotwheelstracker.Utils.Events.CarChangeEvent;
import com.example.hotwheelstracker.Utils.Events.ChangeEventType;
import com.example.hotwheelstracker.Utils.Observer.Observable;
import com.example.hotwheelstracker.Utils.Observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserCarsController implements Observable<CarChangeEvent> {
    private final FileRepository<Integer, Car> carFileRepository;
    private final List<Observer<CarChangeEvent>> observers = new ArrayList<>();
    public UserCarsController(FileRepository<Integer,Car> carFileRepository) {
        this.carFileRepository = carFileRepository;
    }
    public Car addCar(Car entity){
        Optional<Car> optional = carFileRepository.save(entity);
        notifyObservers(new CarChangeEvent(ChangeEventType.ADD,entity));
        return optional.orElse(null);
    }
    public Car deleteCar(Car entity){
        Optional<Car> optional = carFileRepository.delete(entity.getId());
        notifyObservers(new CarChangeEvent(ChangeEventType.DELETE,entity));
        return optional.orElse(null);
    }
    public List<Car> allCars(){
        return carFileRepository.findAll();
    }

    @Override
    public void addObserver(Observer<CarChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<CarChangeEvent> e) {
    }

    @Override
    public void notifyObservers(CarChangeEvent event) {
        observers.forEach(x -> x.update(event));
    }

    public List<Car> filterByName(String nameToFilter) {
            String capitalizedNameToFilter = Pattern.compile("^.").matcher(nameToFilter).replaceFirst(m -> m.group().toUpperCase());//Cars have uppercase first letters, so make the string have uppercase words
            return carFileRepository.findSome(capitalizedNameToFilter);
        }
    }

