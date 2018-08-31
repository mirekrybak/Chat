package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    // private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            printUsers(writer);
            String userName = reader.readLine();
            server.addUserName(userName);
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage);
            String clientMessage;
            while (!(clientMessage = reader.readLine()).equals("bye")) {
                serverMessage = "[" + userName + "]" + clientMessage;
                server.broadcast(serverMessage);
            }

            server.userRemove(userName, this);
            socket.close();

            serverMessage = userName + " has quited.";
            server.broadcast(serverMessage);
        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printUsers(PrintWriter writer) {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other user connected.");
        }
    }

    public void sendMessage(String message) {
        System.out.println(message);
    }
}
