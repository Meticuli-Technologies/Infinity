package com.meti.app.io.update.client;

import com.meti.app.io.update.Update;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public interface UpdateHandler extends Predicate<Update>, Consumer<Update> {
}