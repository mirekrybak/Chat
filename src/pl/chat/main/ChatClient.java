package pl.chat.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ChatClient {
    private Set<String> users;
    private static String hostName = "localhost";
    private static int port = 7777;
    private String userName;

    public Set<String> getUsers() {
        return users;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChatClient(String hostName, int port) {
        users = new LinkedHashSet<>();
        this.hostName = hostName;
        this.port = port;
    }

    public boolean checkNickMultiply(String nick) {
        boolean multiply = false;
        try {                                               // delay to send connected users
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String s : users) {
            if (s.equals(nick)) {
                multiply = true;
                System.out.println("Nick zajęty !!!!!");
                break;
            }
        }
        return multiply;
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
}
