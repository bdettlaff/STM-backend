public class ParsingGameboard {

    public static int[][] parseGameboardFromStringToInt(String receivedGameboard){

        int[][] gameboard = new int [10][10];
        for(int i =0;i<10;i++){
            for(int j=0;j<10;j++){
                gameboard[i][j]=Character.getNumericValue(receivedGameboard.charAt(((i+1)*(j+1)-1)));
            }
        }

        return gameboard;
    }

    public static String parseGameboardFromIntToString(int[][] gameboard){
        String toSendGameboard="";
        for(int i =0;i<10;i++){
            for(int j=0;j<10;j++){
                toSendGameboard+=String.valueOf(gameboard[i][j]);
            }
        }
        return toSendGameboard;
    }
}
