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

    boolean turn=true;// P1=true
    String[][] gameboardP1=null;
    String[][] gameboardP1enemy=null;
    String[][] gameboardP2=null;
    String[][] gameboardP2enemy=null;
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



        try {
            //DOBRA PRAKTYKA dawać input jako BufferedReader i output jako PrintWriter
            BufferedReader inputP1 = new BufferedReader(
                    new InputStreamReader(socketP1.getInputStream()));
            PrintWriter outputP1 = new PrintWriter(socketP1.getOutputStream(), true);
            BufferedReader inputP2 = new BufferedReader(
                    new InputStreamReader(socketP2.getInputStream()));
            PrintWriter outputP2 = new PrintWriter(socketP2.getOutputStream(), true);
            outputP1.println(P1);
            outputP2.println(P2);


            while (true) {
                String inP1=inputP1.readLine();// P1 PS 0101010101001
                String inP2=inputP2.readLine();
                if(inP1!=null){


                    game(inP1,gameboardP1,gameboardP1enemy,outputP1);
                    System.out.println("xddddd1");
                    //outputP2.println("GB "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                }
                if(inP2!=null){


                    game(inP2,gameboardP2,gameboardP2enemy,outputP2);
                }

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

    public void game(String inP,String[][] gameboardWithShips,String[][] gameboardWithShots,PrintWriter outputP){

        GameLogic gameLogic = new GameLogic();
        {
            String[]in=inP.split(" ");
            switch (in[1]){
                case "PS":
                    if(in[0].equals("P1")) {
                        gameboardP1 = ParsingGameboard.parseGameboardFromStringToStringTab(in[2]);
                        gameboardP1enemy = ParsingGameboard.createEmptyGameboard();
                        outputP.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                        System.out.println("xddddd");
                    }
                    if(in[0].equals("P2")) {
                        gameboardP2 = ParsingGameboard.parseGameboardFromStringToStringTab(in[2]);
                        gameboardP2enemy = ParsingGameboard.createEmptyGameboard();
                        outputP.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                    }

                    break;
                case "SH":
                    if(in[0].equals("P1")) {
                        if (gameLogic.checkShot(in[2]+" "+in[3], gameboardP2, gameboardP1enemy)) {
                            gameLogic.shot(in[2]+" "+in[3], gameboardP2, gameboardP1enemy);
                            if(gameLogic.checkWin(gameboardP2)){
                                outputP.println("Win");
                                break;
                            }
                            outputP.println("GB "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                            turn = !turn;
                        } else {
                            outputP.println("Błędny strzał");
                        }
                    }
                    if(in[0].equals("P2")) {
                        if (gameLogic.checkShot(in[2]+" "+in[3], gameboardP1, gameboardP2enemy)) {
                            gameLogic.shot(in[2]+" "+in[3], gameboardP1, gameboardP2enemy);
                            if(gameLogic.checkWin(gameboardP1)){
                                outputP.println("Win");
                                break;
                            }
                            outputP.println("GB "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                            turn = !turn;
                        } else {
                            outputP.println("Błędny strzał");
                        }
                    }


                    break;
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


