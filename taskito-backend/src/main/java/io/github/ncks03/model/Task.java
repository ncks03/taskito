package io.github.ncks03.model;

import java.util.Date;
import java.util.UUID;

public class Task {
    private final UUID id;
    private final Date dateCreated;
    private String description;
    private boolean completed;

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.dateCreated = new Date();
        this.description = description;
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
