package com.meti.lib.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class Command implements Serializable {
    public final List<String> args = new ArrayList<>();

    public Command(String... args) {
        this.args.addAll(Arrays.asList(args));
    }

    public Command(Collection<String> args) {
        this.args.addAll(args);
    }

    @Override
    public String toString() {
        return String.join(" ", args.toArray(new String[0]));
    }
}
