package org.example;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        int port = 15322;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен, ожидание подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientMessage = input.readLine();
                System.out.println("Получено сообщение от клиента: " + clientMessage);

                output.println("Эхо: " + clientMessage);

                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());;
        }
    }
}

