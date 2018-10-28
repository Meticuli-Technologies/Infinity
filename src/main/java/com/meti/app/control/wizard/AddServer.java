package com.meti.app.control.wizard;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.app.main.ClientMain.clientState;
import static com.meti.lib.client.ClientLauncher.launch;
import static java.util.Optional.empty;
import static java.util.Optional.of;

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
        getDependency(WindowedDependency.class).stageProperty.get().close();
    }

    @FXML
    public void apply() {
        try {
            Optional<Socket> socketOptional = constructSocket();
            if (socketOptional.isPresent()) {
                Socket socket = socketOptional.get();
                clientState.addClient(launch(socket));

                getDependency(WindowedDependency.class).stageProperty.get().close();
            } else {
                //TODO: alert user
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }
    }

    private Optional<Socket> constructSocket() throws IOException {
        Optional<InetAddress> address = getAddress();
        OptionalInt port = getPort();

        return address.isPresent() && port.isPresent()
                ? of(new Socket(address.get(), port.getAsInt()))
                : empty();
    }

    private Optional<InetAddress> getAddress() throws UnknownHostException {
        return !addressField.getText().trim().equals("")
                ? of(InetAddress.getByName(addressField.getText()))
                : empty();
    }

    private OptionalInt getPort() {
        return getPortFromText(portField.getText());
    }

    private OptionalInt getPortFromText(String portText) {
        return !portText.trim().equals("")
                ? OptionalInt.of(Integer.parseInt(portText))
                : OptionalInt.empty();
    }
}
