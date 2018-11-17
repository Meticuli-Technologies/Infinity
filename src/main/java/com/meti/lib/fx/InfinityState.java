package com.meti.lib.fx;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;
import com.meti.lib.util.ClassMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class InfinityState extends ClassMap {
    public InfinityState(Object... objects) {
        super(objects);
    }

    public Client<?> getClient() {
        return getAndThrowIfNotExists(Client.class);
    }
    
    public Server getServer(){
        return getAndThrowIfNotExists(Server.class);
    }

    public <T> T getAndThrowIfNotExists(Class<T> aClass){
        Optional<T> tOptional = firstOfType(aClass);
        if(tOptional.isPresent()){
            return tOptional.get();
        }
        else{
            throw new IllegalStateException("Objects of type " + aClass.getSimpleName() + " not found");
        }
    }

    public Logger getLogger() {
        return getLogger(InfinityState.class);
    }

    public Logger getLogger(Class<?> aClass) {
        return firstOfType(Logger.class).orElse(LoggerFactory.getLogger(aClass));
    }

    public Properties getProperties() {
        return firstOfType(Properties.class).orElse(new Properties());
    }
}
