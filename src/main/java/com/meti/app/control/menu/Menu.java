package com.meti.app.control.menu;

import com.meti.app.control.util.InfinityController;
import com.meti.app.control.util.alert.Alerts;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.fxml.FutureFXChecker;
import com.meti.lib.util.collect.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import static com.meti.lib.fx.URLs.getResource;

public class Menu extends InfinityController {
    private final MenuModel menuModel = new MenuModel(this);

    @FXML
    TextField portField;

    public Menu(State state) {
        super(state);
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
            loadDisplays();
        } catch (Exception e) {
            Alerts.tryShowNewInstance(e, state);
        }
    }

    private void loadDisplays() throws IOException {
        FutureFXChecker checker = new MenuChecker();
        checker.start();
        loadDisplays(checker);
    }

    private void loadDisplays(FutureFXChecker checker) throws IOException {
        int port = Integer.parseInt(portField.getText());
        checker.futures.add(menuModel.setupServer(port));
        checker.futures.add(menuModel.setupClient(port));
        onto(getClass().getResource("/com/meti/app/control/server/ServerDisplay.fxml"), 0);
        onto(getClass().getResource("/com/meti/app/control/client/ClientDisplay.fxml"), 1);
    }

    private class MenuChecker extends FutureFXChecker {
        @Override
        public void accept(Exception e) {
            console.log(Level.WARNING, e);
        }
    }
}
