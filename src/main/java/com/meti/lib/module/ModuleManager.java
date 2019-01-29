package com.meti.lib.module;

import com.meti.lib.reflect.ClassSource;
import com.meti.lib.reflect.SetClassSource;
import com.meti.lib.util.Clause;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    public Set<Module> loadModules(Path modulesDirectory) throws IOException {
        Set<Module> loaded = Files.list(modulesDirectory)
                .map(Clause.wrap(this::loadModule))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        loaded.forEach(module -> modules.put(module.name, module));
        return loaded;
    }

    public Set<String> getClassNames(Path jar) throws IOException {
        return new ZipFile(jar.toFile()).stream()
                .map(ZipEntry::getName)
                .collect(Collectors.toSet());
    }

    private Module loadModule(Path moduleDirectory) throws Exception {
        if (!Files.isDirectory(moduleDirectory)) {
            throw new IllegalArgumentException(moduleDirectory + " must be a directory");
        }

        ClassSource classSource = loadBinaries(moduleDirectory);
        return new Module(moduleDirectory.getFileName().toString(), classSource);
    }

    private ClassSource loadBinaries(Path moduleDirectory) throws Exception {
        Path binaryDirectory = getBinaryDirectory(moduleDirectory);
        Set<Path> jars = Files.list(binaryDirectory)
                .filter(path -> path.endsWith("jar"))
                .collect(Collectors.toSet());

        Set<String> allClassNames = jars.stream()
                .map(Clause.wrap(this::getClassNames))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        URLClassLoader classLoader = getClassLoader(jars);
        Set<Class<?>> classes = new HashSet<>();
        for (String className : allClassNames) {
            try {
                classes.add(classLoader.loadClass(className));
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Loaded binaries and class names, but their contents do not match");
            }
        }

        return new SetClassSource(classes);
    }

    private Path getBinaryDirectory(Path moduleDirectory) {
        Path binaries = moduleDirectory.resolve("bin");
        if (!Files.exists(binaries)) {
            throw new IllegalArgumentException("Could not find binaries for " + moduleDirectory + " at " + binaries);
        }
        return binaries;
    }

    private URLClassLoader getClassLoader(Set<Path> jars) {
        return new URLClassLoader(jars.stream()
                .map(Clause.wrap(this::loadBinary))
                .flatMap(Optional::stream)
                .toArray(URL[]::new));
    }

    private URL loadBinary(Path jar) throws MalformedURLException {
        if (!jar.endsWith("jar")) {
            throw new IllegalArgumentException(jar + " is an invalid binary");
        }
        return jar.toUri().toURL();
    }
}
