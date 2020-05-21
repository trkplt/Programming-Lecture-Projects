package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

public class PrimeFinder {

    public static void main(String[] args) {
        int searched = Integer.parseInt(args[1]);
        int found = 0;
        int counter = 1;

        List<Integer> primes = new ArrayList<Integer>();

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
