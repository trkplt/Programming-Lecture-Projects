package edu.kit.informatik;

public class BountyHunterAppOld {
    //TODO: LOOP VS VAR MI KONTROL ET
    //TODO: BUTUN OBJESI OLUSTURULAMAYAN CLASSLARA PRIVATE CONSTRUCTOR EKLE

    private static int[][] planetsInfo;

    private BountyHunterAppOld() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static boolean processInput(String path) {
        String[] input = Terminal.readFile(path);
        planetsInfo = new int[input.length][];

        try {
            for (int line = 0; line < input.length; line++) {
                String[] numberStrings = input[line].split(" ");

                for (int numberIndex = 0; numberIndex < numberStrings.length; numberIndex++) {
                    planetsInfo[line][numberIndex] = Integer.parseInt(numberStrings[numberIndex]);
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private static boolean planetsInfoValid() {
        if (planetsInfo[0][0] != planetsInfo.length - 1) {
            return false;
        }

        int[][] planetsInOut = new int[planetsInfo[0][0]][2];

        for (int i = 1; i < planetsInfo.length; i++) {
            planetsInOut[i - 1][1] = planetsInfo[i][0];

            for (int j = 1; j < planetsInfo[i].length; j++) {
                planetsInOut[planetsInfo[i][j]][0]++;
            }
        }

        for (int[] planetInOut : planetsInOut) {
            if (planetInOut[0] < planetInOut[1] && planetInOut[0] != 0) {
                return false;
            }
        }

        //TODO: DÜZELT
        return false;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            Terminal.printError("only a path to a text file must be given!");
            return;
        }

        if (!processInput(args[0])) {
            Terminal.printError("the given text file does not meet the conditions!");
            return;
        }

        if (!planetsInfoValid()) {
            Terminal.printError("the given text file does not meet the conditions!");
            return;
        }
    }
}
