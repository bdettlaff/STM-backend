import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("STATKI TM");



        try  {
            ServerSocket serverSocket = new ServerSocket(5000);


            while (true) {
                Socket socketP1 =  serverSocket.accept();
                Socket socketP2 = serverSocket.accept();
                new Serwer(socketP1,socketP2).start();
                System.out.println("cos");

            }



        } catch (IOException e) {
            System.out.println("Server error");
        }
    }
}
