package com.meti.app.control;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.net.ServerImpl;
import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.PortSourceSupplier;
import com.meti.lib.source.SourceSupplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class ServerDisplay implements Initializable {
    private final Toolkit toolkit;
    @FXML
    private ListView<String> clients;

    @FXML
    private Text portText;

    public ServerDisplay(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initServer(toolkit.getServer());
    }

    private void initServer(ServerImpl<CompoundSource<?, ?>, PortSourceSupplier> server) {
        server.setOnAccept(compoundSource -> clients.getItems().add(compoundSource.toString()));
        portText.setText(String.valueOf(server.getPort()));
    }
}
