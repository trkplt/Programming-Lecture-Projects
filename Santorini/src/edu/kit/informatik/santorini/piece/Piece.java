package edu.kit.informatik.santorini.piece;

public class Piece {

    private final PieceType type;
    private final String name;

    public Piece(PieceType type) throws NullPointerException {
        if (type == null) {
            throw new NullPointerException();
        }

        if (type == PieceType.PLAYER) {
            throw new IllegalArgumentException();
        }

        this.type = type;
        this.name = null;
    }

    public Piece(String playerPieceName) throws NullPointerException {
        if (playerPieceName == null) {
            throw new NullPointerException();
        }

        //TODO: NAME CONVENTION CHECK IN COMMAND WITH [0-9a-z]+

        this.name = playerPieceName;
        this.type = PieceType.PLAYER;
    }

    public PieceType getPieceType() {
        return this.type;
    }
}
