package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;
import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;

import java.io.Serializable;

public class PieceMoveData implements Serializable {
	private int gameID;
	private PieceData piece;
	private LocationData moveTo;
	private String promoteTo;

	public PieceMoveData(int gameID, PieceData piece, LocationData moveTo, String promoteTo) {
		this.gameID = gameID;
		this.piece = piece;
		this.moveTo = moveTo;
		this.promoteTo = promoteTo;
	}

	public PieceMoveData(int gameID, PieceData piece, LocationData moveTo) {
		this.gameID = gameID;
		this.piece = piece;
		this.moveTo = moveTo;
		this.promoteTo = null;
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

	public LocationData getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(LocationData moveTo) {
		this.moveTo = moveTo;
	}

	public String getPromoteTo() {
		return promoteTo;
	}

	public void setPromoteTo(String promoteTo) {
		this.promoteTo = promoteTo;
	}

	public boolean hasPromotion() {
		return this.promoteTo != null;
	}

	@Override
	public String toString() {
		return "PieceMoveData{" +
				"gameID=" + gameID +
				", piece=" + piece +
				", moveTo=" + moveTo +
				", promoteTo=" + promoteTo +
				'}';
	}
}
