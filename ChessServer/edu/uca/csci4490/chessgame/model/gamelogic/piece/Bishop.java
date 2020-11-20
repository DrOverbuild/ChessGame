package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

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
		ArrayList<Location> locations = new ArrayList<>();
		Direction[] directions = new Direction[]
				{Direction.NORTHEAST, Direction.SOUTHEAST, Direction.NORTHWEST, Direction.SOUTHWEST};

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
	public int getWorth() {
		return 8;
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
