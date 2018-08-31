package pl.chat.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {
    private int port = 7777;
    private List<String> userNames = new LinkedList<>();
    private List<UserThread> userThreads = new LinkedList<>();

    public List<String> getUserNames() {
        return userNames;                                           //  this.userNames
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.execute();
    }

    private void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New user connected.");
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server." + e.getMessage());
            e.printStackTrace();
        }
    }

    //  delivers message to all users

    public void broadcast(String message) {
        for (UserThread u : userThreads) {
            u.sendMessage(message);
        }
    }

    //  stores username of the newly connected client

    public void addUserName(String userName) {
        userNames.add(userName);
    }

    //  remove userThread and associated username when a client disconnected

    public void userRemove(String username, UserThread user) {
        userNames.remove(username);
        userThreads.remove(user);
        System.out.println("The user " + username + " disconnected.");
    }

    public boolean hasUsers() {
        return !userNames.isEmpty();                                //  !this.userNames.isEmpty();
    }
}
