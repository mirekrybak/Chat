package pl.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.chat.main.ClientFX;

import java.io.IOException;
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

    @FXML
    private LogPaneController logPaneController;

    private ClientFX client;
    private String hostName = "localhost";
    private int port = 7777;
    private Parent root;
    private Stage stage;

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
                client = new ClientFX(hostName, port);
                exitButton.setDisable(true);
                connectButton.setDisable(true);
                disconnectButton.setDisable(false);
                openLoginWindow();
            }
        });

        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeLoginWindow();
                exitButton.setDisable(false);
                connectButton.setDisable(false);
                disconnectButton.setDisable(true);
                usersTextArea.clear();
                client.closeSocket();
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

//        userTextField.setOnKeyPressed(x -> {
//            if (x.getCode() == KeyCode.ENTER && !userTextField.getText().equals("")) {
//                String text = userTextField.getText() + "\n";
//                commandArea.appendText(text);
//                userTextField.clear();
//            }
//        });
    }

    public void openLoginWindow() {
        try {
            root = FXMLLoader.load(getClass().getResource("../view/LogPane.fxml"));
            stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setX(545);
            stage.setY(390);
            stage.setTitle("Login pane");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeLoginWindow() {
        stage.close();
    }

    @Override
    public void run() {
    }
}
