package com.meti.app;

import com.meti.lib.net.command.GetCommand;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public class GetCommandEvaluator implements Evaluator {

    @Override
    public boolean canEvaluate(Object obj) {
        return GetCommand.class.isAssignableFrom(obj.getClass());
    }

    @Override
    public void evaluate(Object obj) throws Exception {

    }
}
