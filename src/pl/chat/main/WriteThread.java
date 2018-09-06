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
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your nick: ");
        String nickname = sc.nextLine();
        client.setUserName(nickname);
        writer.println(client.getUserName());

        String text;
        while (!(text = sc.nextLine()).equals("bye")) {
            //text = "[" + nickname + "]:   " + text;
            writer.println(text);
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
