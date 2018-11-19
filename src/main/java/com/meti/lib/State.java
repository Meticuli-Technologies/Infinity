package com.meti.lib;

import com.meti.lib.collect.ClassMap;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class State extends ClassMap {
    public State(Object... objects) {
        super(objects);
    }

    public Stage getPrimaryStage() {
        return firstOfType(Stage.class).orElse(new Stage());
    }

    public Client<?> getClient() {
        return getAndThrowIfNotExists(Client.class);
    }
    
    public Server getServer(){
        return getAndThrowIfNotExists(Server.class);
    }

    public ExecutorService getService() {
        return getAndThrowIfNotExists(ExecutorService.class);
    }

    private <T> T getAndThrowIfNotExists(Class<T> aClass){
        Optional<T> tOptional = firstOfType(aClass);
        if(tOptional.isPresent()){
            return tOptional.get();
        }
        else{
            throw new IllegalStateException("Objects of type " + aClass.getSimpleName() + " not found");
        }
    }

    public Logger getLogger() {
        return getLogger(State.class);
    }

    public Logger getLogger(Class<?> aClass) {
        return firstOfType(Logger.class).orElse(LoggerFactory.getLogger(aClass));
    }

    public Properties getProperties() {
        return firstOfType(Properties.class).orElse(new Properties());
    }
}
