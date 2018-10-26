package com.meti.app.server;

import com.meti.app.server.command.Command;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public class CommandHandler extends TokenHandler<Command> {
    private static final Set<? extends CommandConsumer<?>> consumers = new HashSet<>();


    {

    }

    public CommandHandler(Class<Command> tokenClass) {
        super(tokenClass);
    }

    @Override
    protected Object handleToken(Command token) {
        //TODO: multiple results?
        return consumers.stream()
                .filter((Predicate<CommandConsumer<?>>) commandConsumer -> commandConsumer.getCommandClass().isAssignableFrom(token.getClass()))
                .map((Function<CommandConsumer<?>, Object>) commandConsumer -> commandConsumer.processObject(token))
                .collect(Collectors.toList()).get(0);
    }

    private abstract class CommandConsumer<R extends Serializable> {
        public R processObject(Object obj) {
            return process(getCommandClass().cast(obj));
        }

        protected abstract R process(Command<? extends R> command);

        protected abstract Class<? extends Command<? extends R>> getCommandClass();
    }
}
