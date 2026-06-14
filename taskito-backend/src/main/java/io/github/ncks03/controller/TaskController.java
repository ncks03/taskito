package io.github.ncks03.controller;

import io.github.ncks03.model.Task;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TaskController {

    private final List<Task> tasks;

    public TaskController() {
        this.tasks = new ArrayList<>();
    }

    public TaskController(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Optional<Task> getTask(UUID id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }
}
