import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostName;
    private String userName;
    private int port;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Client() {}

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to the chat server.");

            new RThread(socket, this).start();
            new WThread(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }

        String hostName = args[0];
        int port = 0;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Syntax port error !!! " + e.getMessage());
            System.exit(-1);
        }

        Client client = new Client(hostName, port);
        client.execute();
    }

}
