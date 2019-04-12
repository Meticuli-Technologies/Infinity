package com.meti.lib.asset;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Predicate;

public interface AssetBuilder<A extends Asset> extends Predicate<Path>, Function<Path, A> {
}
