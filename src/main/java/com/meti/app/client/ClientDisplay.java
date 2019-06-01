package com.meti.app.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ClientDisplay {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    @FXML
    private Text statusText;

    @FXML
    private TextField portField;

    @FXML
    public void changePort(){
        portField.setEditable(true);
        String portValue = portField.getText();
        try {
            int port = Integer.parseInt(portValue);
            loadClient(port);
        } catch (NumberFormatException e) {
            statusText.setText("Invalid integer: " + portValue);
        }
    }

    private void loadClient(int port) {

    }
}