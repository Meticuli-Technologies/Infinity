package com.meti.app.core.init;

import com.meti.app.core.load.PropertiesLoader;
import com.meti.app.core.state.InfinityToolkit;
import com.meti.app.core.state.StateImpl;
import com.meti.app.core.state.Toolkit;
import com.meti.lib.fx.StageManager;
import com.meti.lib.fx.StageManagerImpl;
import com.meti.lib.mod.ModManager;
import com.meti.lib.mod.ModManagerImpl;
import com.meti.lib.util.PathUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Initializer implements InitializerImpl {
    private static final Path MODS_DIRECTORY = Paths.get(".\\mods");
    private final Logger logger;

    public Initializer(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Toolkit init(Stage primaryStage) throws IOException {
        Path modDirectory = PathUtil.ensureDirectory(MODS_DIRECTORY);
        Toolkit toolkit = new InfinityToolkit();
        Set<Object> components = initComponents(primaryStage, modDirectory);
        installComponents(components, toolkit.getState());
        return toolkit;
    }

    private Set<Object> initComponents(Stage primaryStage, Path modDirectory) throws IOException {
        return Set.of(
                    this.logger,
                    initProperties(),
                    initModuleManager(modDirectory),
                    initStageManager(primaryStage)
            );
    }

    private void installComponents(Collection<Object> components, StateImpl stateImpl) {
        stateImpl.addAll(components);
    }

    private Properties initProperties() throws IOException {
        return new PropertiesLoader().load();
    }

    private ModManagerImpl initModuleManager(Path modDirectory) throws IOException {
        ModManagerImpl modManagerImpl = new ModManager();
        modManagerImpl.loadAll(modDirectory);
        return modManagerImpl;
    }

    private StageManagerImpl initStageManager(Stage primaryStage) {
        return new StageManager(primaryStage);
    }
}
