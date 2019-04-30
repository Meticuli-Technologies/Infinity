package com.meti.app.control;

import com.meti.app.control.helper.InfinityClientController;
import com.meti.lib.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

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
        addressText.setText(socket.getInetAddress().toString());
        portText.setText(String.valueOf(socket.getLocalPort()));
    }
}
