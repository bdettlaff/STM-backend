public class GameLogic {

//S-STATEK
//O-PUSTE POLE
//X-PUDŁO
//H-TRAFIONY STATEK

    private boolean turn = true;

    public boolean isShotPossible(String shot, String[][] gameboardWithships, String[][] gameboardWithShots) {
        String[] splitedGameboard = shot.split(" ");
        return gameboardWithships[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] != "X" ||
                gameboardWithships[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] != "H";
    }

    public String makeAShot(String shot, String[][] gameboardWithships, String[][] gameboardWithShots) {
        String[] splitedGameboard = shot.split(" ");
        if (gameboardWithships[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])].equals("O")) {
            gameboardWithships[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] = "X";
            gameboardWithShots[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] = "X";
            turn = !turn;
        } else {
            gameboardWithships[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] = "H";
            gameboardWithShots[Integer.parseInt(splitedGameboard[0])][Integer.parseInt(splitedGameboard[1])] = "H";
        }

        return ParsingGameboard.parseGameboardFromStringTabToString(gameboardWithships) + " " + ParsingGameboard.parseGameboardFromStringTabToString(gameboardWithShots);
    }

    public boolean isGameWon(String[][] gameboardWithShips) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameboardWithShips[i][j].equals("S")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean whoseTurn() {
        return turn;
    }
}
