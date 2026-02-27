package org.example;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Project> projects;
    private static ProjectStats stats = new ProjectStats();

    public TaskManager() {
        this.projects = new ArrayList<>();
    }

    public void addProject(Project project) {
        projects.add(project);
        System.out.println("Проект добавлен: " + project.getName());
    }

    public void showAllProjects() {
        System.out.println("ВСЕ ПРОЕКТЫ");
        for (Project p : projects) {
            p.showTasks();
        }
    }

    public static class ProjectStats {
        private int totalTasks = 0;
        private int completedTasks = 0;

        public void calculate(List<Project> projects) {
            totalTasks = 0;
            completedTasks = 0;
            for (Project p : projects) {
                for (Task t : p.getTasks()) {
                    totalTasks++;
                    if (t.getStatus() == TaskStatus.DONE) {
                        completedTasks++;
                    }
                }
            }
        }

        public void printStats() {
            System.out.println("СТАТИСТИКА");
            System.out.println("Всего задач: " + totalTasks);
            System.out.println("Выполнено: " + completedTasks);
        }
    }

    public void showStats() {
        stats.calculate(projects);
        stats.printStats();
    }
}