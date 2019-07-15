package pl.chat.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFX {
    private Socket socket;
    private Stage stage;

    public ClientFX(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
            System.out.println("Connected to ChatServer ....");                         //  system message
            new WriteThreadFX(socket, this).start();
            new ReadThreadFX(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());                  //  system message
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());                         //  system message
        }
    }

    public void closeSocket() {
        try {
            System.out.println("Connect terminated.");
            socket.close();
        } catch (IOException e) {
            System.out.println("Cannot close socket. Application terminated.");
            e.printStackTrace();
            System.exit(-7777);
        }
    }

    public void log(String message) {
        message = message + "\n";

    }

    public void openLoginWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LogPane.fxml"));
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
}
