package com.meti.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
    private final ObservableList<String> messages = FXCollections.observableArrayList();

    /*public static class Add extends CommandHandler.CommandHandlerImpl {
        @Override
        public boolean test(Command command) {
            return command.args.get(0).equals("chat") && command.args.get(1).equals("add");
        }        @Override
        public void accept(Command command) {
            try {
                state.byClassToSingle(Chat.class)
                        .orElseThrow(() -> new NoSuchElementException("Chat not found"))
                        .messages.add(command.args.get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }*/
}
