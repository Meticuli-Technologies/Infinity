package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.StateControllerLoader;
import com.meti.lib.util.ExceptionUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Alert extends InfinityController {
    @FXML
    private Text messageText;

    public Alert(State state) {
        super(state);
    }

    public static Stage create(Exception exception) throws IOException {
        return create(ExceptionUtil.stackTraceString(exception));
    }

    public static Stage create(String message) throws IOException {
        FXMLBundle<Alert> bundle = StateControllerLoader.loadBundle(getAlertResource(), new State());
        bundle.initRoot(new Consumer<Parent>() {
            @Override
            public void accept(Parent parent) {

            }
        });

/*        Stage stage = buildStage(loader.load());
        Alert controller = loader.getController();
        controller.setMessage(message);*/
        return new Stage();
    }

    private static URL getAlertResource() {
        return Alert.class.getResource("/com/meti/app/control/Alert.fxml");
    }

    private static Stage buildStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        return stage;
    }

    private void setMessage(String message) {
        messageText.setText(message);
    }
}
