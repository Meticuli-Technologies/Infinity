package com.meti.core.init;

import com.meti.core.load.PropertiesLoader;
import com.meti.core.state.StateImpl;
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
public final class InfinityInitializer {
    private final StateImpl stateImpl;
    private final Logger logger;

    public InfinityInitializer(StateImpl stateImpl, Logger logger) {
        this.stateImpl = stateImpl;
        this.logger = logger;
    }

    public void initializer(Stage primaryStage) throws IOException {
        Properties properties = initProperties();
        stateImpl.add(properties);

        Path modDirectory = PathUtil.ensureDirectory(Paths.get(".\\mods"));
        initModuleManager(modDirectory);
        stateImpl.add(logger);


    }

    private Properties initProperties() throws IOException {
        return new PropertiesLoader().load();
    }

    private void initModuleManager(Path modDirectory) throws IOException {
        ModManagerImpl modManagerImpl = new ModManager();
        modManagerImpl.loadAll(modDirectory);
    }
}
