package com.meti.app;

import java.io.Serializable;

public class Login implements Serializable {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
