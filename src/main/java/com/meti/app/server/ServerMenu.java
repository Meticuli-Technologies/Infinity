package com.meti.app.server;

import com.meti.app.Controls;
import com.meti.app.client.InfinityController;
import com.meti.lib.javafx.InjectorLoader;
import com.meti.lib.javafx.StageManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ServerMenu extends InfinityController {
    @FXML
    private TextField portField;

    public ServerMenu(Controls controls) {
        super(controls);
    }

    @FXML
    public void host() {
        try {
            int port = Integer.parseInt(portField.getText());
            state.add((ServerBootstrap) () -> port);
            loadServerDisplay(toolkit.getStageManager());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadServerDisplay(StageManager stageManager) throws IOException {
        Parent root = InjectorLoader.load(List.of(getControls()), getServerDisplayURL());
        stageManager.loadPrimaryStage(root);
    }

    private URL getServerDisplayURL() {
        return getClass().getResource("/com/meti/app/server/ServerDisplay.fxml");
    }
}
