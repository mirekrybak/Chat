package pl.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogPaneController implements Initializable {

    @FXML
    private TextField userTextField;

    @FXML
    private Label infoLabel;

    public TextField getUserTextField() {
        return userTextField;
    }

    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(Label infoLabel) {
        this.infoLabel = infoLabel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
