package pl.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TextFieldPaneController implements Initializable {

    @FXML
    private TextField messageTextField;

    @FXML
    private Button validateButton;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField userTextField;

    @FXML
    private TextArea usersTextArea;

    public Button getValidateButton() {
        return validateButton;
    }

    public void setValidateButton(Button validateButton) {
        this.validateButton = validateButton;
    }

    public TextField getUserTextField() {
        return userTextField;
    }

    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    public TextField getMessageTextField() {
        return messageTextField;
    }

    public void setMessageTextField(TextField messageTextField) {
        this.messageTextField = messageTextField;
    }

    public TextArea getChatTextArea() {
        return chatTextArea;
    }

    public void setChatTextArea(TextArea chatTextArea) {
        this.chatTextArea = chatTextArea;
    }

    public TextArea getUsersTextArea() {
        return usersTextArea;
    }

    public void setUsersTextArea(TextArea usersTextArea) {
        this.usersTextArea = usersTextArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
