package com.meti.app;

import com.meti.app.control.LoginRequest;
import com.meti.lib.event.ServerComponent;
import com.meti.lib.net.AbstractHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class UserManager extends ServerComponent<UserEvent, User> {
    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return Stream.of(
                new AbstractHandler<>(
                        new TypePredicate<>(LoginRequest.class),
                        new TypeFunction<>(LoginRequest.class).andThen((Function<LoginRequest, User>) login -> {
                            String username = login.username;
                            return new User(username);
                        })
                )
        );
    }
}
