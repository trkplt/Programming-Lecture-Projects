package edu.kit.informatik;

/**
 * This class calculates and writes out with its main method the hairdresser who will be dealing with us.
 * @author Tarık Polat
 * @version 1.0.0
 */
public class Haircut {

    /**
     * This main method calculates and writes out the number (index but starting at 1) of the hairdresser which will
     * take care of us.
     * @param args is a string array. First parameter is the total number of hairdressers.
     *             Second parameter is our position in the queue (queue starts with number 1).
     *             The other parameters are the times in minutes spent by hairdressers per customer.
     *             For example the third parameter is for the first hairdresser, the fourth parameter is for the second
     *             hairdresser and so on.
     */
    public static void main(String[] args) {

        int time = 0;

        int hairdresserCount = Integer.parseInt(args[0]);

        int[] nextCustomerTime = new int[hairdresserCount];
        int[] timePerCustomer = new int[hairdresserCount];
        int myTurn = Integer.parseInt(args[1]);

        for (int i = 0; i < hairdresserCount; i++) {
            timePerCustomer[i] = Integer.parseInt(args[i + 2]);
            nextCustomerTime[i] = 0;
        }

        int freeHairdressers = hairdresserCount;

        while (myTurn > freeHairdressers) {

            for (int i = 0; i < hairdresserCount; i++) {
                if (freeHairdressers > 0) {
                    if (nextCustomerTime[i] == time) {
                        nextCustomerTime[i] = time + timePerCustomer[i];
                        freeHairdressers--;
                        myTurn--;
                    }
                } else {
                    break;
                }
            }

            time++;

            for (int i = 0; i < hairdresserCount; i++) {
                if (nextCustomerTime[i] == time) {
                    freeHairdressers++;
                }
            }
        }

        int lastAssignedHairDresser = -1;

        for (int i = 0; i < hairdresserCount; i++) {
            if (myTurn > 0) {
                if (nextCustomerTime[i] == time) {
                    lastAssignedHairDresser = i + 1;
                    myTurn--;
                }
            } else {
                break;
            }
        }

        System.out.println(lastAssignedHairDresser);
    }
}
