package io.github.ncks03.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.ncks03.model.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TaskController {

    private final JsonMapper mapper;
    private final Path taskFilePath;

    private List<Task> tasks;

    @Inject
    public TaskController(@ConfigProperty(name = "taskito.tasks.file.location") String taskFileLocation) {
        this.mapper = new JsonMapper();

        this.taskFilePath = Paths.get(taskFileLocation);

        if (!Files.exists(taskFilePath)) {
            try {
                Files.createDirectories(taskFilePath.getParent());
                Files.createFile(taskFilePath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating tasks file!", e);
            }
        }

        // Initialize tasks database
        this.tasks = new ArrayList<>();
        this.readTasks();
    }

    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
        this.writeTasks();
    }

    public void removeTask(UUID id) {
        this.tasks = this.tasks.stream()
                .filter(task -> !task.getId().equals(id))
                .toList();

        this.writeTasks();
    }

    public void update() {
        this.writeTasks();
        this.readTasks();
    }

    public Optional<Task> getTask(UUID id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    private void writeTasks() {
        try {
            this.mapper.writeValue(taskFilePath.toFile(), this.tasks);

        } catch (DatabindException | StreamReadException e) {
            throw new RuntimeException("Unexpected error while parsing tasks file!", e);

        } catch (IOException e) {
            throw new RuntimeException("Unexpected error while reading tasks file!", e);

        }
    }

    private void readTasks() {
        if (taskFilePath.toFile().length() > 0) {
            try {
                this.tasks = this.mapper.readValue(taskFilePath.toFile(), new TypeReference<List<Task>>() {});

            } catch (DatabindException | StreamReadException e) {
                throw new RuntimeException("Unexpected error while parsing tasks file!", e);

            } catch (IOException e) {
                throw new RuntimeException("Unexpected error while reading tasks file!", e);

            }
        }
    }
}
