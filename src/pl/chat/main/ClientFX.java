package pl.chat.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeSet;

public class ClientFX {
    public BufferedWriter writer;
    private final String fileName = "clientLog.txt";
    private Set<String> users = new TreeSet<>();
    private Socket socket;
    private Stage stage;
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<String> getUsers() {
        return users;
    }

    public ClientFX(String hostName, int port) {
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            socket = new Socket(hostName, port);
            log("Client system message: connected to ChatServer.");                         //  system message
            new WriteThreadFX(socket, this).start();
            new ReadThreadFX(socket, this).start();
        } catch (UnknownHostException e) {
            log("Client system message: server not found.");                                //  system message
        } catch (IOException e) {
            log("Client system message: I/O error.");                                       //  system message
        }
    }

    public void downloadNicksFromServer(BufferedReader reader) {
        String nick;
        //      ===   IMPORT NICKS LIST FROM SERVER   ===
        try {
            while (!(nick = reader.readLine()).equals("endOfList")) {
                if (!nick.equals("nicksListExportFromServer")) {
                    users.add(nick);
                    //log("Client system message: Add new user: " + nick);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        log("Client system message: list nicks imported.");                                     //  ===   LIST NICKS IMPORTED   ===
    }

    public boolean checkNickMultiply(String checkNick) {
        if (checkNick.length() < 3) {
            log("Client system message: Nick must be 3 char or more...");           //  system message
            return true;
        }

        for (String s : users) {
            if (checkNick.equals(s)) {
                log(checkNick + " is exist. Try again.");                           //  system message
                return true;
            }
        }

        log(checkNick + " isn't exist. Ok.");                                       //  system message
        return false;
    }

    public String actualTime() {
        String time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString() + " ";
        return time;
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

    public void log(String message) {
        try {
            System.out.println(actualTime() + "log: " + message);
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
