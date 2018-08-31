import java.io.*;
import java.net.Socket;

public class UThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public UThread(Socket socket, Server server) {
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

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);
            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]:" + clientMessage;
                server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            server.userRemove(userName, this);
            socket.close();

            serverMessage = userName + " has quited.";
            server.broadcast(serverMessage, this);
        } catch (IOException e) {
            System.out.println("Error in UThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
