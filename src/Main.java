import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        System.out.println("STATKI TM");



        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            while (true) {
                new Echoer(serverSocket.accept()).start();
                System.out.println("cos");

            }



        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
