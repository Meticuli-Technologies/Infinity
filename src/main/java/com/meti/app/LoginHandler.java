package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.ClientHandler;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
class LoginHandler extends ClientHandler implements Function<Object, Login.LoginResponse> {
    private final Consumer<User> consumer;

    public LoginHandler(Client client, Consumer<User> consumer) {
        super(client);
        this.consumer = consumer;
    }

    @Override
    public Login.LoginResponse apply(Object o) {
        return new Login.LoginResponse("Successfully logged in with username: " + processUser((Login) o).username);
    }

    private Login processUser(Login login) {
        User user = new User(login.username, client);
        consumer.accept(user);
        return login;
    }
}
