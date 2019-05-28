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

            String userName;
            String clientMessage;
            boolean nickExist;

            do {
                System.out.println("Wysyłam listę użytkowników.");
                server.broadcast(server.userNames, this);        // wysyła listę aktualnych użytkowników do nowego klienta
                userName = reader.readLine();                               //oczekiwanie na odpowiedź (nowy nick)
                nickExist = checkNick(userName);                            // sprawdzenie nowego nick'a
                System.out.println("Nick: " + userName + "\t\tnickExist: " + nickExist);
            } while (nickExist);                                            // nick unikalny ---> opuszczenie pętli
            sendMessage(userName);                                          //  send unique nick to client

            System.out.println("OPUSZCZONO PĘTLĘ");

            String serverMessage;

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

    public void sendMessage(String message) {
        writer.println(message);
    }

    public boolean checkNick(String nick) {
        for (String n : server.getUserNames()) {
            //System.out.println("Nick: " + nick + "\tlist: " + n + "\tequals: " + nick.equals(n));
            if (nick.equals(n)) {
                return true;
            }
        }

        return false;
    }
}
