package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Wizard;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Optional;
import java.util.Properties;

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
    public void findAConnection() {
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

    @Override
    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.of(ConnectionFinderWizard.class);
    }
}
