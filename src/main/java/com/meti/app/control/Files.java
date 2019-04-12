package com.meti.app.control;

import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Files extends InfinityServerController {
    @FXML
    private Text nameText;

    @FXML
    private Text sizeText;

    @FXML
    private Text lastAccessedText;

    public Files(State state) {
        super(state);
    }

    @FXML
    public void index() {

    }
}

