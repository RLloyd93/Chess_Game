package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int moveCoordinate;

    private Move(final Board board, final Piece movedPiece, final int moveCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.moveCoordinate = moveCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int moveCoordinate) {
            super(board, movedPiece, moveCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int moveCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, moveCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
