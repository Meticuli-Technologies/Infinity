package com.meti.app.control;

import com.meti.lib.net.Request;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class LoginRequest implements Request {
    public final String username;

    public LoginRequest(String username) {
        this.username = username;
    }
}
