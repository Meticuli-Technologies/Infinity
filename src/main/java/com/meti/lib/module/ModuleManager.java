package com.meti.lib.module;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    public ModuleManager add(Module module) {
        modules.put(module.name, module);
        return this;
    }

    public <T> Stream<Class<? extends T>> getImplementations(Class<T> tClass) {
        return getModules(modules.keySet()).flatMap(module -> module.getImplementations(tClass));
    }

    public Stream<Module> getModules(Set<String> names) {
        return names.stream()
                .map(this::getModule)
                .flatMap(Optional::stream);
    }

    public Optional<Module> getModule(String name) {
        return Optional.ofNullable(modules.get(name));
    }
}
