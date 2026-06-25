package io.github.ncks03.model;

import java.util.Date;
import java.util.UUID;

public class Task {
    private final UUID id;
    private final Date dateCreated;
    private String description;
    private boolean completed;

    public Task() {
        this.id = UUID.randomUUID();
        this.dateCreated = new Date();
        this.description = "";
        this.completed = false;
    }

    public Task(String description) {
        this();
        this.description = description;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
