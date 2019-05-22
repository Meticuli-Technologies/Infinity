package com.meti.lib.mod;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface ModManagerImpl {
    void load(URL jarURL) throws IOException;

    void loadAll(Path directory) throws IOException;
}
