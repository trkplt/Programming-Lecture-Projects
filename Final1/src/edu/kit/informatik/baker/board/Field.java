package edu.kit.informatik.baker.board;

import edu.kit.informatik.baker.product.RawMaterial;

/**
 * This class creates a template for the fields from the {@link edu.kit.informatik.baker.board.Board} from the baker
 * game and encapsulates relevant information about the fields. It also possesses a factory method to create Field
 * objects.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public abstract class Field {

    /**
     * The length of abbreviation of the subclasses of this abstract class as an integer.
     */
    protected static final int ABBREVIATION_LENGTH = 1;

    /**
     * The minimum amount of a subclass object in a {@link edu.kit.informatik.baker.board.Board} from baker game.
     */
    protected static final int MIN_FIELD_AMOUNT = 1;

    /**
     * The maximum amount of a subclass object in a {@link edu.kit.informatik.baker.board.Board} from baker game.
     */
    protected static final int MAX_FIELD_AMOUNT = 8;

    private final int index;
    private final String abbreviation;
    private final RawMaterial rawMaterial;

    /**
     * This constructor can only be used by subclasses. It instantiates the private attributes with the given
     * parameters so that the instance methods of this class can be used via subclasses of this class.
     *
     * @param index is an integer that serves as the index of this field in a baker game
     * {@link edu.kit.informatik.baker.board.Board}
     * @param abbreviation is the single letter String representation of this
     * {@link edu.kit.informatik.baker.board.Field}
     * @param rawMaterial is the raw material that can be harvested from this
     * {@link edu.kit.informatik.baker.board.Field}
     */
    protected Field(int index, String abbreviation, RawMaterial rawMaterial) {
        this.index = index;
        this.abbreviation = abbreviation;
        this.rawMaterial = rawMaterial;
    }

    /**
     * This method provides the index of this {@link edu.kit.informatik.baker.board.Field}.
     *
     * @return an integer that serves as the index of this {@link edu.kit.informatik.baker.board.Field}
     */
    protected int getIndex() {
        return this.index;
    }

    /**
     * This method provides the raw material that can be extracted from this
     * {@link edu.kit.informatik.baker.board.Field}.
     *
     * @return the raw material that can be extracted from this {@link edu.kit.informatik.baker.board.Field}
     */
    public RawMaterial getRawMaterial() {
        return this.rawMaterial;
    }

    /**
     * This method provides the single letter representation of this {@link edu.kit.informatik.baker.board.Field}.
     *
     * @return a single letter String that represents this {@link edu.kit.informatik.baker.board.Field}
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * This static method provides a Field object instantiated with the given parameters. If the given abbreviation is
     * the abbreviation of the {@link edu.kit.informatik.baker.board.StartField}, then the common index of StartFields
     * is used instead of the given index.
     *
     * @param index is an integer that serves as the index of this field in a baker game board
     * @param abbreviation is the single letter String representation of this
     * {@link edu.kit.informatik.baker.board.Field}
     * @return a {@link edu.kit.informatik.baker.board.Field} object instantiated with the given parameters
     */
    protected static Field createField(int index, String abbreviation) {
        Field field;
        switch (abbreviation) {
            case StartField.ABBREVIATION:
                field = new StartField();
                break;
            case Mill.ABBREVIATION:
                field = new Mill(index);
                break;
            case CowPasture.ABBREVIATION:
                field = new CowPasture(index);
                break;
            case HenHouse.ABBREVIATION:
                field = new HenHouse(index);
                break;
            default:
                field = null;
                break;
        }
        return field;
    }
}
