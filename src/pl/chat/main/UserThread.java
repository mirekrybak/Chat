package pl.chat.main;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

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

            printUsers();

            String clientMessage =  reader.readLine();
            String serverMessage;
            String userName;

            //  something wrong !!!!
            //  musi być pętla dopóki nick podany w klasie WriteThread nie będzie się powtarzał

            do {                                                            // dodane 9.11.2018
                if (clientMessage.equals("check")) {
                    sendUsers();
                }

                userName = reader.readLine();
                // System.out.println(!userName.equals("check"));
            } while (userName.equals("check"));


            System.out.println("Nowy użytkownik: " + userName);

            server.addUsername(userName);


            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage/*, this*/);                                            // server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quited.";
            server.broadcast(serverMessage/*, this*/);                                                // server.broadcast(serverMessage, this);
        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printUsers() {
        writer.println("Current users:");
        for (String user : server.getUserNames()) {
            writer.println(user);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public void sendUsers() {
        System.out.println("Wysyłam listę użytkowników");
        for (String user : server.getUserNames()) {
            writer.print("\u00b6");
            writer.println(user);
            System.out.println(user);
        }
        System.out.println("Lista użytkowników wysłana.");
    }
}
