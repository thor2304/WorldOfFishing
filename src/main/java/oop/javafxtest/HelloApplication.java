package oop.javafxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import oop.javafxtest.worldofzuul.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("WorldOfFishing: The Game!");
        stage.setScene(scene);
        String pathBoat = "src/main/resources/oop/javafxtest/images/Boat.png";
        File boatFile = new File(pathBoat);
        Image boatImage = new Image(new FileInputStream(boatFile));
        stage.getIcons().add(boatImage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}