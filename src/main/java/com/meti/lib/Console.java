package com.meti.lib;

public abstract class Console {
    public void log(String user, Level level, String message, Exception exception){

    }

    public abstract void handle(String message);
}
