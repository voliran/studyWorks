package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Project p1 = new Project("Сайт");
        Project p2 = new Project("Приложение");

        manager.addProject(p1);
        manager.addProject(p2);

        Task t1 = new Task("Дизайн", "Нарисовать", "26-02-2026");
        Task t2 = new Task("Верстка", "Сверстать", "26-02-2026");
        Task t3 = new Task("Тесты", "Проверить", "26-02-2026");

        p1.addTask(t1);
        p1.addTask(t2);
        p2.addTask(t3);

        t1.setStatus(TaskStatus.DONE);
        t2.setStatus(TaskStatus.IN_PROGRESS);

        p1.assign("Анна");
        p1.start();

        p2.assign("Иван");
        p2.start();
        p1.showTasks();
        p2.showTasks();

        System.out.println("\nСОРТИРОВКА");
        p1.sortTasks();
        p1.showTasks();

        manager.showStats();

        manager.showAllProjects();
    }
}