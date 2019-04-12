package com.meti.app;

import com.meti.app.control.LoginRequest;
import com.meti.lib.net.AbstractHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.util.HashMap;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class UserManager extends HashMap<User, Client> {
    public Handler<Object> getLoginHandler(Client client) {
        return new AbstractHandler<>(new TypePredicate<>(LoginRequest.class), new TypeFunction<>(LoginRequest.class).andThen(new Function<LoginRequest, User>() {
            @Override
            public User apply(LoginRequest login) {
                String username = login.username;
                User user = new User(username);
                put(user, client);
                return user;
            }
        }));
    }
}
