public class GameLogic {

//S-STATEK
//O-PUSTE POLE
//X-PUDŁO
//H-TRAFIONY STATEK




    public boolean checkShot(String shot,String[][] gameboardWithships,String[][] gameboardWithShots){
        //czy strzał był dozwolony
        String[] ij = shot.split(".");
        if(gameboardWithships[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])] != "X" || gameboardWithships[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])] != "H"){
            return false;
        }
        return true;
    }

    public String shot(String shot,String[][] gameboardWithships,String[][] gameboardWithShots){
        String[] ij = shot.split(".");
        if(gameboardWithships[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])].equals("O")){
            gameboardWithships[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])]="X";
            gameboardWithShots[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])]="X";
        }
        else{
            gameboardWithships[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])]="H";
            gameboardWithShots[Integer.parseInt(ij[0])][Integer.parseInt(ij[1])]="H";
        }

        return ParsingGameboard.parseGameboardFromStringTabToString(gameboardWithships)+" "+ParsingGameboard.parseGameboardFromStringTabToString(gameboardWithShots);
    }

    public boolean checkWin(String[][] gameboardWithShips){

        for(int i =0;i<10;i++){
            for(int j=0;j<10;j++){
                if(gameboardWithShips[i][j].equals("S")){
                    return false;
                }
            }
        }
        return true;

    }

    public void whoseTurn(){

    }
}
