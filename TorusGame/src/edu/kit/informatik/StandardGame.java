package edu.kit.informatik;

public class StandardGame {

    // EL SAYISINI SAKLAYACAK VE G�NCELLEYECEK �EYLER YAZ
    private static boolean gameStarted = false;
    private static boolean win = false;
    private static boolean stoneSelected = false;
    private static String P1 = "select";
    private static String P2 = "place";
    private static int set = 0;
    private static Stone selectedStone = null;

    public void reset(){
        win = false;
        stoneSelected = false;
        selectedStone = null;
        P1 = "select";
        P2 = "place";
        set = 0;
    }

    public Stone getSelectedStone(){
        return selectedStone;
    }

    public void setSelectedStone(Stone stone){
        selectedStone = stone;
    }

    public boolean getGameStarted(){
        return gameStarted;
    }

    public boolean getStoneSelected(){
        return stoneSelected;
    }

    public void setStoneSelected(boolean selected){
        stoneSelected = selected;
    }

    public void increaseSet() {
        set++;
    }

    public void startGame(String[] inputArray, StandardBoard board, Bag bag){
        if(inputArray.length != 2){
            Terminal.printError("invalid parameter(s) for start command!");
            return;
        }

        switch(inputArray[1]){
            case "standard":
                BoardGameMain.game = new StandardGame();
                BoardGameMain.board = new StandardBoard();
                break;
            case "torus":
                BoardGameMain.game = new TorusGame();
                BoardGameMain.board = new TorusBoard();
                break;
            default:
                Terminal.printError("invalid parameter for start command!");
                return;
        }
        BoardGameMain.game.reset();
        board.reset();
        gameStarted = true;
        bag.reset();
        Terminal.printLine("OK");
    }

    public void switchTurn() {
        String temp = P1;
        P1 = P2;
        P2 = temp;
    }

    public boolean getWin() {
        return win;
    }

    static int sameColor = 1;  //checkColorTwoOppDir ba�lar ba�lamaz sameColor = 1 yap
    static int sameEdge = 1;
    static int sameSize = 1;
    static int sameShape = 1;

    protected boolean checkColor(StandardBoard board, Stone stone, int checkRow, int checkCol) {

        try{
            if(board.getStone(checkRow, checkCol) != null && stone.getColor().equals(board.getStone(checkRow, checkCol).getColor())) {
                sameColor++;
                Terminal.printLine("sameColor++ ba�ar�l�");
                return true;
            }
        }catch(Exception e) {
            Terminal.printLine("checkColor()");
        }
        return false;

    }

    protected boolean checkEdge(StandardBoard board, Stone stone, int checkRow, int checkCol) {

        try{
            if(board.getStone(checkRow, checkCol) != null && stone.getEdge().equals(board.getStone(checkRow, checkCol).getEdge())) {
                sameEdge++;
                return true;
            }
        }catch(Exception e) {
            Terminal.printLine("checkEdge()");
        }
        return false;

    }

    protected boolean checkSize(StandardBoard board, Stone stone, int checkRow, int checkCol) {

        try{
            if(board.getStone(checkRow, checkCol) != null && stone.getSize().equals(board.getStone(checkRow, checkCol).getSize())) {
                sameSize++;
                return true;
            }
        }catch(Exception e) {
            Terminal.printLine("checkSize()");
        }
        return false;

    }

    protected boolean checkShape(StandardBoard board, Stone stone, int checkRow, int checkCol) {

        try{
            if(board.getStone(checkRow, checkCol) != null && stone.getShape().equals(board.getStone(checkRow, checkCol).getShape())) {
                sameShape++;
                return true;
            }
        }catch(Exception e) {
            Terminal.printLine("checkShape()");
        }
        return false;

    }

    protected int checkRow, onePlusCheckRow, twoPlusCheckRow, checkCol, onePlusCheckCol, twoPlusCheckCol;

    protected void setValuesFast(int row, int col, int addRow, int addCol) {

        checkRow = row + addRow;
        onePlusCheckRow = checkRow + addRow;
        twoPlusCheckRow = onePlusCheckRow + addRow;
        checkCol = col + addCol;
        onePlusCheckCol = checkCol + addCol;
        twoPlusCheckCol = onePlusCheckCol + addCol;

    }

