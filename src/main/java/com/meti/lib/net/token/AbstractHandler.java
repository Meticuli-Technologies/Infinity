package com.meti.lib.net.token;

import com.meti.lib.State;

import java.util.Optional;

public abstract class AbstractHandler<T> implements TokenHandler<T> {
    private State state;

    @Override
    public Optional<State> getState() {
        return Optional.ofNullable(state);
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }
}
