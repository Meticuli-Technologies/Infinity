package com.meti.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        GridPane.setRowSpan(welcomeText, 2);
        center(welcomeText);

        Button connectButton = new Button("Connect to a Server");
        grid.add(connectButton, 0, 1);
        center(connectButton);

        Button createButton = new Button("Create a Server");
        grid.add(createButton, 1, 1);
        center(createButton);

        createButton.setAlignment(Pos.CENTER);

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

    public void center(Node node) {
        GridPane.setValignment(node, VPos.CENTER);
        GridPane.setHalignment(node, HPos.CENTER);
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
