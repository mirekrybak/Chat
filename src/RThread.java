import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

        //      reading server input and printing to the console
        //      it runs in loop until the client disconnects from the server

public class RThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public RThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);

                //      print the username after displaying the server's message

                if (client.getUserName() != null) {
                    System.out.println("[" + client.getUserName() + "]: ");
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}
