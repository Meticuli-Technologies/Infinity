package com.meti.lib.net.token;

import com.meti.app.Chat;
import com.meti.lib.State;
import com.meti.lib.net.Command;
import com.meti.lib.util.StateNotSetException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandHandler extends TypeHandler<Command> {
    private final Map<Predicate<Command>, Consumer<Command>> map = new HashMap<>();

    public CommandHandler() {
        super(Command.class);

        map.put(command -> command.args.get(0).equals("chat") && command.args.get(1).equals("add"), command -> {
            State state = getState().orElseThrow(new StateNotSetException());
            try {
                state.byClassToSingle(Chat.class)
                        .orElseThrow(() -> new NoSuchElementException("Chat not found"))
                        .add(command.args.get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
