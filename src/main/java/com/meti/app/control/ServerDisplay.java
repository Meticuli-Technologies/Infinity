package com.meti.app.control;

import com.meti.lib.net.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/28/2019
 */
public class ServerDisplay {
    @FXML
    private Text portText;

    @FXML
    private ListView<Client> clientView;
}
