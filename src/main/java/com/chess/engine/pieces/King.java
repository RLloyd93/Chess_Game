package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{

    private final static int[] PIECE_MOVE_POSITION_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        int pieceMoveCoordinate;

        for (final int currentPieceOffset : PIECE_MOVE_POSITION_COORDINATES) {
            pieceMoveCoordinate = this.piecePosition + currentPieceOffset;

            if (BoardUtils.isValidTileMove(pieceMoveCoordinate)) {
                final Tile pieceMoveTile = board.getTile(pieceMoveCoordinate);

                if (isFirstColumnExclusion(this.piecePosition, currentPieceOffset) || isEighthColumnExclusion(this.piecePosition, currentPieceOffset)){
                    continue;
                }

                if (!pieceMoveTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
                } else {
                    final Piece pieceAtDestination = pieceMoveTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();

                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new Move.AttackMove(board, this, pieceMoveCoordinate, pieceAtDestination));
                    }
                }
            }

        }

        return legalMoves;
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int pieceOffset) {

        return BoardUtils.FIRST_COLUMN[currentPosition] && (pieceOffset == -9 || pieceOffset == -1 ||
                pieceOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.EIGTH_COLUMN[currentPosition] && (pieceOffset == -7 || pieceOffset == 1 || pieceOffset == 9);
    }
}
