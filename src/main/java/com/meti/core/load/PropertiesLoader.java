package com.meti.core.load;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class PropertiesLoader implements PropertiesLoaderImpl {
    @Override
    public Properties load() throws IOException {
        Path propertiesPath = createIfNotExists();
        return loadFromPath(propertiesPath);
    }

    private Path createIfNotExists() throws IOException {
        Path propertiesPath = Paths.get(".\\Infinity.properties");
        if (!Files.exists(propertiesPath)) {
            Files.createFile(propertiesPath);
        }
        return propertiesPath;
    }

    private Properties loadFromPath(Path propertiesPath) throws IOException {
        InputStream inStream = Files.newInputStream(propertiesPath);
        Properties properties = new Properties();
        properties.load(inStream);
        inStream.close();
        return properties;
    }
}
