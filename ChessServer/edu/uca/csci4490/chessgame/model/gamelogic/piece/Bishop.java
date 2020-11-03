package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.util.ArrayList;

public class Bishop extends Piece {
	public Bishop() { }

	public Bishop(Color color) {
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
		Piece bishop = new Bishop();
		bishop.setColor(this.getColor());
		bishop.setLocation(this.getLocation());
		bishop.setImage(this.getImage());
		return bishop;
	}
}
