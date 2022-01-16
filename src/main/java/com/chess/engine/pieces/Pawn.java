package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] PIECE_MOVE_POSITION_COORDINATES = {8, 16, 7, 9};

    public Pawn(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentPieceOffset : PIECE_MOVE_POSITION_COORDINATES) {

            int pieceMoveCoordinate = this.piecePosition + (this.pieceColor.getDirection() * currentPieceOffset);

            if (!BoardUtils.isValidTileMove(pieceMoveCoordinate)) {
                continue;
            }

            if (currentPieceOffset == 8 && board.getTile(pieceMoveCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
            } else if (currentPieceOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite())) {
                final int behindPieceMoveDestination = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if (!board.getTile(behindPieceMoveDestination).isTileOccupied() &&
                        !board.getTile(pieceMoveCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
                }
                //this checks that Pawn is making a legal move in certain areas
            } else if (currentPieceOffset == 7 && !((BoardUtils.EIGTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                if (board.getTile(pieceMoveCoordinate).isTileOccupied()) {
                    final Piece pieceOnDestination = board.getTile(pieceMoveCoordinate).getPiece();
                    if (this.pieceColor != pieceOnDestination.getPieceColor()) {
                        legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
                    }
                }
            } else if (currentPieceOffset == 9 && !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                    (BoardUtils.EIGTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                if (board.getTile(pieceMoveCoordinate).isTileOccupied()) {
                    final Piece pieceOnDestination = board.getTile(pieceMoveCoordinate).getPiece();
                    if (this.pieceColor != pieceOnDestination.getPieceColor()) {
                        legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
                    }
                }
            }
        }
        return legalMoves;
    }
}
