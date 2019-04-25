package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.concurrent.CompletableConsumer;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.util.ExceptionUtil;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

import static com.meti.lib.fx.StateControllerLoader.loadBundle;

public class Alert extends InfinityController {
    @FXML
    private Text messageText;

    public Alert(State state) {
        super(state);
    }

    public static Stage create(Exception exception) throws Exception {
        return create(ExceptionUtil.stackTraceString(exception));
    }

    public static Stage create(String message) throws Exception {
        CompletableConsumer<Stage> consumer = new CompletableConsumer<>();
        FXMLBundle<Alert> bundle = loadBundle(getAlertResource(), new State());
        bundle.initRoot(new StageBuilder(consumer))
                .initController(alert -> alert.setMessage(message));
        return consumer.get();
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }

    private void setMessage(String message) {
        messageText.setText(message);
    }
}
