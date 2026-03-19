package org.example;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public void loadFromFile() {

        try {
            if (Files.exists(Paths.get("users"))) {
                FileChannel channel = FileChannel.open(Paths.get("users"), StandardOpenOption.READ);

                long fileSize = channel.size();

                if (fileSize > 0) {

                    users.clear();

                    ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);

                    channel.read(buffer);

                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String content = new String(bytes);

                    String[] lines = content.split("\n");

                    for (String line : lines) {
                        if (!line.trim().isEmpty()) {
                            String[] data = line.split(",");
                            if (data.length == 2) {
                                User user = new User(data[0].trim(), data[1].trim());
                                users.add(user);
                            }
                        }
                    }

                    System.out.println("Данные успешно загружены из файла. Загружено пользователей: " + users.size());
                }

                channel.close();
            } else {
                System.out.println("Файл не найден. Будет создан новый файл при сохранении.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + e.getMessage());
        }
    }

    public void saveToFile(User user) {
        try (FileChannel channel = FileChannel.open(Paths.get("users"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {
            String userData = user.getName() + "," + user.getCity() + "\n";
            byte[] dataBytes = userData.getBytes();

            ByteBuffer buffer = ByteBuffer.wrap(dataBytes);

            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            System.out.println("Пользователь успешно сохранен в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении пользователя в файл: " + e.getMessage());
        }
    }

    public void addUser(String name, String city) {
        User user = new User(name, city);
        users.add(user);
        saveToFile(user);
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            System.out.println("\nСписок пользователей:");
            for (User user : users) {
                System.out.print(user.toString());
            }
            System.out.println();
        }
    }
}