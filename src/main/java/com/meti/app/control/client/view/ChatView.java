package com.meti.app.control.client.view;

import com.meti.app.control.view.ViewModel;

import java.net.URL;

public class ChatView implements ViewModel  {
    @Override
    public String getName() {
        return "Chat";
    }

    @Override
    public URL getURL() {
        return getClass().getResource("/com/meti/app/control/client/view/Chat.fxml");
    }
}
