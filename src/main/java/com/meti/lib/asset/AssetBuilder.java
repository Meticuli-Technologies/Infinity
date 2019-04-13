package com.meti.lib.asset;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface AssetBuilder<A extends Asset> extends Predicate<String>, BiFunction<InputStream, String, A> {
}
