package com.meti.core.init;

import com.meti.core.load.PropertiesLoader;
import com.meti.core.state.StateImpl;
import com.meti.lib.fx.StageManager;
import com.meti.lib.mod.ModManager;
import com.meti.lib.mod.ModManagerImpl;
import com.meti.lib.util.PathUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class InfinityInitializer {
    private static final Path MODS_DIRECTORY = Paths.get(".\\mods");
    private final StateImpl stateImpl;
    private final Logger logger;

    public InfinityInitializer(StateImpl stateImpl, Logger logger) {
        this.stateImpl = stateImpl;
        this.logger = logger;
    }

    public void initializer(Stage primaryStage) throws IOException {
        Path modDirectory = PathUtil.ensureDirectory(MODS_DIRECTORY);
        stateImpl.add(logger);
        stateImpl.add(initProperties());
        stateImpl.add(initModuleManager(modDirectory));
        stateImpl.add(initStageManager(primaryStage));
    }

    private Properties initProperties() throws IOException {
        return new PropertiesLoader().load();
    }

    private ModManagerImpl initModuleManager(Path modDirectory) throws IOException {
        ModManagerImpl modManagerImpl = new ModManager();
        modManagerImpl.loadAll(modDirectory);
        return modManagerImpl;
    }

    private StageManager initStageManager(Stage primaryStage) {
        return new StageManager(primaryStage);
    }
}
