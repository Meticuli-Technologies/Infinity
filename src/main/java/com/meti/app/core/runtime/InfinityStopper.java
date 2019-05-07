package com.meti.app.core.runtime;

import java.time.Duration;

class InfinityStopper {
    void stopImpl(InfinityState state) throws Exception {
        state.getExecutorServiceManager().terminate(Duration.ofSeconds(1));
    }
}