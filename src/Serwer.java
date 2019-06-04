import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Serwer extends Thread {
    private Socket socketP1;
    private Socket socketP2;
    private int start;
    private int end;


    public int sumA;
    public int sumB;
    public int wynik;

    public Serwer(Socket socketP1,Socket socketP2) {
        this.socketP1 = socketP1;
        this.socketP2 = socketP2;
    }

    @Override


    public void run() {

        String P1="P1";
        String P2 ="P2";
        boolean turn=true;

        try {
            //DOBRA PRAKTYKA dawać input jako BufferedReader i output jako PrintWriter
            BufferedReader inputP1 = new BufferedReader(
                    new InputStreamReader(socketP1.getInputStream()));
            PrintWriter outputP1 = new PrintWriter(socketP1.getOutputStream(), true);
            BufferedReader inputP2 = new BufferedReader(
                    new InputStreamReader(socketP2.getInputStream()));
            PrintWriter outputP2 = new PrintWriter(socketP2.getOutputStream(), true);

            while (true) {




            }

        } catch (IOException e) {
            System.out.println("Coś poszło nie tak w echoerze " + e.getMessage());
        } finally {
            try {
                socketP1.close();
            } catch (IOException e) {
                //JA nie wiem co tu się odjaniepawla
            }
        }
    }

    public void printGameboard(int[][] gameboard) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(gameboard[i][j]);
            }
            System.out.println();
        }
    }
}


