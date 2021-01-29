package edu.kit.informatik;

public class StandardBoard {

    private Stone[][] board = new Stone[6][6];

    public void reset(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                board[i][j] = null;
            }
        }
    }

    //getStone gerekli mi ???
    // o cellde ta� yoksa null mu d�nmeli hata ay�klamas� m� yap�lmal�
    public Stone getStone(int row, int col) {
        try {
            return board[row][col];
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
            return null;
        }

    }
    //torusBoard class placeStone() override, row ve colt s�n�rlar d���na
    // koyulabildi�inden dolay�
    public boolean placeStone(StandardBoard board, StandardGame game, Bag bag, String[] inputArray, Stone stone) {
        int row = 0;
        int col = 0;

        if(inputArray.length != 2){
            Terminal.printError("invalid parameter(s) for place command!");
            return false;
        }else if(!game.getGameStarted()){
            Terminal.printError("first a game must be started!");
            return false;
        }else if(!game.getStoneSelected()){
            Terminal.printError("first a stone must be selected!");
            return false;
        }

        try{
            String[] rowCol = inputArray[1].split(";");
            row = Integer.parseInt(rowCol[0]);
            col = Integer.parseInt(rowCol[1]);
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
            Terminal.printError("place command must be followed by row and colon numbers separated by a semi colon!");
            return false;
        }

        try{
            if(this.board[row][col] == null) {
                this.board[row][col] = stone;
                game.increaseSet();
                game.checkWin(board, stone, row, col);     // CHECKWIN METODUNDAK� STONE T�R�NDEN STONE DE���KEN�N� INT TURUNDEN INDEXE �EV�R
                game.setStoneSelected(false);
                game.setSelectedStone(null);
                game.switchTurn();
                return true;
            }else {//bu durumda place iptal ta� torbaya geri koyulacak
                bag.addStone(stone, stone.getIndex());
                game.setStoneSelected(false);
                Terminal.printError("there is already a stone in that cell!");
                return false;
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
            bag.addStone(stone, stone.getIndex());
            game.setStoneSelected(false);
            return false;
        }
    }

    public void printRow(String[] inputArray, StandardGame game) {
        try {
            if(inputArray.length != 2){
                Terminal.printError("invalid parameter(s) for rowprint command!");
                return;
            }else if(!game.getGameStarted()){
                Terminal.printError("first a game must be started!");
                return;
            }
            int row = Integer.parseInt(inputArray[1]);
            String rowText = "";
            for(int i = 0; i < board.length; i++) {
                if(board[row][i] == null) {
                    rowText += "# ";
                }else {
                    rowText += board[row][i].getIndex() + " ";
                }
            }
            rowText = rowText.substring(0, rowText.length() - 1);
            Terminal.printLine(rowText);
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
        }catch(NumberFormatException e){
            Terminal.printError("rowprint command must be followed by a row number!");
        }
    }

    public void printCol(String[] inputArray, StandardGame game) {
        try {
            if(inputArray.length != 2){
                Terminal.printError("invalid parameter(s) for colprint command!");
                return;
            }else if(!game.getGameStarted()){
                Terminal.printError("first a game must be started!");
                return;
            }
            int col = Integer.parseInt(inputArray[1]);
            String colText = "";
            for(int i = 0; i < board.length; i++) {
                if(board[i][col] == null) {
                    colText += "# ";
                }else {
                    colText += board[i][col].getIndex() + " ";
                }
            }
            colText = colText.substring(0, colText.length() - 1);
            Terminal.printLine(colText);
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
        }catch(NumberFormatException e){
            Terminal.printError("colprint command must be followed by a col number!");
        }
    }
}
