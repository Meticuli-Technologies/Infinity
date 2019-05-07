package com.meti.app.control.menu;

import com.meti.app.control.util.InfinityController;
import com.meti.app.control.util.alert.Alerts;
import com.meti.app.io.InfinityServer;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.util.collect.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.meti.lib.fx.URLs.getResource;

public class Menu extends InfinityController {
    private final MenuModel menuModel;

    @FXML
    TextField portField;

    public Menu(State state) {
        super(state);
        this.menuModel = new MenuModel(this.state.getExecutorServiceManager());
    }

    public static Parent loadMenuParent(State mainState) throws IOException {
        return ControllerLoader.loadRoot(getMenuResource(), mainState);
    }

    private static URL getMenuResource() {
        return getResource("/com/meti/app/control/menu/Menu.fxml");
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void next() {
        try {
            loadModel();
            loadDisplays();
        } catch (Exception e) {
            Alerts.tryShowNewInstance(e, state);
        }
    }

    private void loadModel() throws IOException {
        int port = Integer.parseInt(portField.getText());
        InfinityServer server = menuModel.setupServer(port, state.getConsole().getFactory(), state.getModuleManager());
        List<Object> objects = menuModel.setupClient(port);
        state.add(server);
        state.addAll(objects);
    }

    private void loadDisplays() throws IOException {
        onto(getClass().getResource("/com/meti/app/control/server/ServerDisplay.fxml"), 0);
        onto(getClass().getResource("/com/meti/app/control/client/ClientDisplay.fxml"), 1);
    }
}
