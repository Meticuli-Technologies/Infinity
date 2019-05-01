package com.meti.lib.fx;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class URLs {
    private URLs() {
    }

    public static URL getResource(String name) {
        return checkResource(name, URLs.class.getResource(name));
    }

    private static URL checkResource(String name, URL resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Could not find resource: " + name);
        }
        return resource;
    }
}
