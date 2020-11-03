package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

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
		ArrayList<Location> locations = new ArrayList<>();
		Direction[] directions = new Direction[]
				{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST,
						Direction.NORTHEAST, Direction.SOUTHEAST, Direction.NORTHWEST, Direction.SOUTHWEST};

		for (Direction d : directions) {
			locations.add(getLocation().getRelative(d, 1));
		}

		return locations;
	}

	@Override
	public ArrayList<Location> filterAvailableLocations(Board board) {
		ArrayList<Location> allLocations = allMovableLocations(board);
		ArrayList<Location> filtered = new ArrayList<>();

		for (Location location : allLocations) {
			if (!board.pieceIsBlocked(this, location)) {
				filtered.add(location);
			}
		}

		return filtered;
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
