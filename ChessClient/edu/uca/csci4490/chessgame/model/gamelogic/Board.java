package edu.uca.csci4490.chessgame.model.gamelogic;

import edu.uca.csci4490.chessgame.model.gamelogic.piece.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
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

	public void setPieceOnBoard(byte x, byte y, Piece piece) {
		this.board[x][y] = piece;

		if (piece != null) {
			piece.setLocation(new Location(x, y));
		}
	}

	public void setPieceOnBoard(Location loc, Piece piece) {
		setPieceOnBoard(loc.getX(), loc.getY(), piece);
	}
}
