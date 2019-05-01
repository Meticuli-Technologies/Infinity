package com.meti.lib.module;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    public ModuleManager addModule(Module module) {
        modules.put(module.name, module);
        return this;
    }

    public Optional<Module> getModule(String name) {
        return Optional.ofNullable(modules.get(name));
    }

    public Set<Module> getModules(String... names) {
        if (names.length == 0) {
            return new HashSet<>(modules.values());
        } else {
            return getModulesWithNames(names);
        }
    }

    private Set<Module> getModulesWithNames(String[] names) {
        return Arrays.stream(names)
                .map(this::getModule)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
