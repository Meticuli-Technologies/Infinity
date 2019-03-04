package com.meti.lib.net.token;

import com.meti.lib.net.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandHandler extends TypeHandler<Command> {
    private final Map<Predicate<Command>, Consumer<Command>> map = new HashMap<>();

    public CommandHandler() {
        super(Command.class);
    }

    @Override
    public void acceptCast(Command obj) {
        List<Consumer<Command>> list = map.keySet()
                .stream()
                .filter(commandPredicate -> commandPredicate.test(obj))
                .map(map::get)
                .peek(commandConsumer -> commandConsumer.accept(obj))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new IllegalArgumentException(obj + " is an unprocessable command");
        }
    }
}
