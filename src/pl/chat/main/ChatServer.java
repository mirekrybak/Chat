package pl.chat.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static int port = 7777;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public Set<String> getUserNames() {
        return userNames;
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(port);
        server.userNames.add("Tata");
        server.userNames.add("Mama");
        server.execute();
    }

    private void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat server is listening on port " + port);
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("\t\t\t\t    --- >   New user connected.");

                UserThread newUser = new UserThread(socket, this);        //  new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcast(String message/*, UserThread user*/) {                         // broadcast(String message, UserThread user)
        for (UserThread u : userThreads) {
            u.sendMessage(message);
        }
    }

    public void addUsername(String username) {
        userNames.add(username);
    }

    //  remove associated username with UserThread when the client disconnected.

    public void removeUser(String username, UserThread user) {
        boolean removed = userNames.remove(username);
        if (removed) {
            userThreads.remove(user);
            System.err.println("The user " + username + " disconnected.");
        }
    }

    public boolean hasUsers() {
        return !userNames.isEmpty();                            //  !this.userNames.isEmpty();
    }
}
