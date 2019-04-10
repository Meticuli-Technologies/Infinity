package com.meti.app;

import com.meti.lib.collect.State;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class Menu extends InfinityController {
    @FXML
    private Text versionText;

    public Menu(State state, Stage stage) {
        super(state, stage);
    }

    Local loadLocal() throws IOException {
        return onto(URLS.getLocalURL().openStream());
    }

    @FXML
    public void local() {
        factory.accept(this::loadLocal);
    }

    @FXML
    public void openSettings() {

    }
}
