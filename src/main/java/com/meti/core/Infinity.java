package com.meti.core;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Infinity {
    public Infinity() {
    }

    public void start(Stage primaryStage) {
        try {
            Path propertiesPath = Paths.get(".\\Infinity.properties");
            if(!Files.exists(propertiesPath)){
                Files.createFile(propertiesPath);
            }

            InputStream inStream = Files.newInputStream(propertiesPath);
            Properties properties = new Properties();
            properties.load(inStream);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}