package com.meti.lib.event;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager<E extends Event> extends HashMap<E, Consumer<E>> {
}
