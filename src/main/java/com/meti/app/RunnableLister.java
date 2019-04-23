package com.meti.app;

import java.util.List;
import java.util.stream.Collectors;

class RunnableLister {
    String createTaskString(List<Runnable> tasks) {
        if (tasks.isEmpty()) {
            return "The ExecutorService has been shutdown with no tasks awaiting execution.";
        } else {
            return createTaskListString(tasks);
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
