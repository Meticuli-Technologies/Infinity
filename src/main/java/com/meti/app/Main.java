package com.meti.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        zeroConstraints(grid);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().add(rowConstraints);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().add(columnConstraints);

        Text welcomeText = new Text("Welcome to Infinity!");
        grid.add(welcomeText, 0, 0);
        GridPane.setColumnSpan(welcomeText, 2);

        Button connectButton = new Button("Connect to a Server");
        grid.add(connectButton, 0, 1);

        Button createButton = new Button("Create a Server");
        grid.add(createButton, 1, 1);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(grid);

        Stage stage = new Stage();
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.setScene(new Scene(root));
        stage.showAndWait();

        primaryStage.show();
        primaryStage.close();

        Platform.exit();
    }

    public void zeroConstraints(GridPane grid) {
        AnchorPane.setTopAnchor(grid, 0d);
        AnchorPane.setBottomAnchor(grid, 0d);
        AnchorPane.setLeftAnchor(grid, 0d);
        AnchorPane.setRightAnchor(grid, 0d);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