    protected boolean checkFeatureOneDir(StandardBoard board, Stone stone, int row, int col, String direction, String feature) {

        boolean firstCheck = false;
        boolean secondCheck = false;
        boolean thirdCheck = false;

        try {
            switch(direction) {
                case "north":
                    setValuesFast(row, col, - 1, 0);
                    break;
                case "northEast":
                    setValuesFast(row, col, - 1, 1);
                    break;
                case "east":
                    setValuesFast(row, col, 0, 1);
                    break;
                case "southEast":
                    setValuesFast(row, col, 1, 1);
                    break;
                case "south":
                    setValuesFast(row, col, 1, 0);
                    break;
                case "southWest":
                    setValuesFast(row, col, 1, - 1);
                    break;
                case "west":
                    setValuesFast(row, col, 0, - 1);
                    break;
                case "northWest":
                    setValuesFast(row, col, - 1, - 1);
                    break;
                default:
                    Terminal.printError("checkFeatureOneDir() switch case");
                    return false;
            }

            if(checkRow < 0 || checkCol < 0 || checkRow > 5 || checkCol > 5) {
                return false;
            }else if(onePlusCheckRow < 0 || onePlusCheckCol < 0 || onePlusCheckRow > 5 || onePlusCheckCol > 5){
                firstCheck = true;
            } else if(twoPlusCheckRow < 0 || twoPlusCheckCol < 0 || twoPlusCheckRow > 5 || twoPlusCheckCol > 5) {
                secondCheck = true;
            }else {
                secondCheck = true;
                thirdCheck = true;
            }

            if(feature.equals("color") && firstCheck && checkColor(board, stone, checkRow, checkCol)
                    && secondCheck && checkColor(board, stone, onePlusCheckRow, onePlusCheckCol)
                    && thirdCheck && checkColor(board, stone, twoPlusCheckRow, twoPlusCheckCol)) {
                return true;
            }
            if(feature.equals("edge") && firstCheck && checkEdge(board, stone, checkRow, checkCol)
                    && secondCheck && checkEdge(board, stone, onePlusCheckRow, onePlusCheckCol)
                    && thirdCheck && checkEdge(board, stone, twoPlusCheckRow, twoPlusCheckCol)) {
                return true;
            }
            if(feature.equals("size") && firstCheck && checkSize(board, stone, checkRow, checkCol)
                    && secondCheck && checkSize(board, stone, onePlusCheckRow, onePlusCheckCol)
                    && thirdCheck && checkSize(board, stone, twoPlusCheckRow, twoPlusCheckCol)) {
                return true;
            }
            if(feature.equals("shape") && firstCheck && checkShape(board, stone, checkRow, checkCol)
                    && secondCheck && checkShape(board, stone, onePlusCheckRow, onePlusCheckCol)
                    && thirdCheck && checkShape(board, stone, twoPlusCheckRow, twoPlusCheckCol)) {
                return true;
            }
        }catch(Exception e) {
            Terminal.printError("checkFeatureOneDir()");
        }
        return false;

    }

    protected boolean checkFeaturesTwoOppDir(StandardBoard board, Stone stone, int row, int col, String direction) {

        String oppDirection;

        sameColor = 1;
        sameEdge = 1;
        sameSize = 1;
        sameShape = 1;

        try {
            switch(direction) {
                case "north":
                    oppDirection = "south";
                    break;
                case "northEast":
                    oppDirection = "southWest";
                    break;
                case "east":
                    oppDirection = "west";
                    break;
                case "southEast":
                    oppDirection = "northWest";
                    break;
                default:
                    Terminal.printError("checkFeatureTwoOppDir() switch case");
                    return false;
            }
            String[] features = {"color", "edge", "size", "shape"};
            for(String feature: features) {
                if(checkFeatureOneDir(board, stone, row, col, direction, feature) || checkFeatureOneDir(board, stone, row, col, oppDirection, feature)
                        || sameColor >= 4 || sameEdge >= 4 || sameSize >= 4 || sameShape >= 4) {
                    return true;
                }
            }
        }catch(Exception e) {
            Terminal.printError("checkFeatureTwoOppDir()");
        }

        return false;

    }

    // RETURN TRUE �ST�NE OYUNU DURDURMAK ���N B�R �EYLER EKLE
    public void checkWin(StandardBoard board, Stone stone, int row, int col) {

        try {
            if(checkFeaturesTwoOppDir(board, stone, row, col, "north")
                    || checkFeaturesTwoOppDir(board, stone, row, col, "northEast")
                    || checkFeaturesTwoOppDir(board, stone, row, col, "east")
                    || checkFeaturesTwoOppDir(board, stone, row, col, "southEast")) {
                win = true;
                if(P1.equals("place")) {
                    Terminal.printLine("P1 wins");
                }else {
                    Terminal.printLine("P2 wins");
                }
                Terminal.printLine(set);
            }
        }catch(Exception e) {
            Terminal.printError("checkWin()");
        }

    }
}
