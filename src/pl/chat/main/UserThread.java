package pl.chat.main;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);
            System.err.println("\t\t" + userName + " online.");     // String serverMessage = "\t\t" + userName + " online.";
            String serverMessage = null;
            //server.broadcast(serverMessage);
            String clientMessage;

            while (!(clientMessage = reader.readLine()).equals("bye")) {
                serverMessage = "[" + userName + "]: " + clientMessage;
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

    private void printUsers() {
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
