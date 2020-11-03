package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class Knight extends Piece {
	public Knight() {}

	public Knight(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		// TODO implement
		return null;
	}

	@Override
	public ArrayList<Location> filterAvailableLocations(Board board) {
		// TODO implement
		return null;
	}

	@Override
	public Piece copy() {
		Piece knight = new Knight();
		knight.setColor(this.getColor());
		knight.setLocation(this.getLocation());
		knight.setImage(this.getImage());
		return knight;
	}
}
