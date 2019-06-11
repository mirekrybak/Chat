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

            String response;
            String clientMessage;
            boolean nickExist;

            do {
                //  server.log("Wysyłam listę użytkowników.");
                //  send users to new client
                server.broadcast(server.userNames, this);
                //  waiting for response (userName)
                response = reader.readLine();
                //  check userName is unique
                nickExist = checkNick(response);
                System.out.println();
                if (nickExist) {
                    server.broadcast("==========   Nick is used ....   ==========", this);
                    System.out.println("Nick istnieje !!!!!!");
                }
            } while (nickExist);
            sendMessage(response);                                          //  send unique nick to client

            System.out.println("DODANO NOWEGO UŻYTKOWNIKA:");

            String serverMessage;

            System.out.println("Nowy użytkownik: " + response);     // must be as log !!!!!
            server.addUsername(response);

            do {
                System.out.println("\t--> oczekiwanie na wiadomość od nowego klienta [" + response + "]");
                clientMessage = reader.readLine();
                System.out.println("\t<-- odebranie wiadomości od nowego klienta [" + response + "]");
                System.out.println(clientMessage);
                serverMessage = "[" + response + "]: " + clientMessage;
                System.out.println("\t--> wysłanie wiadomości od [" + response + "] do wszystkich użytkowników");
                System.out.println(serverMessage);
                server.broadcast(serverMessage/*, this*/);                                            // server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            server.removeUser(response, this);
            socket.close();

            serverMessage = response + " has quited.";
            server.broadcast(serverMessage/*, this*/);                                                // server.broadcast(serverMessage, this);
        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public boolean checkNick(String nick) {
        for (String n : server.getUserNames()) {
            System.out.println("\t\tNick: " + nick + "\tlist: " + n + "\tequals: " + nick.equals(n));
            if (nick.equals(n)) {
                System.out.println("\tcheckNick method: " + n + " equals " + nick + " - " + nick.equals(n));
                return true;
            }
        }

        return false;
    }
}
