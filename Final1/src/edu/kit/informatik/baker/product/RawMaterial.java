package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.StringJoiner;

/**
 * This enum contains the raw materials in a baker game. It also encapsulates information about these raw materials
 * and provides some useful operations on them.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public enum RawMaterial {

    /**
     * This is a {@link edu.kit.informatik.baker.product.RawMaterial} with the name "flour".
     */
    FLOUR("flour"),

    /**
     * This is a {@link edu.kit.informatik.baker.product.RawMaterial} with the name "egg".
     */
    EGG("egg"),

    /**
     * This is a {@link edu.kit.informatik.baker.product.RawMaterial} with the name "milk".
     */
    MILK("milk");

    private final String name;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.product.RawMaterial} with the given name.
     *
     * @param name is a String containing the name of the raw material
     */
    RawMaterial(String name) {
        this.name = name;
    }

    /**
     * This method provides the name of this {@link edu.kit.informatik.baker.product.RawMaterial}.
     *
     * @return a String containing the name of this raw material
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method provides the pattern for {@link edu.kit.informatik.baker.product.RawMaterial}s in "(x|y|...)"
     * format where x and y are raw materials.
     *
     * @return a String containing the pattern of the raw materials
     */
    public static String getPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            pattern.add(rawMaterial.getName());
        }
        return pattern.toString();
    }

    /**
     * This static method provides the wanted {@link edu.kit.informatik.baker.product.RawMaterial} according to the
     * given name.
     *
     * @param name is the String containing the name of the raw material
     * @return the matched raw material or null if there was no match
     */
    public static RawMaterial getRawMaterial(String name) {
        for (RawMaterial rawMaterial : RawMaterial.values()) {
            if (name.equals(rawMaterial.getName())) {
                return rawMaterial;
            }
        }
        return null;
    }
}
