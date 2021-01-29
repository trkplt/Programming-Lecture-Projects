package edu.kit.informatik.santorini.board;

import edu.kit.informatik.santorini.Game;
import edu.kit.informatik.santorini.piece.PieceType;

public class Field {

    private final Cell[][] cells;

    public Field(Cell[][] cells) throws NullPointerException, IllegalArgumentException {
        if (!Field.isFieldNullFree(cells)) {
            throw new NullPointerException();
        }

        if (!Field.areFieldDimensionsCorrect(cells)) {
            throw new IllegalArgumentException();
        }

        if (!Field.isPlayerPieceCountCorrect(cells)) {
            throw new IllegalArgumentException();
        }

        //TODO: FURTHER CHECKS NECESSARY?

        this.cells = cells;
    }

    private static boolean isFieldNullFree(Cell[][] cells) {
        if (cells == null) {
            return  false;
        }

        for (Cell[] row : cells) {
            if (row == null) {
                return false;
            }

            for (Cell cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean areFieldDimensionsCorrect(Cell[][] cells) {
        if (cells.length == Game.FIELDROWCOUNT) {

            for (Cell[] row : cells) {
                if (row.length != Game.FIELDCOLUMNCOUNT) {
                    return false;
                }
            }
            return true;

        } else if (cells.length == Game.FIELDCOLUMNCOUNT) {

            for (Cell[] column : cells) {
                if (column.length != Game.FIELDROWCOUNT) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }
    }

    private static boolean isPlayerPieceCountCorrect(Cell[][] cells) {
        int counter = 0;

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getTopPieceType() == PieceType.PLAYER) {
                    counter++;
                }
            }
        }

        return counter == Game.PLAYERCOUNT * Game.PIECEPERPLAYER;
    }


}
