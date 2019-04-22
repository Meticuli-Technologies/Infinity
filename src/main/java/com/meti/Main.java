package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.reflect.Method;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Main extends Application  {
    public static void main(String[] args) {
        try {
            Method launchMethod = Application.class.getMethod("launch", String[].class);
            launchMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
