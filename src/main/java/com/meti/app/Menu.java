package com.meti.app;

import com.meti.Main;
import com.meti.control.ContinuedController;
import com.meti.control.ControllerLoader;
import com.meti.control.PostInitializable;
import com.meti.control.depend.exception.ExceptionDependency;
import com.meti.control.depend.window.WindowedDependency;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/5/2018
 */
public class Menu extends ContinuedController implements PostInitializable {
    private Parent stepParent;
    private Step stepController;

    @Override
    public String[] getResourcesStrings() {
        String APP_FOLDER = "/com/meti/app/";
        return new String[]{
                APP_FOLDER + "Step.fxml",
                APP_FOLDER + "HostAServer.fxml",
                APP_FOLDER + "CreateLocalServer.fxml",
                APP_FOLDER + "ConnectToAServer.fxml"
        };
    }

    @Override
    public void postInitialize() {
        try {
            ControllerLoader loader = new ControllerLoader(resources[0], Main.state.dependencyManager);
            stepParent = loader.load();
            stepController = loader.getController();

            Step.setEnds(
                    getClass().getResource("/com/meti/app/Menu.fxml"),
                    getClass().getResource("/com/meti/Display.fxml"),
                    stepController
            );
        } catch (IOException e) {
            getDependency(ExceptionDependency.class).accept(e);
        }
    }

    @FXML
    public void hostAServer() {
        stepController.steps = new URL[]{resources[1]};

        getDependency(WindowedDependency.class).onto(stepParent);
    }

    @FXML
    public void createLocalServer() {
        stepController.steps = new URL[]{resources[2]};
    }

    @FXML
    public void connectToAServer() {
        stepController.steps = new URL[]{resources[3]};
    }
}
