package com.meti.app;

import com.meti.app.chat.ChatEvent;
import com.meti.app.chat.Message;
import com.meti.app.control.InfinityServerController;
import com.meti.lib.control.ControllerLoader;
import com.meti.lib.util.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ServerDisplay extends InfinityServerController implements Initializable {
    @FXML
    private ListView<InetAddress> clientListView;

    @FXML
    private ListView<Message> chatListView;

    @FXML
    private TabPane tabs;

    public ServerDisplay(State state) {
        super(state);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.onAccept = client -> clientListView.getItems().add(client.socket.getInetAddress());
        server.chat.eventManager.put(ChatEvent.ON_ADDED, message ->
                Platform.runLater(() -> chatListView.getItems().add(message))
        );
    }

    @FXML
    public void openFiles() {
        try {
            Tab tab = new Tab("Files", ControllerLoader.load(getClass().getResource("/com/meti/Files.fxml"), state));
            tabs.getTabs().add(tab);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void submitIssue() {
        hostServices.showDocument("https://github.com/Meticuli-Technologies/Infinity/issues/new");
    }
}
