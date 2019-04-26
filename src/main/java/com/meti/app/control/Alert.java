package com.meti.app.control;

import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.State;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.log.Console;
import com.meti.lib.util.ExceptionUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;

import static com.meti.lib.fx.StateControllerLoader.loadFXMLBundleFrom;

public class Alert extends InfinityController {
    @FXML
    private Text messageText;

    @FXML
    private TextArea stackTraceArea;

    public Alert(State state) {
        super(state);
    }

    static Optional<Stage> newInstance(Exception exception, InfinityState state) {
        try {
            return Optional.of(newAlertStage(exception, state));
        } catch (Exception e) {
            return returnEmpty(e, state.getConsole());
        }
    }

    private static Stage newAlertStage(Exception exception, InfinityState state) throws java.io.IOException {
        FXMLBundle<Alert> bundle = loadFXMLBundleFrom(getAlertResource(), state);
        bundle.controller.show(exception);
        return state.getStageManager().createFrom(bundle.root);
    }

    private static Optional<Stage> returnEmpty(Exception e, Console console) {
        console.log(Level.WARNING, e);
        return Optional.empty();
    }

    private void show(Exception exception) {
        messageText.setText(exception.getMessage());
        stackTraceArea.setText(ExceptionUtil.stackTraceString(exception));
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }
}
