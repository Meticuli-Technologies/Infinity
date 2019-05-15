package com.meti.control;

import com.meti.core.InfinitySystem;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

import static com.meti.util.ExceptionUtil.stackTraceString;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Menu extends InfinityController {
    private final MenuModel menuModel;

    public Menu(InfinitySystem system) {
        super(system);
        this.menuModel = new MenuModel(system);
    }

    @FXML
    public void connect() {
        try {
            Stage stage = system.getStageManager().allocate();
            stage.setScene(new Scene(new Injector(URLSource.of("/com/meti/ConnectMenu.fxml"), system, menuModel.getClientLoader()).load()));
            stage.show();
        } catch (IOException e) {
            system.getLogger().log(Level.SEVERE, stackTraceString(e));
        }
    }

    public MenuModel getModel() {
        return menuModel;
    }

    @FXML
    public void local() {
        try {
            menuModel.loadClient(menuModel.loadServer());
        } catch (Exception e) {
            system.getLogger().log(Level.SEVERE, stackTraceString(e));
        }
    }
}
