package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThreadFX extends Thread {
    private BufferedReader reader;
    private ClientFX client;
    private Socket socket;

    UsersChecking checkNick = new UsersChecking();

    public ReadThreadFX(Socket socket, ClientFX client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());            //  system message
            e.printStackTrace();
        }
    }

    public void run() {
        String response;
        try {
            while (true) {
                response = reader.readLine();
                System.out.println(response);
                try {
                    if (response.equals("nicksListExportFromServer")) {
                        checkNick.downloadNicksFromServer(reader);
                        //client.downloadNicksFromServer(reader);                           //  users import from server
                        checkNick.printNicksList();
                    } else {
                        checkNick.printResponse(response);
                        //mainPaneController.textFieldPaneController.getChatTextArea().appendText(response);      //      TODO
                        System.out.println("---> " + response);        // odpowiedź z serwera
                    }
                } catch (NullPointerException e) {      // close socket & exit ReadThread
                    System.out.println("Socket closing ...\nApplication closing ...");      //  system message
                    socket.close();
                    System.exit(-777);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading from server: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("NIEDOBRZE !!!!!!!!!!!!!!!!!!!!!!!");
    }
}
