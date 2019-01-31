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
    public final Map<String, Module> modules = new HashMap<>();

    public Set<ClassSource> getClassSources() {
        return modules.values()
                .stream()
                .map(Module::getSource)
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> implementationsOf(Class<?> clazz) {
        return modules.values().stream()
                .map(Module::getSource)
                .map(classSource -> classSource.bySuper(clazz))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<Module> loadModules(Path modulesDirectory) throws IOException {
        if(Files.exists(modulesDirectory)) {
            return Files.list(modulesDirectory)
                    .map(Clause.wrap(ModuleManager.this::loadModule))
                    .flatMap(Optional::stream)
                    .peek(module -> modules.put(module.name, module))
                    .collect(Collectors.toSet());
        }
        else{
            Files.createDirectory(modulesDirectory);
            return new HashSet<>();
        }
    }

    public Set<String> getClassNames(Path jar) throws IOException {
        return new ZipFile(jar.toFile()).stream()
                .map(ZipEntry::getName)
                .filter(s -> s.endsWith("class"))
                .map(s -> s.replaceAll("\\.class", ""))
                .map(s -> s.replace("/", "."))
                .collect(Collectors.toSet());
    }

    private Module loadModule(Path moduleDirectory) throws Exception {
        if (!Files.isDirectory(moduleDirectory)) {
            throw new IllegalArgumentException(moduleDirectory + " must be a directory");
        }

        return new Module(moduleDirectory.getFileName().toString(), loadBinaries(moduleDirectory));
    }

    private ClassSource loadBinaries(Path moduleDirectory) throws Exception {
        Path binaryDirectory = getBinaryDirectory(moduleDirectory);

        Set<Path> jarNames = Files.list(binaryDirectory)
                .filter(path -> path.toString().endsWith("jar"))
                .collect(Collectors.toSet());

        Set<String> collectedClassNames = jarNames.stream()
                .map(Clause.wrap(this::getClassNames))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        URLClassLoader classLoader = getClassLoader(jarNames);
        return new SetClassSource(collectedClassNames.stream()
                .map(Clause.wrap(classLoader::loadClass))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet()));
    }

    private Path getBinaryDirectory(Path moduleDirectory) throws IOException {
        Path binaries = moduleDirectory.resolve("bin");
        if (!Files.exists(binaries)) {
            Files.createDirectory(binaries);
        }
        return binaries;
    }

    private URLClassLoader getClassLoader(Set<Path> jars) {
        return new URLClassLoader(jars.stream()
                .map(Clause.wrap(this::jarToURL))
                .flatMap(Optional::stream)
                .toArray(URL[]::new));
    }

    private URL jarToURL(Path jar) throws MalformedURLException {
        if (!jar.toString().endsWith("jar")) {
            throw new IllegalArgumentException(jar + " is an invalid binary");
        }
        return jar.toUri().toURL();
    }
}
