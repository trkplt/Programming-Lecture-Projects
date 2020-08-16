package edu.kit.informatik.baker.board;

import edu.kit.informatik.baker.product.RawMaterial;

/**
 * This class represents a CowPasture field in a baker game {@link edu.kit.informatik.baker.board.Board}.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class CowPasture extends Field {

    /**
     * Single letter String representation of this type of {@link edu.kit.informatik.baker.board.Field}.
     */
    protected static final String ABBREVIATION = "C";

    private static final RawMaterial RAW_MATERIAL = RawMaterial.MILK;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.board.CowPasture} object with the given index.
     *
     * @param index is an integer that serves as the index of this {@link edu.kit.informatik.baker.board.Field} in a
     *              baker game {@link edu.kit.informatik.baker.board.Board}
     */
    protected CowPasture(int index) {
        super(index, ABBREVIATION, RAW_MATERIAL);
    }
}
