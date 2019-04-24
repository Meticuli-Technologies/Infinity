package com.meti.app.control;

import com.meti.lib.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

import static com.meti.lib.fx.StateControllerLoader.load;
import static com.meti.lib.util.URLUtil.getResource;

public class Menu extends InfinityController {
    @FXML
    private TextField portField;

    public Menu(State state) {
        super(state);
    }

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){

    }

    public static Parent loadMenuParent(State mainState) throws IOException {
        return load(getMenuResource(), mainState);
    }

    private static URL getMenuResource() {
        return getResource("/com/meti/app/control/Menu.fxml");
    }
}
