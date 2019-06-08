import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Serwer extends Thread {
    private Socket socketP1;
    private Socket socketP2;


    boolean turn=true;// P1=true
    String[][] gameboardP1=null;
    String[][] gameboardP1enemy=null;
    String[][] gameboardP2=null;
    String[][] gameboardP2enemy=null;
    GameLogic gameLogic = new GameLogic();


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
            String inP1=null;
            String inP2=null;
            if((inP1=inputP1.readLine())!=null){


                game(inP1,outputP1,outputP2);

            }
            if((inP2=inputP2.readLine())!=null){


                game(inP2,outputP2,outputP1);
            }
            outputP1.println("Twoja Tura");
            outputP2.println("Tura Przeciwnika");
            outputP2.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));


            while (true) {
                inP1=null;
                inP2=null;
                if(gameLogic.whoseTurn()){
                    inP1=inputP1.readLine();
                    game(inP1,outputP1,outputP2);
                }
                else{
                    inP2=inputP2.readLine();
                    game(inP2,outputP2,outputP1);
                }
            }
        } catch (IOException e) {
            System.out.println("Coś poszło nie tak w echoerze " + e.getMessage());
        } finally {
            try {
                socketP1.close();
            } catch (IOException e) {
            }
        }
    }

    public void game(String inP,PrintWriter outputP,PrintWriter outputPEnemy){


        {
            String[]in=inP.split(" ");
            switch (in[1]){
                case "PS":
                    if(in[0].equals("P1")) {
                        gameboardP1 = ParsingGameboard.parseGameboardFromStringToStringTab(in[2]);
                        gameboardP1enemy = ParsingGameboard.createEmptyGameboard();
                        outputP.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                        outputP.println("Zaczekaj aż przeciwnik ustawi Statki");
                    }
                    if(in[0].equals("P2")) {
                        gameboardP2 = ParsingGameboard.parseGameboardFromStringToStringTab(in[2]);
                        gameboardP2enemy = ParsingGameboard.createEmptyGameboard();
                        outputP.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));

                        outputP.println("Zaczekaj aż przeciwnik ustawi Statki");
                        outputP.println("PS "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                    }

                    break;
                case "SH":
                    if(in[0].equals("P1")) {
                        if (gameLogic.checkShot(in[2]+" "+in[3], gameboardP2, gameboardP1enemy)) {
                            gameLogic.shot(in[2]+" "+in[3], gameboardP2, gameboardP1enemy);
                            if(gameLogic.checkWin(gameboardP2)){
                                outputP.println("Win");
                                outputPEnemy.println("Lost");
                                try {
                                    socketP1.close();
                                    socketP2.close();
                                }
                                catch (Exception e){

                                }
                                break;
                            }
                            if(!gameLogic.whoseTurn()) {
                                outputPEnemy.println("Twoja Tura");
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                                outputP.println("Tura Przeciwnika");
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                            }
                            else{
                                outputPEnemy.println("Tura Przeciwnika");
                                outputPEnemy.println("GB "
                                                     + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)
                                                     + " "
                                                     + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                                outputP.println("Twoja Tura");

                            }
                        } else {
                            outputP.println("Twoja Tura");
                            outputP.println("Błędny strzał");
                        }
                    }
                    if(in[0].equals("P2")) {
                        if (gameLogic.checkShot(in[2]+" "+in[3], gameboardP1, gameboardP2enemy)) {
                            gameLogic.shot(in[2]+" "+in[3], gameboardP1, gameboardP2enemy);
                            if(gameLogic.checkWin(gameboardP1)){
                                outputP.println("Win");
                                outputPEnemy.println("Lost");
                                try {
                                    socketP1.close();
                                    socketP2.close();
                                }
                                catch (Exception e){

                                }
                                break;
                            }
                            if(gameLogic.whoseTurn()) {
                                outputPEnemy.println("Twoja Tura");
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                                outputP.println("Tura Przeciwnika");
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                            }
                            else{
                                outputPEnemy.println("Tura Przeciwnika");
                                outputPEnemy.println("GB "
                                                     + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1)
                                                     + " "
                                                     + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP1enemy));
                                outputP.println("GB "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2)
                                                + " "
                                                + ParsingGameboard.parseGameboardFromStringTabToString(gameboardP2enemy));
                                outputP.println("Twoja Tura");
                            }
                        } else {
                            outputP.println("Twoja Tura");
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


