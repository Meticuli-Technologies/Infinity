package com.meti.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Infinity {
    private InfinityState state;

    public Infinity() {
    }

    public void start(Stage primaryStage) {


        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/Display.fxml"))));
            primaryStage.show();
        } catch (IOException e) {

            //TODO: handle exceptions
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}