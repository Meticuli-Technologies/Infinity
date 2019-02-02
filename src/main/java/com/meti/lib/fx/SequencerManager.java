package com.meti.lib.fx;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/2/2019
 */
public class SequencerManager<T> {
    public final Map<String, Sequencer<T>> sequences = new HashMap<>();
}
