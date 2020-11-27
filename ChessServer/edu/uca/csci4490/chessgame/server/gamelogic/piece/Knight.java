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
		this.setImage("pawn");
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		ArrayList<Location> locations = new ArrayList<>();
		Direction[] directions = new Direction[]
				{Direction.L_N2_E3, Direction.L_N2_W3, Direction.L_N3_E2, Direction.L_N3_W2,
				 Direction.L_S2_E3, Direction.L_S2_W3, Direction.L_S3_E2, Direction.L_S3_W2};

		for (Direction d : directions) {
			locations.add(getLocation().getRelative(d, 0));
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
		knight.setLocation(this.getLocation());
		knight.setImage(this.getImage());
		return knight;
	}

	@Override
	public int getWorth() {
		return 7;
	}
}
