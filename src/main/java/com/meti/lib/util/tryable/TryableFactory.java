package com.meti.lib.util.tryable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class TryableFactory {
    public <T> Consumer<T> constructConsumerFrom(TryableConsumer<T> consumer) {
        return new ConsumerImpl<>(consumer);
    }

    public <T, R> Function<T, Optional<R>> apply(TryableFunction<T, R> function) {
        return new FunctionImpl<>(function);
    }

    public <T> Supplier<Optional<T>> get(TryableSupplier<T> supplier) {
        return new SupplierImpl<>(supplier);
    }

    protected abstract void handle(Exception e);

    private class ConsumerImpl<T> implements Consumer<T> {
        private final TryableConsumer<T> consumer;

        ConsumerImpl(TryableConsumer<T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void accept(T t) {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    private class FunctionImpl<T, R> implements Function<T, Optional<R>> {
        private final TryableFunction<T, R> function;

        FunctionImpl(TryableFunction<T, R> function) {
            this.function = function;
        }

        @Override
        public Optional<R> apply(T t) {
            try {
                return Optional.ofNullable(function.apply(t));
            } catch (Exception e) {
                return handleException(e);
            }
        }

        Optional<R> handleException(Exception e) {
            handle(e);
            return Optional.empty();
        }
    }

    private class SupplierImpl<T> implements Supplier<Optional<T>> {
        private final TryableSupplier<T> supplier;

        SupplierImpl(TryableSupplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public Optional<T> get() {
            try {
                return Optional.ofNullable(supplier.get());
            } catch (Exception e) {
                return handleException(e);
            }
        }

        Optional<T> handleException(Exception e) {
            handle(e);
            return Optional.empty();
        }
    }
}
