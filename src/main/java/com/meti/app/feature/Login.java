package com.meti.app.feature;

import java.io.Serializable;

public class Login implements Serializable {
    public final String username;

    public Login(String username) {
        this.username = username;
    }
}
