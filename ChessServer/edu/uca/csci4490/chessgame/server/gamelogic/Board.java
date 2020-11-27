package edu.uca.csci4490.chessgame.server.gamelogic;

import edu.uca.csci4490.chessgame.model.gamelogic.BoardData;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.*;

import java.util.ArrayList;

public class Board {
	private Piece[][] board;

	public Board() {
		this.board = new Piece[8][8];
	}

	public Piece getPieceAt(int x, int y) {
		return this.board[x][y];
	}

	public Piece getPieceAt(Location loc) {
		if (loc == null) return null;
		return this.board[loc.getX()][loc.getY()];
	}

	/**
	 * Checks if piece is blocked by another piece or by an ally on the destination piece
	 * @param piece
	 * @param destination
	 * @return
	 */
	public boolean pieceIsBlocked(Piece piece, Location destination) {
		if (piece == null || destination == null) return true;

		// check if ally piece is at destination
		if (getPieceAt(destination) != null &&
				getPieceAt(destination).getColor() == piece.getColor()) {
			return true;
		}

		Location original = piece.getLocation();
		Direction dir = original.getDirectionOf(destination);

		if (dir == null) return true;

		// return false if L shaped
		if (dir.isLShaped()) {
			return false; // note that we already checked for ally piece
		}

		// check line between locations for piece
		int distance = 1;
		Location checkedLoc = original.getRelative(dir, distance);
		while (checkedLoc != null && !checkedLoc.equals(destination)) {
			Piece checkedPiece = getPieceAt(checkedLoc);

			if (checkedPiece != null) {
				return checkedPiece.getColor() == piece.getColor();
			}

			distance++;
		}

		return false;
	}

	public void setPieceOnBoard(byte x, byte y, Piece piece) {
		this.board[x][y] = piece;

		if (piece != null) {
			piece.setLocation(new Location(x, y));
		}
	}

	public void setPieceOnBoard(Location loc, Piece piece) {
		setPieceOnBoard(loc.getX(), loc.getY(), piece);
	}

	public void setupBoard() {
		// white ranked pieces
		setPieceOnBoard((byte)0,(byte)0, new Rook(Color.WHITE));
		setPieceOnBoard((byte)1,(byte)0, new Knight(Color.WHITE));
		setPieceOnBoard((byte)2,(byte)0, new Bishop(Color.WHITE));
		setPieceOnBoard((byte)3,(byte)0, new King(Color.WHITE));
		setPieceOnBoard((byte)4,(byte)0, new Queen(Color.WHITE));
		setPieceOnBoard((byte)5,(byte)0, new Bishop(Color.WHITE));
		setPieceOnBoard((byte)6,(byte)0, new Knight(Color.WHITE));
		setPieceOnBoard((byte)7,(byte)0, new Rook(Color.WHITE));

		// white pawns
		for (byte i = 0; i < 8; i++) {
			setPieceOnBoard(i, (byte)1, new Pawn(Color.WHITE));
		}

		// black ranked pieces
		setPieceOnBoard((byte)0,(byte)7, new Rook(Color.BLACK));
		setPieceOnBoard((byte)1,(byte)7, new Knight(Color.BLACK));
		setPieceOnBoard((byte)2,(byte)7, new Bishop(Color.BLACK));
		setPieceOnBoard((byte)3,(byte)7, new King(Color.BLACK));
		setPieceOnBoard((byte)4,(byte)7, new Queen(Color.BLACK));
		setPieceOnBoard((byte)5,(byte)7, new Bishop(Color.BLACK));
		setPieceOnBoard((byte)6,(byte)7, new Knight(Color.BLACK));
		setPieceOnBoard((byte)7,(byte)7, new Rook(Color.BLACK));

		// white pawns
		for (byte i = 0; i < 8; i++) {
			setPieceOnBoard(i, (byte)6, new Pawn(Color.BLACK));
		}
	}

	public void movePiece(Piece piece, Location to) {
		setPieceOnBoard(piece.getLocation(), null);
		setPieceOnBoard(to, piece);
	}

	public Board getHypothetical(Piece piece, Location to) {
		Board board = new Board();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece p = this.getPieceAt(x, y);
				if (p != null) {
					Piece copy = p.copy();
					board.setPieceOnBoard((byte)x, (byte)y, copy);
				}
			}
		}

		board.movePiece(piece, to);
		return board;
	}

	public King getKing(Color color) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece piece = getPieceAt(x, y);

				if (piece instanceof King && piece.getColor() == color) {
					return (King)piece;
				}
			}
		}

		return null; // if this happens, something has gone horribly wrong in the game
	}

	public ArrayList<Piece> allPieces(Color color) {
		ArrayList<Piece> pieces = new ArrayList<>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece piece = getPieceAt(x, y);

				if (piece != null && piece.getColor() == color) {
					pieces.add(piece);
				}
			}
		}

		return pieces;
	}

	public boolean pieceIsEndangered(Piece piece) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece potentialThreat = getPieceAt(x, y);
				if (potentialThreat != null && potentialThreat.getColor() != piece.getColor()) {
					if (!potentialThreat.filterAvailableLocations(this).contains(piece.getLocation())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public BoardData data() {
		BoardData data = new BoardData();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece piece = this.getPieceAt(x, y);

				if (piece == null) {
					data.setPieceOnBoard((byte) x, (byte) y, null);
				} else {
					data.setPieceOnBoard((byte) x, (byte) y, piece.data());
				}
			}
		}
		return data;
	}
}
