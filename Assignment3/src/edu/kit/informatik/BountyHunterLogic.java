package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic class for BountyHunterApp. Contains methods for calculations of app.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class BountyHunterLogic {

    //contains the lines from given text file
    private final String[] input;

    //int version of input
    private int[][] planetsInfo;

    /*
    contains in and out connection numbers of planets
    planets listed according to their indexes
    [x][0] is in, [x][1] is out
     */
    private int[][] planetsInOut;

    /**
     * Constructs a BountyHunterLogic object with the given input.
     * Does not imply that the given input is correct.
     * To see if the input is correct, getDroidCount() must be run.
     *
     * @param input array containing lines from the given text file
     */
    public BountyHunterLogic(String[] input) {
        this.input = input;
    }

    /*
    checks if the string[] input can be converted to an int array and does so if possible.
    returns true if conversion successfull, false otherwise.
     */
    private boolean processInput() {
        planetsInfo = new int[input.length][];

        try {
            for (int line = 0; line < input.length; line++) {
                String[] numberStrings = input[line].split(" ");
                planetsInfo[line] = new int[numberStrings.length];

                for (int numberIndex = 0; numberIndex < numberStrings.length; numberIndex++) {
                    planetsInfo[line][numberIndex] = Integer.parseInt(numberStrings[numberIndex]);
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /*
    checks if all possible routes from last element of the list banned are loop-free.
    returns true if they are loop-free, false otherwise.
     */
    private boolean loopFree(List<Integer> banned) {
        int[] lastVisitInfo = planetsInfo[banned.get(banned.size() - 1) + 1];

        if (lastVisitInfo.length != 1) {
            for (int i = 1; i < lastVisitInfo.length; i++) {
                if (banned.contains(lastVisitInfo[i])) {
                    return false;
                }

                List<Integer> newBanned = new ArrayList<>(banned);
                newBanned.add(lastVisitInfo[i]);
                //banned.add(lastVisitInfo[i]);

                if (!loopFree(newBanned)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    checks if the given input meets the conditions.
    returns true if it does, false otherwise.
     */
    private boolean planetsInfoValid() {
        if (planetsInfo[0].length != 1 || planetsInfo[0][0] != planetsInfo.length - 1
                || planetsInfo[0][0] < 2 || planetsInfo[0][0] > 1000) {
            return false;
        }

        planetsInOut = new int[planetsInfo[0][0]][2];

        for (int i = 1; i < planetsInfo.length; i++) {
            if (planetsInfo[i].length < 1 || planetsInfo[i][0] != planetsInfo[i].length - 1
                    || planetsInfo[i][0] < 0 || planetsInfo[i][0] > planetsInfo[0][0] - 1) {
                return false;
            }

            planetsInOut[i - 1][1] = planetsInfo[i][0];

            for (int j = 1; j < planetsInfo[i].length; j++) {
                if (planetsInfo[i][j] == i - 1
                        || planetsInfo[i][j] < 0 || planetsInfo[i][j] > planetsInfo[0][0]) {
                    return false;
                }

                planetsInOut[planetsInfo[i][j]][0]++;
            }
        }

        for (int[] planetInOut : planetsInOut) {
            if ((planetInOut[0] == 0 && planetInOut[1] > 1)
                    || planetInOut[0] != 0 && (planetInOut[0] < planetInOut[1])) {
                return false;
            }
        }

        for (int planetIndex = 0; planetIndex < planetsInfo[0][0]; planetIndex++) {
            List<Integer> banned = new ArrayList<>();
            banned.add(planetIndex);

            if (!loopFree(banned)) {
                return false;
            }
        }

        return true;
    }

    /**
     * To get the minimum number of required droids to cover all the planets.
     *
     * @return the minimum number of required droids
     * @throws IllegalArgumentException if the input given in constructor does not meet the conditions
     */
    public int getDroidCount() throws IllegalArgumentException {
        try {
            if (processInput() && planetsInfoValid()) {
                int droidCounter = 0;

                for (int[] planetInOut : planetsInOut) {
                    droidCounter += (planetInOut[0] == 0 ? 1 : 0);
                }

                return droidCounter;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }
}
