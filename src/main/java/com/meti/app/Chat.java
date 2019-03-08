package com.meti.app;

import com.meti.lib.net.Command;
import com.meti.lib.net.token.CommandHandler;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Chat extends ArrayList<String> {
    public static class Add extends CommandHandler.CommandHandlerImpl {
        @Override
        public void accept(Command command) {
            try {
                state.byClassToSingle(Chat.class)
                        .orElseThrow(() -> new NoSuchElementException("Chat not found"))
                        .add(command.args.get(2));
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
