package edu.uca.csci4490.chessgame.model.gamelogic;

import java.io.Serializable;

public class BoardData implements Serializable {
	private PieceData[][] board;

	public BoardData() {
		this.board = new PieceData[8][8];
	}

	public PieceData[][] getBoard() {
		return board;
	}

	public void setBoard(PieceData[][] board) {
		this.board = board;
	}

	public PieceData getPieceAt(int x, int y) {
		return this.board[x][y];
	}

	public PieceData getPieceAt(LocationData loc) {
		if (loc == null) return null;
		return this.board[loc.getX()][loc.getY()];
	}

	public void setPieceOnBoard(byte x, byte y, PieceData piece) {
		this.board[x][y] = piece;

		if (piece != null) {
			piece.setLocation(new LocationData(x, y));
		}
	}

	public void setPieceOnBoard(LocationData loc, PieceData piece) {
		setPieceOnBoard(loc.getX(), loc.getY(), piece);
	}
}
