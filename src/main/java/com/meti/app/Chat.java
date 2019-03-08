package com.meti.app;

import com.meti.lib.net.Command;
import com.meti.lib.net.token.CommandHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.NoSuchElementException;

public class Chat {
    private final ObservableList<String> messages = FXCollections.observableArrayList();

    public static class Add extends CommandHandler.CommandHandlerImpl {
        @Override
        public void accept(Command command) {
            try {
                state.byClassToSingle(Chat.class)
                        .orElseThrow(() -> new NoSuchElementException("Chat not found"))
                        .messages.add(command.args.get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean test(Command command) {
            return command.args.get(0).equals("chat") && command.args.get(1).equals("add");
        }
    }
}
