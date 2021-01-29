package edu.kit.informatik;

public class TorusBoard extends StandardBoard{

    private int newLoc(int loc) {
        if(loc < 0) {
            return ((loc % 6) + 6) % 6;
        }
        return loc % 6;
    }

    @Override
    public Stone getStone(int row, int col) {
        return super.getStone(newLoc(row), newLoc(col)); //   ????
    }

    @Override
    public boolean placeStone(StandardBoard board, StandardGame game, Bag bag, String[] inputArray, Stone stone) {

        try{
            if(inputArray.length == 2 && game.getGameStarted() && stone != null){
                String[] rowCol = inputArray[1].split(";");
                inputArray[1] = newLoc(Integer.parseInt(rowCol[0])) + ";" + newLoc(Integer.parseInt(rowCol[1]));
            }
            return super.placeStone(board, game, bag, inputArray, stone);
        }catch(ArrayIndexOutOfBoundsException e){
            Terminal.printError("place command must be followed by row and colon numbers separated by a semi colon!");
            return false;
        }catch (NumberFormatException e){
            Terminal.printError("place command must be followed by row and colon numbers separated by a semi colon!");
            return false;
        }
    }

    @Override
    public void printRow(String[] inputArray, StandardGame game) {

        try{
            if(inputArray.length == 2 && game.getGameStarted()){
                inputArray[1] = String.valueOf(newLoc(Integer.parseInt(inputArray[1])));
            }
            super.printRow(inputArray, game);
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
        }catch(NumberFormatException e){
            Terminal.printError("rowprint command must be followed by a row number!");
        }
    }

    @Override
    public void printCol(String[] inputArray, StandardGame game) {
        try{
            if(inputArray.length == 2 && game.getGameStarted()){
                inputArray[1] = String.valueOf(newLoc(Integer.parseInt(inputArray[1])));
            }
            super.printCol(inputArray, game);
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("the board is 6x6 sized!");
        }catch(NumberFormatException e){
            Terminal.printError("colprint command must be followed by a col number!");
        }
    }
}
