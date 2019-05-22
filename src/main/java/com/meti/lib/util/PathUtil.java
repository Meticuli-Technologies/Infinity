package com.meti.lib.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class PathUtil {
    public static Path ensureDirectory(Path directory) throws IOException {
        if(!Files.exists(directory)){
            Files.createDirectory(directory);
        }
        return directory;
    }
}
