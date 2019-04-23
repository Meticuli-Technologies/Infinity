package com.meti.app;

import java.util.logging.Level;

class InfinityStopper {
    void stopImpl(InfinityState state) throws Exception {
        terminateExecutor(state);
    }

    private void terminateExecutor(InfinityState state) throws Exception {
        logTaskString(state);
        state.getExecutorServiceManager().checkTerminated();
    }

    private void logTaskString(InfinityState state) {
        state.getExecutorServiceManager().getTaskString().ifPresentOrElse(s -> state.getConsole().log(Level.SEVERE, s),
                () -> state.getConsole().log(Level.INFO, "The ExecutorService has been shutdown with no tasks awaiting execution.")
        );
    }
}