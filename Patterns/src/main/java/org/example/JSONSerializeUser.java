package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONSerializeUser {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User("Orozzy", "zewExz223");

        try  {
            mapper.writeValue(new File("user.json"), user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            User newUser = mapper.readValue(new File("user.json"), User.class);
            System.out.println("Логин: " + newUser.getLogin() + " пароль: " + newUser.getPassword());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

