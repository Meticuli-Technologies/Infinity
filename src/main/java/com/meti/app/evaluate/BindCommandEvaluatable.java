package com.meti.app.evaluate;

import com.meti.lib.net.command.BindCommand;
import com.meti.lib.net.server.evaluate.Evaluatable;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/18/2018
 */
public class BindCommandEvaluatable implements Evaluatable<BindCommand> {
    @Override
    public Class<BindCommand> getParameterClass() {
        return BindCommand.class;
    }

    @Override
    public boolean canEvaluate(Object obj) {
        return BindCommand.class.isAssignableFrom(obj.getClass());
    }

    @Override
    public Serializable evaluate(BindCommand obj) {
        return null;
    }
}
