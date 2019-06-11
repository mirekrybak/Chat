package pl.chat.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Scanner scanner;
    private ChatClient client;
    private Socket socket;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
        scanner = new Scanner(System.in);

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        do {
            System.out.print("Podaj nick: ");
            String nick = scanner.nextLine();
            client.setUserName(nick);
            writer.println(nick);
            System.out.println("Istnieje??? " + client.isNickExist());
        } while (client.isNickExist());     // TODO: spróbować weryfikacji w WriteThread !!!!!!!

        System.out.println("\t\tkurka siwa !!!!       " + client.isNickExist());

        System.out.println("End of loop !!!!");

        String text;
        do {
            text = scanner.nextLine();
            writer.println(text);
        } while (!text.equals("bye."));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
