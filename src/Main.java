import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                Socket firstSocket = serverSocket.accept();
                Socket secondSocket = serverSocket.accept();
                new Serwer(firstSocket, secondSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
