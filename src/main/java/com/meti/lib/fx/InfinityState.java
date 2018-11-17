package com.meti.lib.fx;

import com.meti.lib.util.ClassMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class InfinityState extends ClassMap {
    public InfinityState(Object... objects) {
        super(objects);
    }

    public Logger getLogger(Controller controller) {
        return firstOfType(Logger.class).orElse(LoggerFactory.getLogger(controller.getClass()));
    }

    public Properties getProperties() {
        return firstOfType(Properties.class).orElse(new Properties());
    }
}
