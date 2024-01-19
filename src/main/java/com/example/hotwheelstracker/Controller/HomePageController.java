package com.example.hotwheelstracker.Controller;

import com.example.hotwheelstracker.Domain.Car;
import com.example.hotwheelstracker.Utils.Events.CarChangeEvent;
import com.example.hotwheelstracker.Utils.Observer.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HomePageController implements Observer<CarChangeEvent> {

    CarController carController;
    UserCarsController userCarsController;
    ObservableList<Car> model = FXCollections.observableArrayList();
    ObservableList<Car> userCarsModel = FXCollections.observableArrayList();

    @FXML
    private TextField carFilterer;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField searchCarField;
    @FXML
    private TableView<Car> carsTable;
    @FXML
    private TableView<Car> userCarsTable;
    @FXML
    private TableColumn<Car, Integer> tableColumnYear;
    @FXML
    private TableColumn<Car, Integer> tableColumnYearUser;
    @FXML
    private TableColumn<Car, String> tableColumnModelName;
    @FXML
    private TableColumn<Car, String> tableColumnModelNameUser;
    @FXML
    private TableColumn<Car, String> tableColumnSeries;
    @FXML
    private TableColumn<Car, String> tableColumnSeriesUser;
    @FXML
    private TableColumn<Car, Hyperlink> tableColumnImage;
    @FXML
    private TableColumn<Car, Hyperlink> tableColumnImageUser;
    @FXML
    private TableColumn<Car, String> tableColumnSeriesNumber;
    @FXML
    private TableColumn<Car, String> tableColumnSeriesNumberUser;

    @FXML
    public void initialize() {
        carFilterer.textProperty().addListener(e -> loadCars());
        searchCarField.textProperty().addListener(e -> loadUserCars());
        setColumns(tableColumnYear, tableColumnModelName, tableColumnSeries, tableColumnImage, tableColumnSeriesNumber);
        setColumns(tableColumnYearUser, tableColumnModelNameUser, tableColumnSeriesUser, tableColumnImageUser, tableColumnSeriesNumberUser);
        //if I press the hyperlink open a web page
        tableColumnImage.setCellFactory(cell -> {
            return new TableCell<Car,Hyperlink>(){
              @Override
              protected void updateItem(Hyperlink item, boolean empty){
                  super.updateItem(item,empty);
                  if(item == null || empty){
                      setGraphic(null);
                  }
                  else {
                      item.setOnAction(event -> {
                          try {
                              Desktop.getDesktop().browse(new URI(item.getText()));
                          } catch (IOException | URISyntaxException e) {
                              e.printStackTrace();
                          }
                      });
                      setGraphic(item);
                  }
              }
            };
        });
        tableColumnImageUser.setCellFactory(cell -> {
            return new TableCell<Car,Hyperlink>(){
              @Override
              protected void updateItem(Hyperlink item, boolean empty){
                  super.updateItem(item,empty);
                  if(item == null || empty){
                      setGraphic(null);
                  }
                  else {
                      item.setOnAction(event -> {
                          try {
                              Desktop.getDesktop().browse(new URI(item.getText()));
                          } catch (IOException | URISyntaxException e) {
                              e.printStackTrace();
                          }
                      });
                      setGraphic(item);
                  }
              }
            };
        });
        tableColumnYear.setMaxWidth(1f * Integer.MAX_VALUE * 5); // 5% width
        tableColumnModelName.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30% width
        tableColumnSeries.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30% width
        tableColumnSeriesNumber.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 10% width
        tableColumnImage.setMaxWidth(1f * Integer.MAX_VALUE * 45); // 45% width
        tableColumnYearUser.setMaxWidth(1f * Integer.MAX_VALUE * 5); // 5% width

        tableColumnModelNameUser.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 30% width
        tableColumnSeriesUser.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 30% width
        tableColumnSeriesNumberUser.setMaxWidth(1f * Integer.MAX_VALUE * 5); // 10% width
        tableColumnImageUser.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 45% width
        carsTable.setItems(model);
        userCarsTable.setItems(userCarsModel);

    }

    private void setColumns(TableColumn<Car, Integer> tableColumnYearUser, TableColumn<Car, String> tableColumnModelNameUser, TableColumn<Car, String> tableColumnSeriesUser, TableColumn<Car, Hyperlink> tableColumnImageUser, TableColumn<Car, String> tableColumnSeriesNumberUser) {
        tableColumnYearUser.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableColumnModelNameUser.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        tableColumnSeriesUser.setCellValueFactory(new PropertyValueFactory<>("series"));
        tableColumnImageUser.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        tableColumnSeriesNumberUser.setCellValueFactory(new PropertyValueFactory<>("seriesNumber"));
    }

    public void setCarController(CarController controller, UserCarsController userCarController) {
        this.carController = controller;
        this.userCarsController = userCarController;
        userCarController.addObserver(this);
        List<Car> userCars = userCarsController.allCars();
        userCarsModel.setAll(userCars);
        List<Car> cars = carController.allCars();
        model.setAll(cars);
        carsTable.requestFocus();
    }

    private void loadCars() {
        String textToFilterBy = carFilterer.getText();
        List<Car> cars = carController.filterCarsByName(textToFilterBy);
        model.setAll(cars);
    }
    private void loadUserCars(){
        String textToFilterBy = searchCarField.getText();
        List<Car> userCars = userCarsController.filterByName(textToFilterBy);
        userCarsModel.setAll(userCars);
    }
    public void addCarToCollection(ActionEvent actionEvent) {
        Car selectedCar = carsTable.getSelectionModel().getSelectedItem();
        if(selectedCar!=null) {
            userCarsController.addCar(selectedCar);
            warningLabel.setVisible(false);
        }
        else{
            warningLabel.setText("No car selected for adding monsieur!");
            warningLabel.setVisible(true);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2.5), event -> warningLabel.setVisible(false)));
            timeline.play();

        }
    }

    public void deleteCarFromCollection(ActionEvent actionEvent) {
        Car selectedCar = userCarsTable.getSelectionModel().getSelectedItem();
        if(selectedCar!=null) {
            userCarsController.deleteCar(selectedCar);
            warningLabel.setVisible(false);
        }
        else{

            warningLabel.setText("No car selected for deleting monsieur!");
            warningLabel.setVisible(true);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2.5), event -> warningLabel.setVisible(false)));
            timeline.play();
        }
    }

    @Override
    public void update(CarChangeEvent carChangeEvent) {
        loadUserCars();
    }
}
