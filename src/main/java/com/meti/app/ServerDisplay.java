package com.meti.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/23/2019
 */
public class ServerDisplay implements Initializable {
    @FXML
    private BorderPane contentPane;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    private Consumer<ActionEvent> backConsumer;
    private Consumer<ActionEvent> nextConsumer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMenu() throws IOException {
        backConsumer = event -> Platform.exit();
        backButton.setText("Exit");

        contentPane.setCenter(FXMLLoader.load(getClass().getResource("/com/meti/app/ServerMenu.fxml")));
        nextConsumer = event -> loadConsole();
    }

    private void loadConsole(){
        backConsumer = event -> {
            try {
                loadMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        backButton.setText("Back");
    }

    @FXML
    public void back(ActionEvent event) {
        Optional<Consumer<ActionEvent>> optional = getBackConsumer();
        if (optional.isPresent()) {
            optional.get().accept(event);
        } else {
            throw new IllegalStateException("Back consumer has not been set");
        }
    }

    private Optional<Consumer<ActionEvent>> getBackConsumer() {
        return Optional.ofNullable(backConsumer);
    }

    @FXML
    public void next(ActionEvent event) {
        Optional<Consumer<ActionEvent>> optional = getNextConsumer();
        if (optional.isPresent()) {
            optional.get().accept(event);
        } else {
            throw new IllegalStateException("Next consumer has not been set");
        }
    }

    private Optional<Consumer<ActionEvent>> getNextConsumer() {
        return Optional.ofNullable(nextConsumer);
    }
}
