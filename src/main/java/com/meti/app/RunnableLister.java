package com.meti.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class RunnableLister {
    Optional<String> createTaskString(List<Runnable> tasks) {
        if (tasks.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(createTaskListString(tasks));
        }
    }

    private String createTaskListString(List<Runnable> tasks) {
        return "The ExecutorService has been shutdown, " + "but the following tasks were awaiting execution:\n\t" +
                listTasks(tasks);
    }

    private String listTasks(List<Runnable> runnables) {
        return runnables.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
