package edu.kit.informatik;

public class BoardGameMain {

    protected static boolean go = true;
    protected static StandardGame game = new StandardGame();
    protected static StandardBoard board = new StandardBoard();
    protected static Bag bag = new Bag();

    public static void main(String[] args) {

        while(go) {
            try {
                String input = Terminal.readLine();
                String[] inputArray = input.split(" ");
                Stone stone = null;

                switch(inputArray[0]) {
                    case "start":
                        game.startGame(inputArray, board, bag);
                        break;
                    case "select":
                        game.setSelectedStone(bag.getStone(inputArray, game));
                        stone = game.getSelectedStone();
                        if(stone != null){
                            bag.deleteStone(stone.getIndex());
                            Terminal.printLine("OK");
                        }
                        break;
                    case "place":
                        if(board.placeStone(board, game, bag, inputArray, stone) && !game.getWin()){
                            Terminal.printLine("OK");
                        }
                        break;
                    case "bag":
                        bag.printAllStones(game);
                        break;
                    case "rowprint": // B�T�N BEFEHLLERE INPUTARRAY UZUNLUK KONTROL� KOY, �LK �K� PARAMETRE DO�RU SONRASI SA�MA �EYLERDE DOLU OLAB�L�R!!
                        board.printRow(inputArray, game);
                        break;
                    case "colprint":
                        board.printCol(inputArray, game);
                        break;
                    case "quit":
                        go = false;
                        break;
                    default:
                        Terminal.printError(" invalid command!");
                }
            }catch(NullPointerException e) {
                Terminal.printError(" invalid command!");
            }
        }
    }

}
