package com.meti.control.depend.exception;

import com.meti.control.depend.Dependency;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public abstract class ExceptionDependency extends Dependency implements Consumer<Exception> {
    public ExceptionDependency() {
        super("ExceptionDependency");
    }
}
