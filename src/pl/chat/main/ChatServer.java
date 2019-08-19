package pl.chat.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static int port = 7777;
    private Set<UserThread> userThreads = new HashSet<>();
    private Set<String> userNames = new HashSet<>();

    public Set<String> getUserNames() {
        return userNames;
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(port);
        server.execute();
    }

    private void execute() {
        userNames.add("Ryby");
        userNames.add("Baran");
        userNames.add("Byk");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            String message = "Chat server is listening on port " + port;
            System.out.println(message);
            while (true) {
                Socket socket = serverSocket.accept();
                message = "\t\t\tNew user connected.";
                System.out.println(message);

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            String message = "Error in the server: " + e.getMessage();
            System.out.println(message);
            e.printStackTrace();
        }
    }

    public String serverTime() {
        String time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        return time;
    }

    public void broadcast(String message) {                                             // send messages to all users
        for (UserThread u : userThreads) {
            u.sendMessage(message);
        }
    }

    public void broadcast(Set<String> userNames) {               //  send nicks all login users to new user
        for (UserThread u : userThreads) {
                u.sendMessage("nicksListExportFromServer");
                for (String nick : userNames) {
                    u.sendMessage(nick);
                }
                u.sendMessage("endOfList");
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
            String message = "\t\t\tThe user " + username + " disconnected.";
            System.err.println(message);
        }
    }
}
