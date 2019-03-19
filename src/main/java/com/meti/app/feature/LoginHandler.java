package com.meti.app.feature;

import com.meti.app.User;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class LoginHandler extends ClientHandler implements Function<Object, Login.LoginResponse> {
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
