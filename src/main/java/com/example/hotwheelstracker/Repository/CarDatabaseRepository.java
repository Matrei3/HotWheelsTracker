package com.example.hotwheelstracker.Repository;

import com.example.hotwheelstracker.Domain.Car;
import javafx.scene.control.Hyperlink;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CarDatabaseRepository implements Repository<Integer, Car> {
    protected final String url;
    protected final String username;
    protected final String password;

    public CarDatabaseRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Car> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement findStatement = connection.prepareStatement("select * from cars")
        ) {
            ResultSet resultSet = findStatement.executeQuery();
            HashMap<Integer,Car> carsWithSpecificName = new HashMap<>();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id_car");
                Integer car_year = resultSet.getInt("car_year");
                Integer collection_number = resultSet.getInt("collection_number");
                String model_name = resultSet.getString("model_name");
                String seriesBG = resultSet.getString("series_bg_color");
                String series = resultSet.getString("series");
                String series_number = resultSet.getString("series_number");
                String imageLink = resultSet.getString("image_link");
                Hyperlink hyperlink = new Hyperlink(imageLink);
                Car car = new Car(car_year,collection_number,model_name,seriesBG,series,series_number,hyperlink);
                car.setId(id);
                carsWithSpecificName.put(id,car);
            }
            return carsWithSpecificName.values().stream().toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Car> save(Car entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement insertStatement = connection.prepareStatement("insert into cars(car_year,collection_number,model_name,series_bg_color,series,series_number,image_link) values (?,?,?,?,?,?,?)")
        ) {
            insertStatement.setInt(1, entity.getYear());
            insertStatement.setInt(2, entity.getCollectionNumber());
            insertStatement.setString(3, entity.getModelName());
            insertStatement.setString(4, entity.getSeriesBackgroundColor());
            insertStatement.setString(5, entity.getSeries());
            insertStatement.setString(6, entity.getSeriesNumber());
            insertStatement.setString(7, entity.getImageLink().toString());
            if (insertStatement.executeUpdate() < 1)
                return Optional.empty();
            else
                return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Car> delete(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Car> update(Car entity) {
        return Optional.empty();
    }

    @Override
    public List<Car> findSome(String name) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement findStatement = connection.prepareStatement("select * from cars where model_name like CONCAT( '%',?,'%')")
        ) {
            findStatement.setString(1,name);
            ResultSet resultSet = findStatement.executeQuery();
            HashMap<Integer,Car> carsWithSpecificName = new HashMap<>();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id_car");
                Integer car_year = resultSet.getInt("car_year");
                String model_name = resultSet.getString("model_name");
                String series = resultSet.getString("series");
                String imageLink = resultSet.getString("image_link");
                String seriesBG = resultSet.getString("series_bg_color");
                String series_number = resultSet.getString("series_number");
                if(series_number==null)
                    series_number = "No series";
                Hyperlink hyperlink = new Hyperlink(imageLink);
                Car car = new Car(car_year,model_name,series,hyperlink);
                car.setId(id);
                car.setSeriesNumber(series_number);
                car.setSeriesBackgroundColor(seriesBG);
                carsWithSpecificName.put(id,car);
            }
            return carsWithSpecificName.values().stream().toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
