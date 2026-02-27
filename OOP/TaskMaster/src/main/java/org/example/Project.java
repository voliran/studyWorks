package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Project implements Manageable {
    private String name;
    private List<Task> tasks;
    private String manager;

    public Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Задача добавлена: " + task.getTitle());
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void showTasks() {
        System.out.println("Проект: " + name);
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    public static class TaskComparator implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getTitle().compareTo(t2.getTitle()); // по названию
        }
    }

    public void sortTasks() {
        Collections.sort(tasks, new TaskComparator());
        System.out.println("Задачи отсортированы");
    }


    @Override
    public void assign(String person) {
        this.manager = person;
        System.out.println("Менеджер проекта: " + person);
    }

    @Override
    public void start() {
        System.out.println("Проект " + name + " начат");
    }

    @Override
    public void complete() {
        System.out.println("Проект " + name + " завершен");
    }

    public String getName() { return name; }
}