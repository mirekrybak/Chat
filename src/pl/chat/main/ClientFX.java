package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeSet;

public class ClientFX {
    private TreeSet<String> users;
    private Socket socket;
//    private boolean imported = false;

//    public boolean isImported() {
//        return imported;
//    }
//
//    public void setImported(boolean imported) {
//        this.imported = imported;
//    }

    public TreeSet<String> getUsers() {
        return users;
    }

    public ClientFX(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
            System.out.println("Connected to ChatServer ....");                         //  system message
            //new WriteThreadFX(socket, this).start();
            new ReadThreadFX(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());                  //  system message
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());                         //  system message
        }


    }

//    public void downloadNicksFromServer(BufferedReader reader) {
//        String nick;
//        users = new TreeSet<>();
//
//        //      ===   IMPORT LIST NICKS FROM SERVER   ===
//
//       // System.out.println("\t\t\t\t\tPOCZĄTEK IMPORTU LISTY");
//        try {
//            while (!(nick = reader.readLine()).equals("endOfList")) {
//                users.add(nick);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //      ===   LIST NICKS IMPORTED   ===
//        imported = true;
//    }

//    public boolean checkNickMultiply(String checkNick) {
//        if (checkNick.length() > 0) {
//            for (String s : users) {
//                if (checkNick.equals(s)) {
//                    System.out.println(checkNick + "   -   I S T N I E J E   ! ! ! !");   //  system message
//                    return true;
//                }
//            }
//        }
//
//        System.out.println(checkNick + "   -   U N I K A L N Y   ! ! !");               //  system message
//        return false;
//    }

    public void log(String message) {
        message = message + "\n";

    }
}
