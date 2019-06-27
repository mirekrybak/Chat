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

        //System.out.println("ReadThreadFX init ....");
    }

    public void run() {
        try {
            String response;
            while (true) {
                response = reader.readLine();
                //System.out.println("\t\t\t\t\tSTRUMIEŃ Z SERWERA:  " + response);
                try {
                    //System.out.println("\t\t\tOczekiwanie na listę userów z serwera ...");
                    if (response.equals("nicksListExportFromServer")) {
                        client.createNicksListFromServer(reader);                           //  users import from server
                    } else {
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
    }
}
