package edu.kit.informatik;

public class Bag {

	/*Stone stone0 = new Stone(0, "black", "angular", "small", "hollow");
	Stone stone1 = new Stone(1, "black", "angular", "small", "full");
	Stone stone2 = new Stone(2, "black", "angular", "big", "hollow");
	Stone stone3 = new Stone(3, "black", "angular", "big", "full");
	Stone stone4 = new Stone(4, "black", "round", "small", "hollow");
	Stone stone5 = new Stone(5, "black", "round", "small", "full");
	Stone stone6 = new Stone(6, "black", "round", "big", "hollow");
	Stone stone7 = new Stone(7, "black", "round", "big", "full");
	Stone stone8 = new Stone(8, "white", "angular", "small", "hollow");
	Stone stone9 = new Stone(9, "white", "angular", "small", "full");
	Stone stone10 = new Stone(10, "white", "angular", "big", "hollow");
	Stone stone11 = new Stone(11, "white", "angular", "big", "full");
	Stone stone12 = new Stone(12, "white", "round", "small", "hollow");
	Stone stone13 = new Stone(13, "white", "round", "small", "full");
	Stone stone14 = new Stone(14, "white", "round", "big", "hollow");
	Stone stone15 = new Stone(15, "white", "round", "big", "full");*/

    final String b = "black";
    final String w = "white";
    final String a = "angular";
    final String r = "round";
    final String s = "small";
    final String bg = "big";
    final String h = "hollow";
    final String f = "full";

    Stone stone0 = new Stone(0, b, a, s, h);
    Stone stone1 = new Stone(1, b, a, s, f);
    Stone stone2 = new Stone(2, b, a, bg, h);
    Stone stone3 = new Stone(3, b, a, bg, f);
    Stone stone4 = new Stone(4, b, r, s, h);
    Stone stone5 = new Stone(5, b, r, s, f);
    Stone stone6 = new Stone(6, b, r, bg, h);
    Stone stone7 = new Stone(7, b, r, bg, f);
    Stone stone8 = new Stone(8, w, a, s, h);
    Stone stone9 = new Stone(9, w, a, s, f);
    Stone stone10 = new Stone(10, w, a, bg, h);
    Stone stone11 = new Stone(11, w, a, bg, f);
    Stone stone12 = new Stone(12, w, r, s, h);
    Stone stone13 = new Stone(13, w, r, s, f);
    Stone stone14 = new Stone(14, w, r, bg, h);
    Stone stone15 = new Stone(15, w, r, bg, f);

    //bunun i�ine stone k�sayollar�n� kullanarak yaz
    private Stone[] bag = {
            new Stone(0, b, a, s, h),
            new Stone(1, b, a, s, f),
            new Stone(2, b, a, bg, h),
            new Stone(3, b, a, bg, f),
            new Stone(4, b, r, s, h),
            new Stone(5, b, r, s, f),
            new Stone(6, b, r, bg, h),
            new Stone(7, b, r, bg, f),
            new Stone(8, w, a, s, h),
            new Stone(9, w, a, s, f),
            new Stone(10, w, a, bg, h),
            new Stone(11, w, a, bg, f),
            new Stone(12, w, r, s, h),
            new Stone(13, w, r, s, f),
            new Stone(14, w, r, bg, h),
            new Stone(15, w, r, bg, f)
    };

    private final Stone[] bagCopy = bag;

    public void reset() {
        bag = bagCopy;
    }

    public Stone getStone(String[] inputArray, StandardGame game) {
        try {
            if(inputArray.length != 2){
                Terminal.printError("invalid parameter(s) for select command!");
                return null;
            }else if(!game.getGameStarted()) {
                Terminal.printError("first a game must be started!");
                return null;
            } else if(game.getWin()){
                Terminal.printError("the game is over, a new one must be started!");
                return null;
            }else if(bag[Integer.parseInt(inputArray[1])] == null){
                Terminal.printError(" this stone was already picked!");
                return null;
            }  else if(game.getStoneSelected()){
                Terminal.printError("there is already a selected stone, first a place command must be performed!");
                return null;
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            Terminal.printError("stones are labeled with integers from 0 to 15!");
            return null;
        }catch(NumberFormatException e){
            Terminal.printError("invalid parameter for select command!");
            return null;
        }
        game.setStoneSelected(true);
        return bag[Integer.parseInt(inputArray[1])];
    }

    public void addStone(Stone stone, int index){
        bag[index] = stone;
    }

    public void deleteStone(int index) {
        bag[index] = null;
    }

    public void printAllStones(StandardGame game) {
        if(!game.getGameStarted()){
            Terminal.printError("first a game must be started!");
            return;
        }
        String print = "";
        for(int i = 0; i < bag.length; i++) {
            if(bag[i] != null) {
                print += i + " ";
            }
        }
        if(print.length() >= 2) {
            print = print.substring(0, print.length() - 1);
            Terminal.printLine(print);
        }else {
            Terminal.printError(" there are no stones in the bag!");
        }
    }
}
