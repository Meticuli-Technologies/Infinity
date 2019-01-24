package com.meti.lib.math;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Vector {
    public final int[] arguments;

    public Vector(int... arguments) {
        this.arguments = arguments;
    }

    public static Vector empty(int dimensions) {
        int[] zeroes = new int[dimensions];
        for (int i = 0; i < dimensions; i++) {
            zeroes[i] = 0;
        }
        return new Vector(zeroes);
    }

    public Vector add(Vector other) {
        int[] newArgs = new int[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            newArgs[i] = arguments[i] + other.arguments[i];
        }
        return new Vector(newArgs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(arguments, vector.arguments);
    }

    @Override
    public String toString() {
        return Arrays.stream(arguments)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ", "{", "}"));
    }
}
