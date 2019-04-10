package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.FXMLBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    FXMLBundle<Local> loadLocal() throws IOException {
        return onto(URLS.getLocalURL().openStream());
    }

    @FXML
    public Optional<Exception> local() {
        return factory.accept(this::loadLocal).get();
    }

    @FXML
    public void openSettings() {

    }
}
