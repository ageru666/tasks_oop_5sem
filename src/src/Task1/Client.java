package Task1;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            MyObject obj = new MyObject("Hello, Server!");
            out.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
