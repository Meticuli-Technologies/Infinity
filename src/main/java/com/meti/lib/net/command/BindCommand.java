package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/18/2018
 */
public class BindCommand<P extends Collection<? extends String> & Serializable> extends Command<String, P>{
    public BindCommand(P collection) {
        super(collection);
    }
}
