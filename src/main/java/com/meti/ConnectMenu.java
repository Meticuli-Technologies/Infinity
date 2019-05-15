package com.meti;

import com.meti.control.ClientLoader;
import com.meti.control.InfinityController;
import com.meti.core.InfinitySystem;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;

import static com.meti.util.ExceptionUtil.stackTraceString;

public class ConnectMenu extends InfinityController {
    private final ClientLoader loader;
    @FXML
    private TextField address;
    @FXML
    private TextField portField;

    public ConnectMenu(InfinitySystem system, ClientLoader loader) {
        super(system);
        this.loader = loader;
    }

    @FXML
    public void back() {
        try {
            Stage stage = system.getStageManager().allocate();
            stage.setScene(new Scene(new Injector(URLSource.of("/com/meti/Menu.fxml"), system).load()));
            stage.show();
        } catch (IOException e) {
            system.getLogger().log(Level.SEVERE, stackTraceString(e));
        }
    }

    @FXML
    public void next() {
        try {
            int port = Integer.parseInt(portField.getText());
            loader.loadClient(port, InetAddress.getByName(address.getText()), system, system.getStageManager().allocate());
        } catch (Exception e) {
            system.getLogger().log(Level.SEVERE, stackTraceString(e));
        }
    }
}
