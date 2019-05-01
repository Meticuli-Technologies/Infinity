package com.meti.app.control.util.alert;

import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.StageManager;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;

import static com.meti.lib.fx.ControllerLoader.loadFXMLBundleFrom;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class Alerts {
    private Alerts() {
    }

    public static void tryShowNewInstance(Exception e, InfinityState state) {
        try {
            newAlertStage(e, state).showAndWait();
        } catch (Exception alertException) {
            state.getConsole().log(Level.WARNING, alertException);
        }
    }

    private static Stage newAlertStage(Exception exception, InfinityState state) throws java.io.IOException {
        FXMLBundle<Alert> bundle = loadFXMLBundleFrom(getAlertResource(), state);
        bundle.controller.setText(exception);
        return state.getStageManager().setRootToStage(bundle.root, StageManager.NEW_STAGE);
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }
}
