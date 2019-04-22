package com.meti.app;

import com.meti.lib.State;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    public static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);
    private final ExecutorService service;
    private final State mainState;

    public Infinity() {
        this.service = Executors.newCachedThreadPool();
        this.mainState = new State();
    }

    @Override
    public void start(Stage primaryStage) {
        mainState.add(primaryStage);
    }

    @Override
    public void stop() {
        List<Runnable> runnables = service.shutdownNow();
        StringBuilder taskString = createTaskString(runnables);
        //TODO: log taskString

        if (!service.isTerminated()) {
            try {
                boolean hasTerminated = service.awaitTermination(AWAIT_TERMINATION.toMillis(), TimeUnit.MILLISECONDS);
                if (!hasTerminated) {
                    //TODO: log "Service has not terminated successfully, some threads may still be running!"
                } else {
                    //TODO: log "Service terminated successfully!"
                }
            } catch (InterruptedException e) {
                //TODO: log e
            }
        }
    }

    private StringBuilder createTaskString(List<Runnable> tasks) {
        if (tasks.isEmpty()) {
            return createEmptyTaskString();
        } else {
            return createPresentTaskString(tasks);
        }
    }

    private StringBuilder createEmptyTaskString() {
        return new StringBuilder()
                .append("The ExecutorService has been shutdown with no tasks awaiting execution.");
    }

    private StringBuilder createPresentTaskString(List<Runnable> tasks) {
        return new StringBuilder()
                .append("The ExecutorService has been shutdown, " + "but the following tasks were awaiting execution:")
                .append(collectTasks(tasks));
    }

    private String collectTasks(List<Runnable> runnables) {
        return runnables.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
