package com.meti.app.control.util.alert;

import com.meti.app.control.util.InfinityController;
import com.meti.lib.util.collect.State;
import com.meti.lib.util.log.Exceptions;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/*
This suppression is added because
the Alert class is only utilized inside of Alerts,
therefore the compiler marks this class as package private.
However, FXML controllers are required to be public,
but the compiler is not detecting this issue.
 */
@SuppressWarnings("WeakerAccess")
public class Alert extends InfinityController {
    @FXML
    private Text messageText;

    @FXML
    private TextArea stackTraceArea;

    public Alert(State state) {
        super(state);
    }

    public void setText(Exception exception) {
        messageText.setText(exception.getMessage());
        stackTraceArea.setText(Exceptions.stackTraceString(exception));
    }
}
