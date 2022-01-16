package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece{

    private final static int[] PIECE_MOVE_POSITION_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentPieceOffset : PIECE_MOVE_POSITION_COORDINATES) {
            int pieceMoveCoordinate = this.piecePosition;
            //ensures that our move is legal & won't go off the board
            while (BoardUtils.isValidTileMove(pieceMoveCoordinate)) {
                //in the instances where a piece coordinate wouldn't represent a legal move for a 'Bishop', we break
                if (isFirstColumnExclusion(pieceMoveCoordinate, currentPieceOffset) ||
                        isEighthColumnExclusion(pieceMoveCoordinate, currentPieceOffset)) {
                    break;
                }
            }
            pieceMoveCoordinate += currentPieceOffset;

            if (BoardUtils.isValidTileMove(pieceMoveCoordinate)) {

                final Tile pieceMoveTile = board.getTile(pieceMoveCoordinate);
                //as long as tile is open, this will add a Major Move to the list
                if (!pieceMoveTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, pieceMoveCoordinate));
                } else {
                    final Piece pieceAtDestination = pieceMoveTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    //if there is a piece on this tile & it's not our color, then it becomes an Attack Move
                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new Move.AttackMove(board, this, pieceMoveCoordinate, pieceAtDestination));
                    }
                    //stops the loop once it reaches the desired tile
                    break;
                }
            }
        }
        return legalMoves;
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (pieceOffset == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int pieceOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (pieceOffset == 1);
    }
}
