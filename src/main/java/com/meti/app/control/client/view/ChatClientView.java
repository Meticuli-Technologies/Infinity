package com.meti.app.control.client.view;

import com.meti.app.control.view.ClientView;

import java.net.URL;

public class ChatClientView implements ClientView {
    @Override
    public String getName() {
        return "ChatController";
    }

    @Override
    public URL getURL() {
        return getClass().getResource("/com/meti/app/control/client/view/Chat.fxml");
    }
}
