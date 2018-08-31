package pl.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandPaneController implements Initializable {

    @FXML
    private TextArea commandTextArea;

    public TextArea getCommandTextArea() {
        return commandTextArea;
    }

    public void setCommandTextArea(TextArea commandTextArea) {
        this.commandTextArea = commandTextArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init: CommandPaneController.");
    }
}
