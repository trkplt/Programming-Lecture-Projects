package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for logic and maintaining in- and output of the prime finder programme.
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class PrimeFinder {

    /*
    Private because no objects of this class shall be created, this class is only for logic and maintaining
    in- and output of the prime finder programme.
     */
    private PrimeFinder() { }

    /**
     * Main method for the program. This method contains both the logic and the maintaining of in- and output of the
     * programme.
     * @param args arguments of the programme
     */
    public static void main(String[] args) {
        int searched = Integer.parseInt(args[1]);
        int found = 0;
        int counter = 1;

        List<Integer> primes = new ArrayList<>();

        while (found < searched) {
            counter++;
            boolean isPrime = true;

            for (Integer prime : primes) {
                if (counter % prime == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primes.add(counter);
                found++;
            }
        }

        Terminal.printLine(primes.get(primes.size() - 1));
    }
}
