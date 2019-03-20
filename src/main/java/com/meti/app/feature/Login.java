package com.meti.app.feature;

import com.meti.lib.net.Query;
import com.meti.lib.respond.CachedResponse;
import com.meti.lib.respond.Respondable;

public class Login extends Query<Login.LoginResponse> implements Respondable<Login.LoginResponse> {
    public final String username;

    public Login(String username) {
        super(LoginResponse.class);
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
