package com.meti.app.core.load;

import java.io.IOException;
import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface PropertiesLoaderImpl {
    Properties load() throws IOException;
}
