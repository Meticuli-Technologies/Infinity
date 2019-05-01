package com.meti.app.control.client;

import com.meti.app.control.view.ViewModel;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/30/2019
 */
public class ClientDisplay extends InfinityClientController implements Initializable {
    @FXML
    private Text addressText;

    @FXML
    private Text portText;

    @FXML
    private ListView<String> viewListView;

    public ClientDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Socket socket = client.source.socket;
        setText(socket.getInetAddress(), socket.getLocalPort());

        Set<ViewModel> viewModels = viewModelStream(state.getModuleManager().getImplementations(ViewModel.class))
                .collect(Collectors.toSet());
    }

    private Stream<? extends ViewModel> viewModelStream(Stream<Class<? extends ViewModel>> implStream) {
        return implStream.map(state.getConsole().getFactory().apply(aClass -> aClass.getDeclaredConstructor()))
                .flatMap(Optional::stream)
                .map(state.getConsole().getFactory().apply(constructor -> constructor.newInstance()))
                .flatMap(Optional::stream);
    }

    private void setText(InetAddress inetAddress, int localPort) {
        addressText.setText(inetAddress.toString());
        portText.setText(String.valueOf(localPort));
    }
}
