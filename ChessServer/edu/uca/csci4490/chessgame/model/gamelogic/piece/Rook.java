package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

import java.util.ArrayList;

public class Rook extends Piece {
	public Rook() { }

	public Rook(Color color) {
		super();
		this.setColor(color);
		this.setImage("rook");
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		ArrayList<Location> locations = new ArrayList<>();
		Direction[] directions = new Direction[]
				{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

		for (Direction d : directions) {
			locations.addAll(getLocation().allLocationsInDirection(d));
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
		Piece rook = new Rook();
		rook.setColor(this.getColor());
		rook.setLocation(this.getLocation());
		rook.setImage(this.getImage());
		return rook;
	}

	@Override
	public int getWorth() {
		return 6;
	}
}
