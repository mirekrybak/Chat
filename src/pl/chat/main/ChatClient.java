package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ChatClient {
    private boolean nickExist = true;
    private static String hostName = "localhost";
    private static int port = 7777;
    public Set<String> users;
    private String userName = "";



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isNickExist() {
        return nickExist;
    }

    public void setNickExist(boolean nickExist) {
        this.nickExist = nickExist;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public ChatClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        users = new LinkedHashSet<>();
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to ChatServer ....");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient(hostName, port);
        client.execute();
    }

    public void createNicksListFromServer(BufferedReader reader) {
        String nick;

        System.out.println("\t\t===   IMPORT NICKÓW Z SERWERA !!!!  ===");
        try {
            while (!(nick = reader.readLine()).equals("endOfList")) {
                System.out.println("\t\t\t" + nick);
                users.add(nick);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\t\t===   IMPORT NICKÓW ZAKOŃCZONY !!!!   ===");

    }

    public void checkNickMultiply() {
        for (String s : users) {
            System.out.print("userName: " + userName + "\t");
            System.out.println("z listy: "  + s + "\tequals: " + userName.equals(s));
            if (userName.equals(s)) {  //
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!   Podany nick zajęty !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                nickExist = true;         //
                break;
            } else {
                nickExist = false;
            }
        }
    }
}
