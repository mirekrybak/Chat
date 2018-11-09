package pl.chat.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private ChatClient client;
    private Socket socket;

    public WriteThread(Socket socket, ChatClient client) {
        this.client = client;
        this.socket = socket;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        String userNick;
        boolean isExist;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Enter your nick: ");
            userNick = sc.nextLine();

            writer.println("check");                                            //  polecenie wysłania listy użytkowników przez serwer

            isExist = client.checkNickMultiply(userNick);

//            if (isExist) {
//                System.out.println("==========      Nick: " + userNick + "   I S T N I E J E   !!!");
//            }
//            System.out.println(isExist);

        } while (isExist);

        client.setUserName(userNick);
        writer.println(userNick);

        String text;
        do {
            System.out.println("Pętla  użytkownik --->  serwer          TEST");
            text = sc.nextLine();
            writer.println(text);
        } while (!text.equals("bye"));
        sc.close();

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
