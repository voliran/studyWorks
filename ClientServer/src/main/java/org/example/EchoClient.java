package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {
        int port = 15322;
        String serverAddress = "127.0.0.1";

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Подключение к серверу...");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Привет, сервер!");
            System.out.println("Отправлено сообщение: Привет, сервер!");

            String response = input.readLine();
            System.out.println("Ответ от сервера: " + response);
        } catch (IOException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());;
        }
    }
}
