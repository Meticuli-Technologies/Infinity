package com.meti.app;

import com.meti.app.control.connect.ConnectionCreator;
import com.meti.lib.SocketConnection;

import java.net.URL;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class SocketConnectionCreatorModel extends ConnectionCreator<SocketConnectionCreatorView, SocketConnection> {
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
            Optional<SocketConnection> connection = controller.getConnection();
            if (connection.isPresent()) {
                return connection.get();
            } else {
                throw new IllegalStateException("No connection found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
