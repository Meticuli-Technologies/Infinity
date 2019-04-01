package com.meti.lib.event;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventHandler<E extends Event> extends HashMap<E, Consumer<E>> {
}
