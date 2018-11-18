package com.meti.app;

import com.meti.lib.State;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class InputParser {
    private State state;
    private ServerDisplay serverDisplay;
    private final Map<Predicate<String>, Consumer<String[]>> inputMap = new HashMap<>();

    {
        inputMap.put(s -> s.startsWith("exit"), strings -> {
            state.getLogger().info("Exiting application");
            Platform.exit();
        });
    }

    public InputParser(State state, ServerDisplay serverDisplay) {
        this.state = state;
        this.serverDisplay = serverDisplay;
    }

    void parseToken(String input) {
        if (inputMap.size() != 0) {
            inputMap.keySet()
                    .stream()
                    .filter(stringPredicate -> stringPredicate.test(input))
                    .map(inputMap::get)
                    .forEach(consumer -> {
                        String[] parts = input.split(" ");
                        consumer.accept(Arrays.copyOfRange(parts, 1, parts.length));
                    });
        } else {
            throw new IllegalStateException("No values found inside of inputMap!");
        }
    }
}
