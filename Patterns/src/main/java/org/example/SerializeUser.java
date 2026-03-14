package org.example;

import java.io.*;

public class SerializeUser {


    public static void main(String[] args) {

        User user = new User("Orozzy", "zewExz223");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {
            User newUser = (User) ois.readObject();
            System.out.println("Логин: " + newUser.getLogin() + " пароль: " + newUser.getPassword());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
