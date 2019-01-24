package com.meti.lib.math;

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
}
