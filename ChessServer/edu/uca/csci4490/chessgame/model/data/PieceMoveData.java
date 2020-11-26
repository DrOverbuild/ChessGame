package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;

import java.io.Serializable;

public class PieceMoveData implements Serializable {
	private int gameID;
	private Piece piece;
	private Location moveTo;
	private Class<? extends Piece> promoteTo;

	public PieceMoveData(int gameID, Piece piece, Location moveTo, Class<? extends Piece> promoteTo) {
		this.gameID = gameID;
		this.piece = piece;
		this.moveTo = moveTo;
		this.promoteTo = promoteTo;
	}

	public PieceMoveData(int gameID, Piece piece, Location moveTo) {
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

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Location getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(Location moveTo) {
		this.moveTo = moveTo;
	}

	public Class<? extends Piece> getPromoteTo() {
		return promoteTo;
	}

	public void setPromoteTo(Class<? extends Piece> promoteTo) {
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
