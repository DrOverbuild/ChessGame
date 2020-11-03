package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class Pawn extends Piece {
	private boolean hasReachedTheOtherEnd = false;

	public Pawn() { }

	public Pawn(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}

	public boolean hasReachedTheOtherEnd() {
		return hasReachedTheOtherEnd;
	}

	public void setHasReachedTheOtherEnd(boolean hasReachedTheOtherEnd) {
		this.hasReachedTheOtherEnd = hasReachedTheOtherEnd;
	}

	public byte getOtherEnd() {
		return getColor() == Color.WHITE ? (byte)0 : (byte)7;
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		ArrayList<Location> locations = new ArrayList<>();

		return null;
	}

	@Override
	public ArrayList<Location> filterAvailableLocations(Board board) {
		// TODO implement
		return null;
	}

	@Override
	public Piece copy() {
		Piece pawn = new Pawn();
		pawn.setColor(this.getColor());
		pawn.setLocation(this.getLocation());
		pawn.setImage(this.getImage());
		return pawn;
	}
}
