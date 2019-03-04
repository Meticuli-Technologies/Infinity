package com.meti.lib.net.token;

import com.meti.lib.State;
import com.meti.lib.net.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandHandler extends TypeHandler<Command> {
    public final Map<Predicate<Command>, Consumer<Command>> map = new HashMap<>();

    public CommandHandler() {
        super(Command.class);
    }

    public <T extends Predicate<Command> & Consumer<Command>> void add(T obj){
        map.put(obj, obj);
    }

    @Override
    public void acceptCast(Command obj) {
        List<Consumer<Command>> list = map.keySet()
                .stream()
                .filter(commandPredicate -> commandPredicate.test(obj))
                .peek(this::checkForHandler)
                .map(map::get)
                .peek(commandConsumer -> commandConsumer.accept(obj))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new IllegalArgumentException(obj + " is an unprocessable command");
        }
    }

    private void checkForHandler(Predicate<Command> commandPredicate) {
    }

    public static abstract class CommandHandlerImpl implements Predicate<Command>, Consumer<Command> {
        protected State state;

        public void setState(State state) {
            this.state = state;
        }
    }
}
