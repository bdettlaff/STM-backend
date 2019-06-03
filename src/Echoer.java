import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echoer extends Thread {
    private Socket socket;
    private int start;
    private int end;


    public int sumA;
    public int sumB;
    public int wynik;

    public Echoer(Socket socket) {
        this.socket = socket;
    }

    @Override


    public void run() {


        try {
            //DOBRA PRAKTYKA dawać input jako BufferedReader i output jako PrintWriter
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {

                String echoString = input.readLine(); // string pobrany od klienta
                int[][] gameboard = ParsingGameboard.parseGameboardFromStringToInt(echoString);


                output.println("tablica legit doszła kumplu" ); //// wyswietlane na kliencie

                printGameboard(gameboard);
            }

        } catch (IOException e) {
            System.out.println("Coś poszło nie tak w echoerze " + e.getMessage());
        } finally {
            try {
                socket.close();
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


