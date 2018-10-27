package com.meti.app.server;

import com.meti.app.server.command.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public class CommandHandler extends TokenHandler<Command> {
    private static final Set<CommandConsumer<Serializable, ? extends Command>> consumers = new HashSet<>();

    {
        consumers.add(new RequestCommandConsumer());
    }

    public CommandHandler(Class<Command> tokenClass) {
        super(tokenClass);
    }

    @Override
    protected Object handleToken(Command token, Server server) {
        ArrayList<Object> results = new ArrayList<>();
        consumers.stream()
                .filter((Predicate<CommandConsumer<?, ?>>) commandConsumer -> commandConsumer.getCommandClass().isAssignableFrom(token.getClass()))
                .forEach((Consumer<CommandConsumer<?, ?>>) commandConsumer -> results.add(commandConsumer.processObject(token, server)));
        return results.get(0);
    }
}
