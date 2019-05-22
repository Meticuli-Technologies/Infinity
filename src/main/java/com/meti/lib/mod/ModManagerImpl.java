package com.meti.lib.mod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface ModManagerImpl {
    void load(URL jarURL) throws IOException;

    void loadAll(Path directory) throws IOException;

    <T> Set<T> getAllInstances(Class<T> tClass, List<Object> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
