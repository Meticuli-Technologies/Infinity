package com.meti.app.control;

import com.meti.app.io.InfinityServerListener;
import com.meti.lib.State;
import com.meti.lib.fx.StateControllerLoader;
import com.meti.lib.net.Processor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

import static com.meti.lib.util.URLUtil.getResource;

public class Menu extends InfinityController {
    @FXML
    private TextField portField;

    public Menu(State state) {
        super(state);
    }

    public static Parent loadMenuParent(State mainState) throws IOException {
        return StateControllerLoader.loadRoot(getMenuResource(), mainState);
    }

    private static URL getMenuResource() {
        return getResource("/com/meti/app/control/Menu.fxml");
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void next() {
        try{
            int port = Integer.parseInt(portField.getText());

            InfinityServerListener serverListener = new InfinityServerListener(port, state.getExecutorServiceManager().service);
            serverListener.listen();
            state.add(serverListener);
        } catch (Exception e){
            Alerts.showInstance(e, state);
        }
    }
}
