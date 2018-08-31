import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port = 7777;                                    // private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UThread> userThreads = new HashSet<>();
    /*
    public ChatServerTest(int port) {
        this.port = port;
    }
    */
    public static void main(String[] args) {    /*
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServerTest <port-number>");
            System.exit(-1);
        }

        int port = 0;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number !!!");
            System.exit(-1);
        }

        ChatServerTest server = new ChatServerTest(port);
        server.execute();
        */

        Server server = new Server();
        server.execute();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("ChatServerTest is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected...");
                UThread newUser = new UThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //      delivers message from user to others (broadcasting)

    void broadcast(String message, UThread excludeUser) {
        for (UThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    //      stores username of the newly connected client

    void addUserName(String userName) {
        userNames.add(userName);
    }

    //      remove associated username and userThread when a client disconnected

    void userRemove(String userName, UThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " disconnected.");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
