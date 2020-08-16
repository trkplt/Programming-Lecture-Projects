package edu.kit.informatik.baker.board;

import edu.kit.informatik.baker.ui.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a game board from a baker game. It encapsulates {@link edu.kit.informatik.baker.board.Field}s
 * in it and provides some useful operations on them.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Board {

    private static final int MIN_NUM_OF_MIDDLE_FIELDS = 0;
    private static final int MAX_NUM_OF_MIDDLE_FIELDS = 23;
    private static final int SAME_FIELD_MAX_MARGIN = 4;
    private static final String FIELDS_PATTERN = Main.PATTERN_GROUP_INIT + Mill.ABBREVIATION + Main.PATTERN_OR
            + CowPasture.ABBREVIATION + Main.PATTERN_OR + HenHouse.ABBREVIATION + Main.PATTERN_GROUP_END;
    private static final String BOARD_PATTERN = StartField.ABBREVIATION + Main.SEPARATOR + Main.PATTERN_GROUP_INIT
            + FIELDS_PATTERN + Main.SEPARATOR + Main.PATTERN_GROUP_END + Main.PATTERN_COUNT_INIT
            + MIN_NUM_OF_MIDDLE_FIELDS + Main.COMMA + MAX_NUM_OF_MIDDLE_FIELDS + Main.PATTERN_COUNT_END
            + Main.PATTERN_GROUP_INIT + FIELDS_PATTERN + Main.PATTERN_GROUP_END;

    private final List<Field> fields;
    private final boolean boardValid;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.board.Board} object using the given string
     * representation of a board and checks its validity. The validity of the board can be checked via
     * {@link #isBoardValid()}. If the board is not valid, the instance methods return null.
     *
     * @param fieldString is the string representation of a baker game board
     */
    public Board(String fieldString) {
        Pattern pattern = Pattern.compile(BOARD_PATTERN);
        Matcher matcher = pattern.matcher(fieldString);

        if (!matcher.matches()) {
            this.fields = null;
            this.boardValid = false;
            return;
        }

        this.fields = new ArrayList<>();
        int fieldIndex = StartField.INDEX;

        for (int i = Main.ZERO; i < fieldString.length(); i++) {
            String representation = fieldString.substring(i, i + Field.ABBREVIATION_LENGTH);
            if (representation.equals(Main.SEPARATOR)) {
                continue;
            }

            Field field = Field.createField(fieldIndex, representation);

            if (field == null) {
                this.boardValid = false;
                return;
            }

            this.fields.add(field);
            ++fieldIndex;
        }

        this.boardValid = fieldNumRulesFollowed() && nextFieldRuleFollowed() && fourFieldsRuleFollowed();
    }

    private boolean fieldNumRulesFollowed() {
        int startCounter = 0;
        int millCounter = 0;
        int cowPastureCounter = 0;
        int henHouseCounter = 0;

        for (Field field : this.fields) {
            switch (field.getAbbreviation()) {
                case StartField.ABBREVIATION:
                    ++startCounter;
                    break;
                case Mill.ABBREVIATION:
                    ++millCounter;
                    break;
                case CowPasture.ABBREVIATION:
                    ++cowPastureCounter;
                    break;
                case HenHouse.ABBREVIATION:
                    ++henHouseCounter;
                    break;
                default:
                    break;
            }
        }

        return startCounter == Field.MIN_FIELD_AMOUNT
                && millCounter >= Field.MIN_FIELD_AMOUNT && millCounter <= Field.MAX_FIELD_AMOUNT
                && cowPastureCounter >= Field.MIN_FIELD_AMOUNT && cowPastureCounter <= Field.MAX_FIELD_AMOUNT
                && henHouseCounter >= Field.MIN_FIELD_AMOUNT && henHouseCounter <= Field.MAX_FIELD_AMOUNT;
    }

    private boolean nextFieldRuleFollowed() {
        String prevFieldAbbreviation = Main.EMPTY_STRING;

        for (Field field : this.fields) {
            String currentFieldAbbreviation = field.getAbbreviation();

            if (currentFieldAbbreviation.equals(prevFieldAbbreviation)) {
                return false;
            }

            prevFieldAbbreviation = currentFieldAbbreviation;
        }

        String secondFieldAbbreviation = this.fields.get(StartField.INDEX + Main.ONE).getAbbreviation();
        return !secondFieldAbbreviation.equals(prevFieldAbbreviation);
    }

    private boolean fourFieldsRuleCounter(List<Integer> fieldIndexList) {
        int prevIndex = StartField.INDEX;

        for (Integer currentIndex : fieldIndexList) {
            if (currentIndex - prevIndex > SAME_FIELD_MAX_MARGIN) {
                return false;
            }
            prevIndex = currentIndex;
        }

        return this.fields.size() - Main.ONE - prevIndex + fieldIndexList.get(Main.ZERO)
                <= SAME_FIELD_MAX_MARGIN;
    }

    private boolean fourFieldsRuleFollowed() {
        List<Integer> millIndexes = new ArrayList<>();
        List<Integer> cowPastureIndexes = new ArrayList<>();
        List<Integer> henHouseIndexes = new ArrayList<>();

        for (Field field : this.fields) {
            switch (field.getAbbreviation()) {
                case Mill.ABBREVIATION:
                    millIndexes.add(field.getIndex());
                    break;
                case CowPasture.ABBREVIATION:
                    cowPastureIndexes.add(field.getIndex());
                    break;
                case HenHouse.ABBREVIATION:
                    henHouseIndexes.add(field.getIndex());
                    break;
                default:
                    break;
            }
        }

        return fourFieldsRuleCounter(millIndexes)
                && fourFieldsRuleCounter(cowPastureIndexes)
                && fourFieldsRuleCounter(henHouseIndexes);
    }

    /**
     * This method provides the {@link edu.kit.informatik.baker.board.StartField} of this board.
     *
     * @return the {@link edu.kit.informatik.baker.board.StartField} of this board
     */
    public Field getStartField() {
        if (!this.boardValid) {
            return null;
        }
        return this.fields.get(StartField.INDEX);
    }

    /**
     * This method checks if this {@link edu.kit.informatik.baker.board.Board} is valid.
     *
     * @return true if the board is valid, false otherwise
     */
    public boolean isBoardValid() {
        return this.boardValid;
    }

    /**
     * This method provides the resulting {@link edu.kit.informatik.baker.board.Field}, if a player was to move
     * forward the given times on the board starting from the given {@link edu.kit.informatik.baker.board.Field}.
     *
     * @param field is the initial {@link edu.kit.informatik.baker.board.Field} on the board from where the move starts
     * @param moves is the amount of forward moves on the {@link edu.kit.informatik.baker.board.Board}
     * @return the resulting {@link edu.kit.informatik.baker.board.Field} after the moves
     */
    public Field getNextField(Field field, int moves) {
        if (!this.boardValid) {
            return null;
        }

        int index = field.getIndex() + moves;

        if (index < this.fields.size()) {
            return this.fields.get(index);
        } else {
            return getNextField(this.fields.get(StartField.INDEX), index - this.fields.size());
        }
    }
}
