package com.meti.lib.util;

import com.meti.lib.util.tryable.TryableFactory;

import java.util.Optional;
import java.util.stream.Stream;

public class Streams {
    private Streams(){}

    public static <T> Stream<? extends T> instanceStream(TryableFactory factory, Stream<Class<? extends T>> implStream) {
        return implStream.map(factory.apply(aClass -> aClass.getDeclaredConstructor()))
                .flatMap(Optional::stream)
                .map(factory.apply(constructor -> constructor.newInstance()))
                .flatMap(Optional::stream);
    }
}
