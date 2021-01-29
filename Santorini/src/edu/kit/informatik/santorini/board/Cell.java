package edu.kit.informatik.santorini.board;

import edu.kit.informatik.santorini.Game;
import edu.kit.informatik.santorini.piece.Piece;
import edu.kit.informatik.santorini.piece.PieceType;

import java.util.Vector;

public class Cell {

    private final Vector<Piece> pieces;

    public Cell(Piece playerPiece) {
        this.pieces = new Vector<>(Game.MAXNUMOFPIECESINCELL);

        if (playerPiece != null) {
            this.pieces.add(playerPiece);
        }
    }

    public Cell() {
        this(null);
    }

    public PieceType getTopPieceType() {
        return this.pieces.size() > Game.EMPTYCELLSIZE ? this.pieces.lastElement().getPieceType() : null;
    }

    //TODO: WITH APOLLO RETURNS ENEMY PIECE TO INITIAL CELL, WHAT IF INITIAL CELL IS OCCUPIED
    public boolean addPiece(Piece piece) throws NullPointerException {
        if (piece == null) {
            throw new NullPointerException();
        }

        if (this.getTopPieceType() == PieceType.PLAYER) {
            return false;
        } else {
            return this.pieces.add(piece);
        }
    }
}
