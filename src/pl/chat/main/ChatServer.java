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
                System.err.println("\t\tNew user connected.");
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
        /*
        for (UserThread u : userThreads) {
            u.sendMessage(message + " test");
        }
        */
        System.out.println(message);
    }

    //  stores username of the newly connected client

    public void addUserName(String userName) {
        userNames.add(userName);
    }

    //  remove userThread and associated username when a client disconnected

    public void userRemove(String username, UserThread user) {
        boolean removeed = userNames.remove(username);
        if (removeed) {
            userThreads.remove(user);
            System.err.println("The user " + username + " disconnected.");
        }
        // userNames.remove(username);
    }

    public boolean hasUsers() {
        return !userNames.isEmpty();                                //  !this.userNames.isEmpty();
    }
}
