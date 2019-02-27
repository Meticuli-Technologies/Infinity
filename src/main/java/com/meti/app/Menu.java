package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Menu extends Controller {
    public Menu(State state) {
        super(state);
    }

    @FXML
    public void connect() {
        try {
            Stage stage = state.byClassToSingle(Stage.class).orElse(new Stage());
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/ClientMenu.fxml"))));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void host() {
        try {
            Stage stage = state.byClassToSingle(Stage.class).orElse(new Stage());
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/ServerMenu.fxml"))));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
