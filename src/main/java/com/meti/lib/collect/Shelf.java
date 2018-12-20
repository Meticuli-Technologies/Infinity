package com.meti.lib.collect;

import java.util.HashSet;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class Shelf<O, P extends Predicate<O>> extends HashSet<ShelfBinding<O, P>> {
    public void addObject(O object) {
        stream().filter(opShelfBinding -> opShelfBinding.predicate.test(object))
                .map(binding -> binding.content)
                .forEach(os -> os.add(object));
    }
}
