package com.example.hotwheelstracker.Repository;

import com.example.hotwheelstracker.Domain.Car;
import javafx.scene.control.Hyperlink;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class UserCarsFileRepository extends FileRepository<Integer, Car> {
    public UserCarsFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    public Optional<Car> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] attr = data.split(",");
                Integer id = Integer.parseInt(attr[0]);
                Integer year = Integer.parseInt(attr[1]);
                Integer colNumber = null;
                if (!Objects.equals(attr[2], "null")) {

                    colNumber = Integer.parseInt(attr[2]);
                }
                String modelName = attr[3];
                String seriesBGcolor = attr[4];
                String series = attr[5];
                String seriesNumber = attr[6];
                Hyperlink hyperlink = new Hyperlink(attr[7]);
                Car car = new Car(year, colNumber, modelName, seriesBGcolor, series, seriesNumber, hyperlink);
                car.setId(id);
                cars.add(car);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public Optional<Car> save(Car entity) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)
        ) {

            out.println(entity.getId() + "," + entity.getYear() + "," + entity.getCollectionNumber() + "," + entity.getModelName() + "," + entity.getSeriesBackgroundColor() + "," + entity.getSeries() + "," + entity.getSeriesNumber() + "," + entity.getImageLink().getText());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Car> delete(Integer carId) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            lines = lines.stream().filter(line -> Integer.parseInt(line.split(",")[0]) != carId).collect(Collectors.toList());
            Files.write(Path.of(filePath), lines);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();

    }

    @Override
    public Optional<Car> update(Car entity) {
        return Optional.empty();
    }

    @Override
    public List<Car> findSome(String name) {
        List<Car> cars = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            lines = lines.stream().filter(line -> line.split(",")[3].contains(name)).collect(Collectors.toList());
            lines.forEach(line -> {
                String[] attr = line.split(",");
                Integer id = Integer.parseInt(attr[0]);
                Integer year = Integer.parseInt(attr[1]);
                Integer colNumber = null;
                if (!Objects.equals(attr[2], "null")) {

                    colNumber = Integer.parseInt(attr[2]);
                }
                String modelName = attr[3];
                String seriesBGcolor = attr[4];
                String series = attr[5];
                String seriesNumber = attr[6];
                Hyperlink hyperlink = new Hyperlink(attr[7]);
                Car car = new Car(year, colNumber, modelName, seriesBGcolor, series, seriesNumber, hyperlink);
                car.setId(id);
                cars.add(car);
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    @Override
    public int size() {
        return 0;
    }
}
