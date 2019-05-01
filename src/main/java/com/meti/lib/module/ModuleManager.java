package com.meti.lib.module;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    protected void add(Module module) {
        modules.put(module.name, module);
    }

    public <T> Stream<Class<?>> getImplementations(Class<T> tClass) {
        return getModules(modules.keySet()).flatMap(module -> module.getImplementations(tClass));
    }

    private Stream<Module> getModules(Set<String> names) {
        return names.stream()
                .map(this::getModule)
                .flatMap(Optional::stream);
    }

    private Optional<Module> getModule(String name) {
        return Optional.ofNullable(modules.get(name));
    }
}
