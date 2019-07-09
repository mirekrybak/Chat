package pl.chat.main;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    private String nick;

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
                //  send users to new client
                server.broadcast(server.getUserNames(), this);
                //  waiting for nick (userName)
                nick = reader.readLine();
                //  check userName is unique
                nickExist = checkNick(nick);
            } while (nickExist);

            server.addUsername(nick);
            String serverMessage;               // = "\t\t\tNew user added: " + nick;

            do {
                clientMessage = reader.readLine();
                serverMessage = server.actualTime() + ": " + nick + ": " + clientMessage;
                server.broadcast(serverMessage);
            } while (!clientMessage.equals("bye"));

            removeUserAndCloseSocket();

//            server.removeUser(nick, this);
//            socket.close();

            serverMessage = "\t\t\t" + nick + " has quited.";
            server.broadcast(serverMessage);                                                // server.broadcast(serverMessage, this);
        } catch (IOException e) {
            String message = "Error in UserThread: " + e.getMessage();
            System.out.println(message);
            removeUserAndCloseSocket();
            e.printStackTrace();
        }
    }



    private void removeUserAndCloseSocket() {
        server.removeUser(nick, this);
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
