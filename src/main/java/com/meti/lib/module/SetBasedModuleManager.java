package com.meti.lib.module;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class SetBasedModuleManager implements ModuleManager {
    private final Set<Module> modules = new HashSet<>();

    @Override
    public Set<Module> getModules() {
        return modules;
    }

    public void add(Module module) {
        modules.add(module);
    }
}
