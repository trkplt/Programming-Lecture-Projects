package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Deneme {
    public static void main(String[] args) {
        Planet planet1 = new Planet(0, true);
        planet1.addPortalExit(1);
        Planet planet2 = new Planet(1, true);
        planet2.addPortalExit(1);
        planet2.addPortalExit(2);
        planet2.setSearched();
        System.out.println(Objects.equals(planet1, planet2));
        List<Integer> list = new ArrayList<>();
        int[] array = new int[5];
        String str = "";
        array[0] = str.length();
        array[1] = array.length;
        array[2] = list.size();
    }
}
