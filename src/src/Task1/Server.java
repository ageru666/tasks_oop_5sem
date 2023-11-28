package Task1;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");

            // 5 з'єднань
            for (int i = 0; i < 5; i++) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                    MyObject obj = (MyObject) in.readObject();
                    System.out.println("Received (" + (i + 1) + "): " + obj.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Shutting down.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
