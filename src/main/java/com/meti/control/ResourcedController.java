package com.meti.control;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public abstract class ResourcedController extends Controller {
    public final URL[] resources = getResources();

    private URL[] getResources() {
        String[] resourceStrings = getResourcesStrings();
        URL[] urls = new URL[resourceStrings.length];

        for (int i = 0; i < resourceStrings.length; i++) {
            urls[i] = getResource(resourceStrings[i]);
        }

        return urls;
    }

    private URL getResource(String resourceString) {
        URL result = getClass().getResource(resourceString);

        if (result != null) {
            return result;
        }

        throw new IllegalArgumentException("Invalid resource: " + resourceString);
    }

    public abstract String[] getResourcesStrings();
}
