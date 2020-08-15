package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.StringJoiner;

public enum RawMaterial {
    FLOUR("flour"),
    EGG("egg"),
    MILK("milk");

    private final String name;

    RawMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static String getPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            pattern.add(rawMaterial.getName());
        }
        return pattern.toString();
    }

    public static RawMaterial getRawMaterial(String input) {
        for (RawMaterial rawMaterial : RawMaterial.values()) {
            if (input.equals(rawMaterial.getName())) {
                return rawMaterial;
            }
        }
        return null;
    }
}
