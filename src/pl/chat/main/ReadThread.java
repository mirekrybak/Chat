package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private ChatClient client;
    private Socket socket;

    public ReadThread(Socket socket, ChatClient client) {
        this.client = client;
        this.socket = socket;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                if (response.startsWith("\u00b6")) {
                    client.getUsers().add(response.replace("\u00b6",""));
                } else {
                    System.out.println(response/* + "   <--- wypisuje wiadomość kiedy nie dodaje użytkownika do zbioru"*/);
                }
            } catch (IOException e) {
                //  close socket when the client disconnected.
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
    }
}
