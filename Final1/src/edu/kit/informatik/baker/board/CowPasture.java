package edu.kit.informatik.baker.board;

import edu.kit.informatik.baker.product.RawMaterial;

/**
 * This class represents a CowPasture field in a baker game board.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class CowPasture extends Field {

    /**
     * Single letter String representation of this type of Field.
     */
    protected static final String ABBREVIATION = "C";

    private static final RawMaterial RAW_MATERIAL = RawMaterial.MILK;

    /**
     * This constructor creates a CowPasture object with the given index.
     *
     * @param index is an integer that serves as the index of this field in a baker game board
     */
    protected CowPasture(int index) {
        super(index, ABBREVIATION, RAW_MATERIAL);
    }
}
