package pl.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.chat.main.ClientFX;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class MainPaneController implements Initializable, Runnable {

    public static MainPaneController mainPaneController;        //      TODO

    @FXML
    public ButtonPaneController buttonPaneController;

    @FXML
    public TextFieldPaneController textFieldPaneController;     //      TODO

    @FXML
    public CommandPaneController commandPaneController;         //      TODO

    public Set<String> users = new TreeSet<>();
    private ClientFX client;
    private String hostName = "localhost";
    private int port = 7777;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPaneController = this;                              //      TODO

        Button connectButton = buttonPaneController.getConnectButton();
        Button disconnectButton = buttonPaneController.getDisconnectButton();
        Button exitButton = buttonPaneController.getExitButton();

        TextField nickTextField = textFieldPaneController.getUserTextField();
        TextField textField = textFieldPaneController.getMessageTextField();
        TextArea usersTextArea = textFieldPaneController.getUsersTextArea();
        TextArea chatTextArea = textFieldPaneController.getChatTextArea();
        Button validateButton = textFieldPaneController.getValidateButton();

        TextArea commandArea = commandPaneController.getCommandTextArea();

        chatTextArea.setEditable(false);
        commandArea.setEditable(false);
        usersTextArea.setEditable(false);
        textField.setEditable(false);
        nickTextField.setEditable(false);
        disconnectButton.setDisable(true);
        validateButton.setDisable(true);

        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client = new ClientFX(hostName, port);
                disconnectButton.setDisable(false);
                connectButton.setDisable(true);
                exitButton.setDisable(true);
                nickTextField.setEditable(true);
                nickTextField.setPromptText("enter nick");
                validateButton.setDisable(false);
            }
        });

        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disconnectButton.setDisable(true);
                nickTextField.clear();
                validateButton.setDisable(true);
                connectButton.setDisable(false);
                exitButton.setDisable(false);
                usersTextArea.clear();
                nickTextField.setStyle("-fx-text-fill: #000000");
                //client.closeSocket();
            }
        });

        validateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.setNick(nickTextField.getText());
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Exit application !!!");
                System.exit(-1);
            }
        });
    }

    @Override
    public void run() {
    }
}
