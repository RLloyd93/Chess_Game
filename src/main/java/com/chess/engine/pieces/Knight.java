package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

    private final static int[] KNIGHT_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentPieceOffset : KNIGHT_MOVE_COORDINATES) {
            final int pieceMoveCoordinate = this.piecePosition + currentPieceOffset;

            if (BoardUtils.isValidTileMove(pieceMoveCoordinate)) {

                if (isFirstColumnExclusion(this.piecePosition, currentPieceOffset) ||
                    isSecondColumnExclusion(this.piecePosition, currentPieceOffset) ||
                    isSeventhColumnExclusion(this.piecePosition, currentPieceOffset) ||
                    isEigthColumnExclusion(this.piecePosition, currentPieceOffset)) {
                    continue;
                }

                final Tile pieceMoveTile = board.getTile(pieceMoveCoordinate);

                if (!pieceMoveTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, pieceMoveCoordinate));
                } else {
                    final Piece pieceAtDestination = pieceMoveTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();

                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new AttackMove(board, this, pieceMoveCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int pieceOffset) {

        return BoardUtils.FIRST_COLUMN[currentPosition] && (pieceOffset == -17 || pieceOffset == -10 ||
                pieceOffset == 6 || pieceOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (pieceOffset == -10 || pieceOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (pieceOffset == -6 || pieceOffset ==10);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.EIGTH_COLUMN[currentPosition] && (pieceOffset == -15 || pieceOffset == -6 ||
                pieceOffset == 10 || pieceOffset == 17);
    }
}
