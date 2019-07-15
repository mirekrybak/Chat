package pl.chat.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private ChatClient client;
    private Scanner scanner;
    private Socket socket;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
        scanner = new Scanner(System.in);

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());       //  system message
            e.printStackTrace();
        }
    }

    public void run() {
        String text;
        do {


            System.out.print("Podaj nick: ");                                           // nick input !!!!!
            text = scanner.nextLine();
            writer.println(text);
        } while (client.checkNickMultiply(text));

        do {
            text = scanner.nextLine();
            writer.println(text);
        } while (!text.equals("bye."));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());           //  system message
            e.printStackTrace();
        }
    }
}
