package com.meti.app.control.util.alert;

import com.meti.app.control.util.InfinityController;
import com.meti.lib.State;
import com.meti.lib.util.ExceptionUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class Alert extends InfinityController {
    @FXML
    private Text messageText;

    @FXML
    private TextArea stackTraceArea;

    public Alert(State state) {
        super(state);
    }

    public void show(Exception exception) {
        messageText.setText(exception.getMessage());
        stackTraceArea.setText(ExceptionUtil.stackTraceString(exception));
    }
}
