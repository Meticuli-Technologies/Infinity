package com.meti.lib.net.token;

import com.meti.lib.net.Command;

public class CommandHandler extends TypeHandler<Command> {
    public CommandHandler() {
        super(Command.class);
    }

    @Override
    public void acceptCast(Command obj) {

    }
}
