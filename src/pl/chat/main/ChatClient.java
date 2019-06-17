package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ChatClient {
    private static String hostName = "localhost";
    private static int port = 7777;
    private Set<String> users;

    public ChatClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        users = new LinkedHashSet<>();
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to ChatServer ....");                         //  system message

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());                  //  system message
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());                         //  system message
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient(hostName, port);
        client.execute();
    }

    public void createNicksListFromServer(BufferedReader reader) {
        String nick;

        System.out.println("\t\t===   IMPORT NICKÓW Z SERWERA !!!!  ===");              //  system message
        try {
            while (!(nick = reader.readLine()).equals("endOfList")) {
                System.out.println("\t\t\t" + nick);
                users.add(nick);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\t\t===   IMPORT NICKÓW ZAKOŃCZONY !!!!   ===");            //  system message
    }

    public boolean checkNickMultiply(String checkNick) {
        if (checkNick.length() > 0) {
            for (String s : users) {
                if (checkNick.equals(s)) {
                    System.out.println(checkNick + "   -   I S T N I E J E   ! ! !");   //  system message
                    return true;
                }
            }
        }

        System.out.println(checkNick + "   -   U N I K A L N Y   ! ! !");               //  system message
        return false;
    }

    public void log(String message) {
        message = message + "\n";

    }
}
