package com.meti.lib;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class ServiceSubmitter implements Function<Callable<Optional<Exception>>, Future<Optional<Exception>>> {
    private final ExecutorService service;

    public ServiceSubmitter(ExecutorService service) {
        this.service = service;
    }

    @Override
    public Future<Optional<Exception>> apply(Callable<Optional<Exception>> optionalCallable) {
        return service.submit(optionalCallable);
    }
}
