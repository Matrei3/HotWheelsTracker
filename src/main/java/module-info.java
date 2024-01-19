module com.example.hotwheelstracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.jsoup;
    requires java.sql;
    requires com.jfoenix;
    requires java.desktop;

    opens com.example.hotwheelstracker to javafx.fxml;
    exports com.example.hotwheelstracker;
    exports com.example.hotwheelstracker.Controller;
    exports com.example.hotwheelstracker.Repository;
    exports com.example.hotwheelstracker.Domain;
    exports com.example.hotwheelstracker.Utils.Observer;
    exports com.example.hotwheelstracker.Utils.Events;
    opens com.example.hotwheelstracker.Controller to javafx.fxml;


}