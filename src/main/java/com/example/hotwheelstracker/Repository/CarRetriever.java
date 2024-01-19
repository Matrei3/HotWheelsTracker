package com.example.hotwheelstracker.Repository;

import com.example.hotwheelstracker.Domain.Car;
import javafx.scene.control.Hyperlink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * This class is used to get the Hot Wheels from the hotwheels.fandom.com site from 1995-current year
 */
public class CarRetriever {

    /**
     * @param yearToGetCars - year for which you want to retrieve the cars (currently supports >=1995)
     * @return all cars from yearToGetCars as a stream of Car objects
     */
    public static Iterable<Car> retrieveCars(Integer yearToGetCars) {
        Document doc;
        try {
            // fetching the target website
            doc = Jsoup.connect("https://hotwheels.fandom.com/wiki/List_of_" + yearToGetCars + "_Hot_Wheels").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements bodies = doc.select("tbody"); //each table containing cars
        List<Car> carsList = new ArrayList<>();
        bodies.remove(bodies.size() - 1); //contains references to other years/models/series which we do not need, so we discard it
        bodies.forEach(product -> {
            Elements tableRows = product.select("tr");//
            tableRows.remove(0); //we do not need to know the colors of column headers, so we discard it
            tableRows.forEach(row -> {
                Elements carFields = row.select("td");
                String collectionNumberString = carFields.get(1).text();
                int collectionNumberInt = -1;
                if (!collectionNumberString.isEmpty()) {
                    try {
                        Integer.parseInt(collectionNumberString);//sometimes it's not an int so we set it to -1
                        collectionNumberInt = Integer.parseInt(collectionNumberString);//if it is int
                    } catch (NumberFormatException ignored) {
                    }

                }
                String modelName = Objects.requireNonNull(carFields.get(2).selectFirst("a")).attr("title");//this also contains a link to the car, but we are only interested in the car name
                String seriesBGcolor = carFields.get(3).attr("bgcolor");//we get to bg color to make the GUI cute later
                String series = "";
                if (carFields.get(3).select("a").attr("title").isEmpty())
                    series = carFields.get(3).text();
                else
                    series = Objects.requireNonNull(carFields.get(3).selectFirst("a")).attr("title");//sometimes the series has a link. but we only care about the title


                String seriesNumber = carFields.get(4).text();//determines the order of the car in the series (e.g. 1/10 -> 1st car in a series of 10)
                String imageLink;
                if (carFields.size() == 6)
                    imageLink = Objects.requireNonNull(carFields.get(5).selectFirst("a")).attr("href");
                else {
                    if (!carFields.get(4).select("a").isEmpty())
                        imageLink = Objects.requireNonNull(carFields.get(4).selectFirst("a")).attr("href");//sometimes there is no series number so the fields are offset by 1
                    else
                        imageLink = "https://static.wikia.nocookie.net/hotwheels/images/b/b5/Image_Not_Available.jpg/revision/latest?cb=20151025125428";//image may be missing, so we replace it with a "missing image" link
                }
                Car car = new Car(yearToGetCars, collectionNumberInt, modelName, seriesBGcolor, series, seriesNumber, new Hyperlink(imageLink));
                carsList.add(car);
            });

        });
        return carsList;
    }
}
