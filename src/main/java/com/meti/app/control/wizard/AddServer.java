package com.meti.app.control.wizard;

import com.meti.app.main.ClientMain;
import com.meti.lib.client.ClientLauncher;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class AddServer extends Controller {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @Override
    public Set<Class<? extends Dependency>> getDependencyClasses() {
        return Stream.of(WindowedDependency.class).collect(Collectors.toSet());
    }

    @FXML
    public void cancel() {
        getDependency(WindowedDependency.class).stage.close();
    }

    @FXML
    public void apply() {
        InetAddress address = null;
        int port = -1;
        try {
            if (!addressField.getText().trim().equals("")) {
                address = InetAddress.getByName(addressField.getText());
            }

            if (!portField.getText().trim().equals("")) {
                port = Integer.parseInt(portField.getText());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //TODO: handle exception
        }

        if (address != null && port != -1) {
            try {
                ClientMain.clientState.clientMap.put(address, ClientLauncher.launch(address, port));

                getDependency(WindowedDependency.class).stage.close();
            } catch (IOException e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        } else {
            //TODO: alert user
        }
    }
}
