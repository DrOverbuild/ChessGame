package edu.uca.csci4490.chessgame.model.gamelogic;

import java.io.Serializable;

public class MoveData implements Serializable {
	private PieceData piece;
	private LocationData from;
	private LocationData to;

	public PieceData getPiece() {
		return piece;
	}

	public void setPiece(PieceData piece) {
		this.piece = piece;
	}

	public LocationData getFrom() {
		return from;
	}

	public void setFrom(LocationData from) {
		this.from = from;
	}

	public LocationData getTo() {
		return to;
	}

	public void setTo(LocationData to) {
		this.to = to;
	}

	public MoveData(PieceData piece, LocationData from, LocationData to) {
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
}
