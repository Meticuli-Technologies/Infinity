package com.meti.lib;

import com.meti.lib.handle.AbstractHandler;
import com.meti.lib.handle.CollectionConsumer;
import com.meti.lib.handle.HandlerMap;
import com.meti.lib.handle.TypePredicate;
import com.meti.lib.util.CollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class State {
    private final HandlerMap<Object, StateHandler<?>> map = new HandlerMap<>();

    public <T> Optional<T> byClassToSingle(Class<T> tClass) throws Exception {
        List<T> list = byClass(tClass);
        return CollectionUtil.toSingle(list);
    }

    public <T> List<T> byClass(Class<T> tClass) throws Exception {
        TryableFactory factory = new TryableFactory();
        return factory.checkAll(map.stream()
                        .filter(stateHandler -> stateHandler.getPredicate().orElseThrow().testClass(tClass))
                        .map(AbstractHandler::getConsumer)
                        .flatMap(Optional::stream)
                        .map(CollectionConsumer::getCollection)
                        .flatMap(Collection::stream)
                        .map(factory.apply(tClass::cast))
                        .flatMap(Optional::stream)
                        .collect(Collectors.toList()));
    }

    public State createSubState() {
        State state = new State();
        add(state);
        return state;
    }

    public void add(Object obj) {
        List<StateHandler<?>> handlers = map.stream()
                .filter(stateHandler -> stateHandler.getPredicate().orElseThrow().test(obj))
                .collect(Collectors.toList());

        if (handlers.isEmpty()) {
            StateHandler<?> handler = new StateHandler<>(obj.getClass());
            handler.accept(obj);
            map.add(handler);
        }
    }

    public Stream<Object> stream() {
        return Stream.empty();
    }

    private class StateHandler<T> extends AbstractHandler<Object, TypePredicate<T>, CollectionConsumer<Object, List<Object>>> {
        public StateHandler(Class<T> tClass) {
            super(new TypePredicate<>(tClass), CollectionConsumer.asList());
        }
    }
}
