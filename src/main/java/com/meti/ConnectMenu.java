package com.meti;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.control.InfinityController;
import com.meti.fx.Injector;
import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import com.meti.net.source.URLSource;
import com.meti.util.ExceptionUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.meti.util.ExceptionUtil.*;

public class ConnectMenu extends InfinityController {
    @FXML
    private TextField address;

    @FXML
    private TextField port;

    protected ConnectMenu(Logger logger, ExecutorServiceManager executorServiceManager, StageManager stageManager, InfinityModuleManager moduleManager) {
        super(logger, executorServiceManager, stageManager, moduleManager);
    }

    @FXML
    public void back() {
        try {
            Stage stage = stageManager.getStage(0);
            stage.setScene(new Scene(new Injector(URLSource.of("/com/meti/Menu.fxml"), logger, executorServiceManager, stageManager, moduleManager).load()));
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, stackTraceString(e));
        }
    }

    @FXML
    public void next() {

    }
}
