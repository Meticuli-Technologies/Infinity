package com.meti.chat.view;

import com.meti.handle.TypeTokenHandler;
import javafx.application.Platform;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class FXConsumerHandler<T> extends TypeTokenHandler<T> {
    private Consumer<T> tConsumer;

    public FXConsumerHandler(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected void handleGeneric(T token) {
        if (tConsumer != null) {
            Platform.runLater(() -> tConsumer.accept(token));
        } else {
            throw new IllegalStateException("Consumer has not been set!");
        }
    }

    public void setConsumer(Consumer<T> tConsumer) {
        this.tConsumer = tConsumer;
    }
}
