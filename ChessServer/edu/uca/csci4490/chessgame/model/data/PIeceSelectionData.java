package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;

import java.io.Serializable;

public class PIeceSelectionData implements Serializable {
	private int gameID;
	private Piece piece;

	public PIeceSelectionData(int gameID, Piece piece) {
		this.gameID = gameID;
		this.piece = piece;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
