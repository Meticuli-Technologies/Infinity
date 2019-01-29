package com.meti.app;

import com.meti.lib.console.Console;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Menu extends Controller {
    @FXML
    private Text versionText;

    @Override
    public void confirm() {
        Properties properties = state.get().singleContent(Properties.class);
        String version = properties.getProperty("version");
        versionText.setText("Version: " + version);
    }

    @FXML
    public void openConnectionManager() {
        try {
            onto(getClass().getResource("/com/meti/app/ConnectionManager.fxml"));
        } catch (IOException e) {
            state.get().singleContent(Console.class).log(Level.WARNING, e);
        }
    }

    @FXML
    public void hostAServer() {

    }

    @FXML
    public void useLocally() {

    }

    @FXML
    public void openSettings() {
    }
}
