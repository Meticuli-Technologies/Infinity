package com.meti.app.control;

import com.meti.app.io.InfinityServer;
import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.io.ServerSocketSupplier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import static com.meti.lib.util.URLUtil.getResource;

public class Menu extends InfinityController {
    /* private final MenuModel menuModel = new MenuModel(state);*/
    @FXML
    private TextField portField;

    public Menu(State state) {
        super(state);
    }

    public static Parent loadMenuParent(State mainState) throws IOException {
        return ControllerLoader.loadRoot(getMenuResource(), mainState);
    }

    private static URL getMenuResource() {
        return getResource("/com/meti/app/control/Menu.fxml");
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void next() {
        try {
            InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(Integer.parseInt(portField.getText()))));
            serviceManager.service.submit(server.getListener());
            //TODO: do something with future

            onto(getClass().getResource("/com/meti/app/control/ServerDisplay.fxml"));
        } catch (Exception e) {
            Alerts.showInstance(e, state);
        }
    }
}
