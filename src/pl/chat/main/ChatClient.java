package pl.chat.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    private static List<String> users;
    private static String hostName = "localhost";
    private static int port = 7777;

    public static List<String> getUsers() {
        return users;
    }

    public static void setUsers(List<String> users) {
        ChatClient.users = users;
    }

    public ChatClient(String hostName, int port) {
        users = new ArrayList<>();
        this.hostName = hostName;
        this.port = port;
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient(hostName, port);
        client.execute();
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            new WriteThread(socket, this).start();
            new ReadThread(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public boolean isOnlyOne(String userNick) {
        System.out.println(userNick);
        return true;
    }
}
