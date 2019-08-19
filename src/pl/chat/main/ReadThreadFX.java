package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static pl.chat.controller.MainPaneController.mainPaneController;

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
            client.log("Client system message: error getting input stream: ");            //  system message
        }
    }

    private void printNicksList() {
        mainPaneController.textFieldPaneController.getUsersTextArea().clear();
        for (String s : client.getUsers()) {
            mainPaneController.users.add(s);
            mainPaneController.textFieldPaneController.getUsersTextArea().appendText(s + "\n");
        }
    }

    private void printResponse(String response) {
        String text = response + "\n";
        mainPaneController.textFieldPaneController.getChatTextArea().appendText(text);
    }

    public void run() {
        String response;
        while (true) {
            try {
                response = reader.readLine();
                if (response.equals("nicksListExportFromServer")) {
                    client.downloadNicksFromServer(reader);
                    printNicksList();
                } else {
                    printResponse(response);
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}
