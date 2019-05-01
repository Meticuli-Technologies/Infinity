package com.meti.lib.module;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    public ModuleManager addModule(Module module) {
        modules.put(module.name, module);
        return this;
    }
}
