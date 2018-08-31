

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

//      reading user's input and sending to the server
//      it runs in loop until user type "bye"


public class WThread extends Thread {
    private PrintWriter writer;
    private Client client;
    private Socket socket;

    public WThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output,true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        Console console = System.console();
        String userName = console.readLine("\nEnter your nick: ");
        client.setUserName(userName);
        writer.println(userName);

        String text;
        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }
    }
}
