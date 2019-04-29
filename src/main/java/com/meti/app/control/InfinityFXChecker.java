package com.meti.app.control;

import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.fx.FutureFXChecker;
import com.meti.lib.io.Server;

import java.util.concurrent.Future;
import java.util.logging.Level;

class InfinityFXChecker extends FutureFXChecker {
    private final InfinityState state;
    private final Level level;

    public InfinityFXChecker(Future<Server> future, InfinityState state, Level level) {
        super(future);
        this.state = state;
        this.level = level;
    }

    @Override
    public void accept(Exception e) {
      state.getConsole().log(level, e);
    }
}
