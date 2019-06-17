package pl.chat.main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static int port = 7777;
    private Set<UserThread> userThreads = new HashSet<>();
    private Set<String> userNames = new HashSet<>();
    private String fileName = "logServer.txt";
    private FileWriter writer;
    private LocalTime time;

    public Set<String> getUserNames() {
        return userNames;
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(port);
        server.createLogFile();
        server.execute();
    }

    private void createLogFile() {
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            String message = "Chat server is listening on port " + port;
            System.out.println(message);
            log(message);                                                               //  must be log!!!!!!!!!!!
            while (true) {

                Socket socket = serverSocket.accept();
                message = "\t\t\tNew user connected.";
                System.out.println(message);
                log(message);                                                           //  must be log!!!!!!!!!!!

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            String message = "Error in the server: " + e.getMessage();
            System.out.println(message);
            log(message);                                                               //  must be log!!!!!!!!!!!
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {                                             // send messages to all users
        for (UserThread u : userThreads) {
            time = LocalTime.now();
            String actualTime = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + " " + message;
            u.sendMessage(actualTime);
        }
    }

    public void broadcast(Set<String> userNames, UserThread userThread) {               //  send nicks all login users to new user
        for (UserThread u : userThreads) {
            if (u == userThread) {
                u.sendMessage("nicksListExportFromServer");
                for (String nick : userNames) {
                    u.sendMessage(nick);
                    String message = "send nick: " + nick;
                    log(message);
                }
                u.sendMessage("endOfList");
            }
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
            log(message);                                                               //  must be log!!!!!!!!!!!
        }
    }

    public void log(String message) {
        // TODO
        message = message + "\n";
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
