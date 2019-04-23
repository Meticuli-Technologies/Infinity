package com.meti.app;

import com.meti.lib.State;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
        try {
            terminateExecutor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminateExecutor() throws Exception {
        String taskString = createTaskString();
        boolean terminationSuccessful = hasTerminated();
    }

    public boolean hasTerminated() throws Exception {
        if (!service.isTerminated()) {
            if (!terminationSuccessful()) {
                throw new TimeoutException("Service failed to terminate within " + AWAIT_TERMINATION.toString());
            } else {
                return true;
            }
        }

        return true;
    }

    public boolean terminationSuccessful() throws InterruptedException {
        return !service.awaitTermination(AWAIT_TERMINATION.toMillis(), TimeUnit.MILLISECONDS);
    }

    public String createTaskString() {
        return createTaskString(service.shutdownNow());
    }

    private String createTaskString(List<Runnable> tasks) {
        if (tasks.isEmpty()) {
            return createEmptyTaskString();
        } else {
            return createPresentTaskString(tasks);
        }
    }

    private String createEmptyTaskString() {
        return "The ExecutorService has been shutdown with no tasks awaiting execution.";
    }

    private String createPresentTaskString(List<Runnable> tasks) {
        return "The ExecutorService has been shutdown, " + "but the following tasks were awaiting execution:\n\t" +
                collectTasks(tasks);
    }

    private String collectTasks(List<Runnable> runnables) {
        return runnables.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
