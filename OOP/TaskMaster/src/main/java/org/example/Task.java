package org.example;

public class Task {
    private String title;
    private String description;
    private String dueDate;
    private TaskStatus status;
    private String assignee;

    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = TaskStatus.TODO;
        this.assignee = null;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public TaskStatus getStatus() { return status; }
    public String getAssignee() { return assignee; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setAssignee(String assignee) { this.assignee = assignee; }

    @Override
    public String toString() {
        return "[" + status + "] " + title + " (" + dueDate + ")";
    }
}