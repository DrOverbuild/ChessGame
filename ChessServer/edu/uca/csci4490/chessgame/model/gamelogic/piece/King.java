package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class King extends Piece {
	public King() { }

	public King(Color color) {
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
		Piece king = new King();
		king.setColor(this.getColor());
		king.setLocation(this.getLocation());
		king.setImage(this.getImage());
		return king;
	}
}
