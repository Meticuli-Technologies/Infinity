package com.meti.app.control;

import com.meti.lib.fx.Sequencer;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Menu extends InfinityController {
    @FXML
    private Text versionText;

    @Override
    public void confirmInfinity() {
        String version = properties.getProperty("version");
        versionText.setText("Version: " + version);

        sequencerManager.sequences.put("EntryViewer", new Sequencer<>(
                getClass().getResource("/com/meti/app/control/Menu.fxml"),
                getClass().getResource("/com/meti/app/control/EntryViewer.fxml"),
                getClass().getResource("/com/meti/app/control/Display.fxml"))
        );

        setSequence("EntryViewer", 0);
    }

    @FXML
    public void openConnections() {
        toNext();
    }

    @FXML
    public void openServers() {
        toNext();
    }

    @FXML
    public void openLocals() {
        toNext();
    }

    @FXML
    public void openSettings() {
        try {
            Settings settings = onto(getSettingsURL());
            settings.backURL.set(getClass().getResource("/com/meti/app/control/Menu.fxml"));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    private URL getSettingsURL() {
        return getClass().getResource("/com/meti/app/control/Settings.fxml");
    }
}
