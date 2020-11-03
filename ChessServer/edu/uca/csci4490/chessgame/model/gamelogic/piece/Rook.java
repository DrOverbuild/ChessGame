package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class Rook extends Piece {
	public Rook() { }

	public Rook(Color color) {
		super();
		this.setColor(color);
		this.setImage("rook");
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
