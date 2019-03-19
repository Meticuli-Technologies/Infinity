package com.meti.app.feature;

import com.meti.lib.respond.CachedResponse;
import com.meti.lib.respond.Respondable;

public class Login implements Respondable<Login.LoginResponse> {
    public final String username;

    public Login(String username) {
        this.username = username;
    }

    @Override
    public Class<? extends LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    public static class LoginResponse extends CachedResponse<String> {
        public LoginResponse(String cache) {
            super(cache);
        }
    }
}
