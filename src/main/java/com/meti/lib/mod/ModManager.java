package com.meti.lib.mod;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class ModManager {
    private final Set<ModBuilder> builders = new HashSet<>();
    private final Set<Mod> mods = new HashSet<>();
}
