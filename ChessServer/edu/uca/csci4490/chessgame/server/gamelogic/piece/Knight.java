package edu.uca.csci4490.chessgame.server.gamelogic.piece;

import edu.uca.csci4490.chessgame.server.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.server.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

import java.util.ArrayList;

public class Knight extends Piece {
	public Knight() {}

	public Knight(Color color) {
		super();
		this.setColor(color);
		this.setImage("knight");
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		ArrayList<Location> locations = new ArrayList<>();
		Direction[] directions = new Direction[]
				{Direction.L_N1_E2, Direction.L_N1_W2, Direction.L_N2_E1, Direction.L_N2_W1,
				 Direction.L_S1_E2, Direction.L_S1_W2, Direction.L_S2_E1, Direction.L_S2_W1};

		for (Direction d : directions) {
			Location relative = getLocation().getRelative(d, 0);
			if (relative != null) {
				locations.add(relative);
			}
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
		Piece knight = new Knight();
		knight.setColor(this.getColor());
		knight.setLocation(this.getLocation().copy());
		knight.setImage(this.getImage());
		return knight;
	}

	@Override
	public int getWorth() {
		return 7;
	}
}
