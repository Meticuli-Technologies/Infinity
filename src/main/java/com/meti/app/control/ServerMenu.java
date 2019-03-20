package com.meti.app.control;

import com.meti.app.net.InfinityServer;
import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.net.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMenu extends Controller {
    @FXML
    private TextField portField;

    public ServerMenu(State state) {
        super(state);
    }

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){
        try {
            Server server = new InfinityServer(new ServerSocket(Integer.parseInt(portField.getText())));
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
