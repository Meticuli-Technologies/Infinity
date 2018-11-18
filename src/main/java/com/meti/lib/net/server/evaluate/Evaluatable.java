package com.meti.lib.net.server.evaluate;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public interface Evaluatable<T> {
    Class<T> getParameterClass();
    boolean canEvaluate(Object obj);
    Serializable evaluate(T obj);

    default Serializable evaluateObject(Object obj) {
        if(getParameterClass().isAssignableFrom(obj.getClass())){
            return evaluate(getParameterClass().cast(obj));
        }
        else{
            throw new IllegalArgumentException("Class " +  obj.getClass().getSimpleName() + " is invalid for class " + getParameterClass().getSimpleName());
        }
    }
}
