package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import javafx.fxml.FXML;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Menu extends Controller {
    @FXML
    public void connectToAServer(){
        //TODO: connect to a server
    }

    @FXML
    public void hostALocalServer(){
        try {
            onto(getClass().getResource("/com/meti/app/HostALocalServer.fxml"), "Host A Local Server");
        } catch (IOException e) {
            state.getLogger().error("Failed to load HostALocalServer.fxml", e);
        }
    }

    @FXML
    public void hostAServer(){
        //TODO: host a server
    }
}
