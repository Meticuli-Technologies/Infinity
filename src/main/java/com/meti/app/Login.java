package com.meti.app;

import com.meti.lib.CachedResponse;
import com.meti.lib.Respondable;

public class Login implements Respondable<Login.LoginResponse> {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Class<? extends LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    public class LoginResponse extends CachedResponse<String> {
        public LoginResponse(String cache) {
            super(cache);
        }
    }
}
