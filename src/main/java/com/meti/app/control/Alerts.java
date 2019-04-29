package com.meti.app.control;

import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.StageManager;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
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

    public static <T> Optional<T> showInstance(Exception e, InfinityState state) {
        try {
            newInstance(e, state)
                    .orElseThrow()
                    .showAndWait();
        } catch (Exception alertException) {
            state.getConsole().log(Level.WARNING, alertException);
        }

        return Optional.empty();
    }

    private static Optional<Stage> newInstance(Exception exception, InfinityState state) {
        try {
            return Optional.of(newAlertStage(exception, state));
        } catch (Exception e) {
            return state.getConsole().logAsEmpty(Level.WARNING, e);
        }
    }

    private static Stage newAlertStage(Exception exception, InfinityState state) throws java.io.IOException {
        FXMLBundle<Alert> bundle = loadFXMLBundleFrom(getAlertResource(), state);
        bundle.controller.show(exception);
        return state.getStageManager().setRootToStage(bundle.root, StageManager.NEW_STAGE);
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }
}
