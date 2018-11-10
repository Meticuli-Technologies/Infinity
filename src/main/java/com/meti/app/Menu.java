package com.meti.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Menu extends Controller {
    @FXML
    public void connectToAServer(){

    }

    @FXML
    public void hostALocalServer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meti/app/HostALocalServer.fxml"));
            stage.setScene(new Scene(loader.load()));

            HostALocalServer hostALocalServer = loader.getController();
            hostALocalServer.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void hostAServer(){

    }
}
