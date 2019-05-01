package com.meti.app.control.util.alert;

import com.meti.app.control.util.InfinityController;
import com.meti.lib.State;
import com.meti.lib.util.ExceptionUtil;
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
        stackTraceArea.setText(ExceptionUtil.stackTraceString(exception));
    }
}
