package com.meti.app.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Alert {
    @FXML
    private Text messageText;

    public static Stage create(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getAlertResource());
        Stage stage = buildStage(loader.load());
        Alert controller = loader.getController();
        controller.setMessage(message);
        return stage;
    }

    private static Stage buildStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        return stage;
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }

    private Alert setMessage(String message) {
        messageText.setText(message);
        return this;
    }
}
