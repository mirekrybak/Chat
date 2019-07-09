package pl.chat.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThreadFX extends Thread {
    private PrintWriter writer;
    private ClientFX client;
    private Scanner scanner;
    private Socket socket;

    UsersChecking checkNick = new UsersChecking();

    public WriteThreadFX(Socket socket, ClientFX client) {
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

        System.out.println("WriteThreadFX init ...");
    }

    public void run() {
        String text;
        do {
            System.out.print("Podaj nick: ");                                           // nick input !!!!!
            text = scanner.nextLine();
            writer.println(text);
//        } while (client.checkNickMultiply(text));
        } while (checkNick.checkNickMultiply(text));
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
