package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import com.meti.util.ExceptionUtil;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Menu extends InfinityController {
    private final ClientLoader clientLoader = new ClientLoader();
    private final ServerLoader serverLoader = new ServerLoader();
    private final MenuModel menuModel = new MenuModel(this);

    public Menu(Logger logger, ExecutorServiceManager executorServiceManager, StageManager stageManager, InfinityModuleManager moduleManager) {
        super(logger, executorServiceManager, stageManager, moduleManager);
    }

    public ClientLoader getClientLoader() {
        return clientLoader;
    }

    public ServerLoader getServerLoader() {
        return serverLoader;
    }

    @FXML
    public void local() {
        try {
            menuModel.loadClient(menuModel.loadServer());
        } catch (Exception e) {
            logger.log(Level.SEVERE, ExceptionUtil.stackTraceString(e));
        }
    }
}
