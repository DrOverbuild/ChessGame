package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
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
	public ArrayList<Location> allMovableLocations(Game game) {
		// TODO implement
		return null;
	}

	@Override
	public ArrayList<Location> filterAvailableLocations(Game game) {
		// TODO implement
		return null;
	}
}
