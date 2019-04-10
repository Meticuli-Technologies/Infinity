package com.meti.app;

import java.net.URL;

public class URLS {
    private URLS() {
    }

    public static URL getLocalURL() {
        return getResource("/com/meti/app/Local.fxml");
    }

    static URL getResource(String value) {
        return URLS.class.getResource(value);
    }

    static URL getMenuURL() {
        return getResource("/com/meti/app/Menu.fxml");
    }
}
