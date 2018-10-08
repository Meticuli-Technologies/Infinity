package com.meti.control.depend;

import com.meti.control.depend.Dependency;
import com.meti.control.depend.DependencyFactory;
import com.meti.control.depend.exception.ExceptionDependency;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public class ExceptionDependencyFactory extends DependencyFactory {
    //TODO: revise, feels like Consumer<Exception> is said more than enough times
    public final Supplier<Consumer<Exception>> supplier;

    public ExceptionDependencyFactory(Supplier<Consumer<Exception>> supplier) {
        super("ExceptionDependency");
        this.supplier = supplier;
    }

    @Override
    public Dependency create() {
        return new ExceptionDependency() {
            @Override
            public void accept(Exception e) {
                supplier.get().accept(e);
            }
        };
    }
}
