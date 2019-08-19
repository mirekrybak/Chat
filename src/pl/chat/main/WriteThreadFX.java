package pl.chat.main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static pl.chat.controller.MainPaneController.mainPaneController;

public class WriteThreadFX extends Thread {
    private PrintWriter writer;
    private ClientFX client;
    private Socket socket;
    private boolean nickExist = true;
    private boolean proceed = true;
    private String text = "";

    public WriteThreadFX(Socket socket, ClientFX client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            client.log("Error getting output stream: " + e.getMessage());       //  system message
            e.printStackTrace();
        }
    }

    public void run() {
        do {
            mainPaneController.textFieldPaneController.getValidateButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override               //  button validate pressed
                public void handle(MouseEvent event) {
                    nickExist = client.checkNickMultiply(client.getNick());
                    if (nickExist) {
                        mainPaneController.textFieldPaneController.getUserTextField().setStyle("-fx-text-fill: #f5321e");
                        client.log("Client system message: cannot add current user.");     //      system message - cannot add current user
                    } else {
                        client.log("Client system message: add new user.");                //      add new user - system message
                    }
                }
            });
        } while (nickExist);

        mainPaneController.textFieldPaneController.getUserTextField().setStyle("-fx-text-fill: #0ff515");
        mainPaneController.textFieldPaneController.getMessageTextField().setEditable(true);
        writer.println(client.getNick());                                                  //      send nickname toserver

        do {
            //  send not empty message to server after ENTER key pressed
            mainPaneController.textFieldPaneController.getMessageTextField().setOnKeyPressed(x -> {
                if (x.getCode() == KeyCode.ENTER && !mainPaneController.textFieldPaneController.getMessageTextField().getText().equals("")) {
                    text = mainPaneController.textFieldPaneController.getMessageTextField().getText();
                    writer.println(text);
                    mainPaneController.textFieldPaneController.getMessageTextField().clear();
                }
            });

            mainPaneController.buttonPaneController.getDisconnectButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    proceed = false;
                    writer.println("");
                }
            });
        } while (proceed);

        client.log("Client system message: client disconnected.");

        try {
            socket.close();
            client.log("Client system message: socket closed.");
            client.writer.close();
        } catch (IOException e) {
            client.log("Client system message: error writing to server.");
        }
    }
}
