package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class Queen extends Piece {
	public Queen() { }

	public Queen(Color color) {
		super();
		this.setColor(color);
		this.setImage("queen");
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
		Piece queen = new Queen();
		queen.setColor(this.getColor());
		queen.setLocation(this.getLocation());
		queen.setImage(this.getImage());
		return queen;
	}
}
