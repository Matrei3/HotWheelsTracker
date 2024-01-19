package com.example.hotwheelstracker.Domain;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Car extends Entity<Integer>{
    private Integer year;
    private Integer collectionNumber;
    private String modelName;
    private String seriesBackgroundColor;
    private String series;
    private String seriesNumber;
    private Hyperlink imageLink;
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Car(Integer year, Integer collectionNumber, String modelName, String seriesBackgroundColor, String series, String seriesNumber, Hyperlink imageLink) {
        this.year = year;
        this.collectionNumber = collectionNumber;
        this.modelName = modelName;
        this.seriesBackgroundColor = seriesBackgroundColor;
        this.series = series;
        this.seriesNumber = seriesNumber;
        this.imageLink = imageLink;
    }

    public Car(Integer year, String modelName, String series, Hyperlink imageLink) {
        this.year = year;
        this.modelName = modelName;
        this.series = series;
        this.imageLink = imageLink;
    }

    public String getSeriesBackgroundColor() {
        return seriesBackgroundColor;
    }

    public void setSeriesBackgroundColor(String seriesBackgroundColor) {
        this.seriesBackgroundColor = seriesBackgroundColor;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(Integer collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public Hyperlink getImageLink() {
        return imageLink;
    }

    public void setImageLink(Hyperlink imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Car{" +
                "year=" + year +
                ", collectionNumber=" + collectionNumber +
                ", modelName='" + modelName + '\'' +
                ", series='" + series + '\'' +
                ", seriesNumber='" + seriesNumber + '\'' +
                ", image=" + imageLink +
                '}';
    }
}
