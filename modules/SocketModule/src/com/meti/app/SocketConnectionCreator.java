package com.meti.app;

import com.meti.app.control.ConnectionCreator;
import com.meti.lib.SocketConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public class SocketConnectionCreator extends ConnectionCreator<SocketConnection> {
    public SocketConnectionCreator() throws IOException {
        super("Socket", FXMLLoader.load(SocketConnectionCreator.class.getResource("/com/meti/app/SocketConnectionCreatorView.fxml")));
    }

    @Override
    public SocketConnection getResult() throws IOException {
        return new SocketConnection(null);
    }
}
