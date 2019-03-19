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
    private final Consumer<User> userConsumer;

    public LoginHandler(Client client, Consumer<User> userConsumer) {
        super(client);
        this.userConsumer = userConsumer;
    }

    @Override
    public Login.LoginResponse apply(Object o) {
        return new Login.LoginResponse("Successfully logged in with username: " + processUser((Login) o).username);
    }

    private Login processUser(Login login) {
        User user = new User(login.username, client);
        userConsumer.accept(user);
        return login;
    }
}
