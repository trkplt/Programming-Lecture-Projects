package edu.kit.informatik.baker.board;

import edu.kit.informatik.baker.product.RawMaterial;
import edu.kit.informatik.baker.ui.Main;

/**
 * This class represents the start field in a baker game board.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class StartField extends Field {

    /**
     * Single letter String representation of this type of Field.
     */
    protected static final String ABBREVIATION = "S";

    /**
     * Integer representing the index of this field in all the baker game boards. It is given this way because it is
     * common among all the baker game boards according to the rules.
     */
    protected static final int INDEX = Main.ZERO;

    private static final RawMaterial RAW_MATERIAL = null;

    /**
     * This constructor creates a StartField object with the index 0 (zero).
     */
    protected StartField() {
        super(INDEX, ABBREVIATION, RAW_MATERIAL);
    }
}
