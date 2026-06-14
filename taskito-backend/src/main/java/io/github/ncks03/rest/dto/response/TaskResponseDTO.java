package io.github.ncks03.rest.dto.response;

import io.github.ncks03.model.Task;

import java.util.Date;
import java.util.UUID;

public record TaskResponseDTO(UUID id, Date dateCreated, String description, boolean completed) {
    public static TaskResponseDTO fromTask(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getDateCreated(),
                task.getDescription(),
                task.isCompleted()
        );
    }
}
