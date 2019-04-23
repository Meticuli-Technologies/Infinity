package com.meti.lib.util;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class URLUtil {
    private URLUtil() {
    }

    public static URL getResource(String name) {
        return getResource(URLUtil.class, name);
    }

    public static URL getResource(Class<?> clazz, String name) {
        return checkResource(name, clazz.getResource(name));
    }

    private static URL checkResource(String name, URL resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Could not find resource: " + name);
        }
        return resource;
    }
}
