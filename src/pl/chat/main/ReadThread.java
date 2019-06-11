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
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO     UPRZĄTNĄĆ BAŁAGAN
    ////////////////////////////////////////////////////////////////////////////////////////////
    public void run() {
        try {
          String response;


            // TODO         W S Z Y S T K O   N A   R A Z I E   O K  !!!!!!!!!!!!
            while (true) {
                response = reader.readLine();
                // TODO: przy opuszczeniu chat'u następuje zamknięcie gniazda --> błąd odczytu
                try {
                    if (response.equals("nicksListExportFromServer")) {
                        client.createNicksListFromServer(reader);                //  IMPORT UŻYTKOWNIKÓW Z SERWERA
                        client.checkNickMultiply();
                    }
                } catch (NullPointerException e) {
                    System.out.println("POWINNO ZAMKNĄĆ GNIAZDO I OPUŚCIĆ PROGRAM !!!");
                    socket.close();
                    System.out.println(socket.isClosed());
                    System.exit(-5);
                }

                System.out.println("---   odpowiedź z serwera ---> " + response + " <---");
            }
        } catch (IOException e) {
            System.out.println("Error reading from server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
