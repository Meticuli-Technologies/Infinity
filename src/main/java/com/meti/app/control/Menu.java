package com.meti.app.control;

import com.meti.app.io.InfinityServer;
import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.io.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
            int port = Integer.parseInt(portField.getText());
            InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(port)));
            serviceManager.service.submit(server.getListener());
            state.add(server);

            onto(getClass().getResource("/com/meti/app/control/ServerDisplay.fxml"), 0);

            ObjectSource<SocketSource> client = new ObjectSource<>(new SocketSource(new Socket(InetAddress.getByName("localhost"), port)));
            ObjectChannel channel = client.getChannel(true);
            Querier querier = new Querier(channel);
            serviceManager.service.submit(querier.getListener());

            onto(getClass().getResource("/com/meti/app/control/ClientDisplay.fxml"), 1);

            //TODO: do something with futures
        } catch (Exception e) {
            Alerts.showInstance(e, state);
        }
    }
}
