package com.meti.app;

import com.meti.app.control.connect.ConnectionCreatorView;
import com.meti.lib.SocketConnection;
import com.meti.lib.fx.Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class SocketConnectionCreator extends Controller implements ConnectionCreatorView.ConnectionCreator<SocketConnectionCreatorView, SocketConnection> {
    private SocketConnectionCreatorView socketConnectionCreatorView;

    @Override
    public String getName() {
        return "Sockets";
    }

    @Override
    public Class<SocketConnectionCreatorView> getControllerClass() {
        return SocketConnectionCreatorView.class;
    }


    @Override
    public URL getURL() {
        return getClass().getResource("/com/meti/app/SocketConnectionCreatorView.fxml");
    }

    @Override
    public SocketConnection get() {
        try {
            InetAddress address = InetAddress.getByName(socketConnectionCreatorView.addressField.getText());
            int port = Integer.parseInt(socketConnectionCreatorView.portField.getText());
            return new SocketConnection(new Socket(address, port));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void accept(SocketConnectionCreatorView socketConnectionCreatorView) {
        this.socketConnectionCreatorView = socketConnectionCreatorView;
    }
}
