package com.meti.core;

import com.meti.core.load.PropertiesLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class Infinity {
    private final StateImpl stateImpl = new InfinityState();

    public void start(Stage primaryStage) {
        try {
            Properties properties = new PropertiesLoader().load();
            stateImpl.add(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}