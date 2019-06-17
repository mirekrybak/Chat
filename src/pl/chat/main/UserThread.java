package pl.chat.main;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    private String response;

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


            String clientMessage;
            boolean nickExist;

            do {
                server.log("Send connected users to new client.");
                //  send users to new client
                server.broadcast(server.getUserNames(), this);
                //  waiting for response (userName)
                response = reader.readLine();
                //  check userName is unique
                nickExist = checkNick(response);
            } while (nickExist);

            server.addUsername(response);
            String serverMessage = "\t\t\tNew user added: " + response;
            server.log(serverMessage);

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + response + "]: " + clientMessage;
                server.broadcast(serverMessage);                                            // server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            removeUserAndCloseSocket();

//            server.removeUser(response, this);
//            socket.close();

            serverMessage = "\t\t\t" + response + " has quited.";
            server.log(serverMessage);
            server.broadcast(serverMessage);                                                // server.broadcast(serverMessage, this);
        } catch (IOException e) {
            String message = "Error in UserThread: " + e.getMessage();
            System.out.println(message);
            server.log(message);
            removeUserAndCloseSocket();
            e.printStackTrace();
        }
    }

    private void removeUserAndCloseSocket() {
        server.removeUser(response, this);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public boolean checkNick(String nick) {
        for (String n : server.getUserNames()) {
            if (nick.equals(n)) {
                return true;
            }
        }

        return false;
    }
}
