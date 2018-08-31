package pl.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonPaneController implements Initializable {

    @FXML
    private Button connectButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button exitButton;

    public Button getConnectButton() {
        return connectButton;
    }

    public void setConnectButton(Button connectButton) {
        this.connectButton = connectButton;
    }

    public Button getDisconnectButton() {
        return disconnectButton;
    }

    public void setDisconnectButton(Button disconnectButton) {
        this.disconnectButton = disconnectButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init: buttonPaneController.");
    }
}
