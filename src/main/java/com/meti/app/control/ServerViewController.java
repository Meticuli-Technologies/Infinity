package com.meti.app.control;

import com.meti.app.main.ClientMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ServerViewController {
    @FXML
    public void addServer(){
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/fxml/wizard/AddServer.fxml"))));
            stage.show();
        } catch (IOException e) {
            ClientMain.logger.error("Failed to load fxml", e);
        }
    }
}
