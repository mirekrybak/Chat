package pl.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable, Runnable {

    @FXML
    private ButtonPaneController buttonPaneController;

    @FXML
    private TextFieldPaneController textFieldPaneController;

    @FXML
    private CommandPaneController commandPaneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(buttonPaneController);
        System.out.println(textFieldPaneController);
        System.out.println(commandPaneController);

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
