package com.meti.lib.collect;

import com.meti.lib.collect.event.Event;
import com.meti.lib.collect.event.EventManager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class Component<K extends Enum<?>, E extends Event> {
    public final EventManager<K, E> eventManager = new EventManager<>();
}
