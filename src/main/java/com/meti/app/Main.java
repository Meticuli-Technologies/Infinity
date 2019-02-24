package com.meti.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();

        Text welcomeText = new Text("Welcome to Infinity!");
        grid.add(welcomeText, 0, 0);
        GridPane.setColumnSpan(welcomeText, 2);

        Button connectButton = new Button("Connect to a Server");
        grid.add(connectButton, 0, 1);

        Button createButton = new Button("Create a Server");
        grid.add(createButton, 1, 1);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(grid);

        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
