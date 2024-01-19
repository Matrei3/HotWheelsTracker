package com.example.hotwheelstracker;

import com.example.hotwheelstracker.Controller.CarController;
import com.example.hotwheelstracker.Controller.HomePageController;
import com.example.hotwheelstracker.Controller.UserCarsController;
import com.example.hotwheelstracker.Domain.Car;
import com.example.hotwheelstracker.Repository.CarDatabaseRepository;
import com.example.hotwheelstracker.Repository.FileRepository;
import com.example.hotwheelstracker.Repository.Repository;
import com.example.hotwheelstracker.Repository.UserCarsFileRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        HomePageController controller = fxmlLoader.getController();
        String[] parameters = getParameters().getRaw().toArray(new String[0]);
        Repository<Integer,Car> carRepository = new CarDatabaseRepository(parameters[0],parameters[1],parameters[2]);
        FileRepository<Integer, Car> fileRepository = new UserCarsFileRepository("src/main/java/com/example/hotwheelstracker/Data/user-cars.csv");
        UserCarsController userCarsController = new UserCarsController(fileRepository);
        CarController carController = new CarController(carRepository);
        controller.setCarController(carController,userCarsController);
        stage.setTitle("Hot Wheels Tracker");
        stage.getIcons().add(new Image("com/example/hotwheelstracker/images/logo.png"));
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
        //not a great idea to load 9950 pictures into the project (waited 10 minutes for compile just for it to crash)
    }
}