package com.meti.lib.net.token;

import com.meti.lib.State;
import com.meti.lib.net.Command;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandHandler extends TypeHandler<Command> {
    public final Set<CommandHandlerImpl> implementations = new HashSet<>();

    public CommandHandler() {
        super(Command.class);
    }

    @Override
    public void acceptCast(Command obj) {
        List<Consumer<Command>> list = implementations
                .stream()
                .filter(impl -> impl.test(obj))
                .peek(this::checkForHandler)
                .peek(commandConsumer -> commandConsumer.accept(obj))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new IllegalArgumentException(obj + " is an unprocessable command");
        }
    }

    private void checkForHandler(CommandHandlerImpl impl) {
        impl.setState(getStateOrThrow());
    }

    public static abstract class CommandHandlerImpl implements Predicate<Command>, Consumer<Command> {
        protected State state;

        public void setState(State state) {
            this.state = state;
        }
    }
}
