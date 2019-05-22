package com.meti.core.init;

import com.meti.core.load.PropertiesLoader;
import com.meti.core.state.StateImpl;
import com.meti.lib.mod.ModManager;
import com.meti.lib.util.PathUtil;

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

    public void initializer() throws IOException {
        Properties properties = new PropertiesLoader().load();
        stateImpl.add(properties);

        ModManager modManager = new ModManager();
        Path modDirectory = PathUtil.ensureDirectory(Paths.get(".\\mods"));
        modManager.loadAll(modDirectory);
        stateImpl.add(logger);
    }
}
