package com.example.hotwheelstracker.Repository;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class ImageRetriever {

    public static BufferedImage imageRetriever(String url) throws IOException {

        //String destinationFile = "resources/carImages/" + url;
        if (url.contains("/wiki/"))//Some urls are broken
            url = "https://static.wikia.nocookie.net/hotwheels/images/b/b5/Image_Not_Available.jpg/revision/latest?cb=20151025125428";
        return saveImage(url);
    }

    private static BufferedImage saveImage(String imageUrl) throws IOException {
        BufferedImage image;
        URL url = new URL(imageUrl);
        image = ImageIO.read(url);
        return image;


    }
}
