package pl.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import pl.chat.main.ClientFX;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable, Runnable {

    public static MainPaneController mainPaneController;        //      TODO

    @FXML
    private ButtonPaneController buttonPaneController;

    @FXML
    public TextFieldPaneController textFieldPaneController;     //      TODO

    @FXML
    public CommandPaneController commandPaneController;         //      TODO

    private ClientFX client;
    private String hostName = "localhost";
    private int port = 7777;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPaneController = this;                              //      TODO

        Button connectButton = buttonPaneController.getConnectButton();
        Button disconnectButton = buttonPaneController.getDisconnectButton();
        Button exitButton = buttonPaneController.getExitButton();

        TextArea commandArea = commandPaneController.getCommandTextArea();

        TextField textField = textFieldPaneController.getMessageTextField();
        TextArea chatTextArea = textFieldPaneController.getChatTextArea();
        TextArea usersTextArea = textFieldPaneController.getUsersTextArea();

        chatTextArea.setEditable(false);
        commandArea.setEditable(false);
        usersTextArea.setEditable(false);
        disconnectButton.setDisable(true);

        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //usersTextArea.clear();
                client = new ClientFX(hostName, port);

//                //  waiting for end of nicks import from server
//                while (client.isImported() == false) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

                //  print nicks list in userTextArea
//                for (String s : client.getUsers()) {
//                    s = s + "\n";
//                    usersTextArea.appendText(s);
//                }

                //connectButton.setDisable(true);
                //disconnectButton.setDisable(false);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Exit application !!!");
                System.exit(-1);
            }
        });

        textField.setOnKeyPressed(x -> {
            if (x.getCode() == KeyCode.ENTER && !textField.getText().equals("")) {
                String text = textField.getText() + "\n";
                chatTextArea.appendText(text);
                commandArea.appendText(text);
                textField.clear();
            }
        });
    }

    @Override
    public void run() {
    }
}
