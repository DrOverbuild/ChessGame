package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;

import java.io.Serializable;

public class PieceSelectionData implements Serializable {
	private int gameID;
	private PieceData piece;

	public PieceSelectionData(int gameID, PieceData piece) {
		this.gameID = gameID;
		this.piece = piece;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public PieceData getPiece() {
		return piece;
	}

	public void setPiece(PieceData piece) {
		this.piece = piece;
	}

	@Override
	public String toString() {
		return "PieceSelectionData{" +
				"gameID=" + gameID +
				", piece=" + piece +
				'}';
	}
}
