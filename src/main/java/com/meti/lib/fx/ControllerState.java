package com.meti.lib.fx;

import com.meti.lib.manage.Manager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerState extends Manager<Class<?>, List<Object>> {
    public static ControllerState of(Object... objects) {
        ControllerState controllerState = new ControllerState();
        Arrays.stream(objects).forEach(o -> {
            Class<?> oClass = o.getClass();
            if (controllerState.containsKey(oClass)) {
                controllerState.get(oClass).add(oClass);
            } else {
                controllerState.put(oClass, Stream.of(o).collect(Collectors.toList()));
            }
        });
        return controllerState;
    }

    public <T> T getObject(Class<T> tClass) {
        return getObjects(tClass).get(0);
    }

    public <T> List<T> getObjects(Class<T> tClass) {
        List<Object> objects = get(tClass);
        if (objects != null) {
            return objects.stream()
                    .filter(o -> o.getClass().isAssignableFrom(tClass))
                    .map(tClass::cast)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("No entry found for " + tClass.getSimpleName());
        }
    }
}
