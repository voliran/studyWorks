package com.example.tasks.model;

public class Task {

    private final long id;
    private final String title;
    private final boolean completed;

    public Task(long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
