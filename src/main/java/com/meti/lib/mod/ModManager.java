package com.meti.lib.mod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class ModManager implements ModManagerImpl {
    private static final String CLASS_SUFFIX = ".class";
    private final Set<ModImpl> modImpls = new HashSet<>();

    @Override
    public void loadAll(Path directory) throws IOException {
        if (!Files.isDirectory(directory))
            throw new IllegalArgumentException(directory + " is not a directory.");
        for (URI uri : listURIs(directory)) {
            load(uri.toURL());
        }
    }

    @Override
    public void load(URL jarURL) throws IOException {
        Set<String> contentNames = getContentNames(jarURL);
        Set<String> classNames = convertToClassNames(contentNames);
        Set<Class<?>> initialClasses = loadInitialClasses(jarURL, classNames);
        throwIfNoInitialClasses(initialClasses);
        ModImpl modImpl = new Mod(initialClasses);
        modImpls.add(modImpl);
    }

    private Set<URI> listURIs(Path directory) throws IOException {
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.filter(path -> path.endsWith(".jar"))
                    .map(Path::toUri)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public <T> Set<T> getAllInstances(Class<T> tClass, List<Object> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<ModImpl> mods = modImpls.stream()
                .filter(mod -> mod.containsImplementationClass(tClass))
                .collect(Collectors.toSet());
        Set<T> instances = new HashSet<>();
        for (ModImpl mod : mods) {
            instances.addAll(mod.instantiate(tClass, dependencies));
        }
        return instances;
    }

    private void throwIfNoInitialClasses(Set<Class<?>> initialClasses) throws IOException {
        if(initialClasses.isEmpty()){
            throw new IOException("No classes were found");
        }
    }

    private Set<Class<?>> loadInitialClasses(URL jarURL, Set<String> classNames) throws IOException {
        Set<Class<?>> initialClasses = new HashSet<>();
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL})) {
            for (String className : classNames) {
                Class<?> loadedClass = classLoader.loadClass(className);
                initialClasses.add(loadedClass);
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to read URL as JAR", e);
        }
        return initialClasses;
    }

    private Set<String> convertToClassNames(Set<String> contentNames) {
        return contentNames.stream()
                    .filter(s -> s.endsWith(CLASS_SUFFIX))
                    .map(s -> s.replace(CLASS_SUFFIX, "")).collect(Collectors.toSet());
    }

    private Set<String> getContentNames(URL jarURL) throws IOException {
        ZipInputStream inputStream = new ZipInputStream(jarURL.openStream());
        Set<String> contentNames = new HashSet<>();
        ZipEntry currentEntry;
        do {
            currentEntry = inputStream.getNextEntry();
            if (currentEntry == null) break;
            else contentNames.add(currentEntry.getName());
        } while (true);
        inputStream.close();
        return contentNames;
    }
}
