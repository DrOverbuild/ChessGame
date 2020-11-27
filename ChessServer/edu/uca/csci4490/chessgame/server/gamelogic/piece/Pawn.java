package edu.uca.csci4490.chessgame.server.gamelogic.piece;

import edu.uca.csci4490.chessgame.server.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.server.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

import java.util.ArrayList;

public class Pawn extends Piece {
	private boolean hasReachedTheOtherEnd = false;

	public Pawn() { }

	public Pawn(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}

	public boolean hasReachedTheOtherEnd() {
		return hasReachedTheOtherEnd;
	}

	public void setHasReachedTheOtherEnd(boolean hasReachedTheOtherEnd) {
		this.hasReachedTheOtherEnd = hasReachedTheOtherEnd;
	}

	public byte getOtherEnd() {
		return getColor() == Color.WHITE ? (byte)0 : (byte)7;
	}

	@Override
	public ArrayList<Location> allMovableLocations(Board board) {
		ArrayList<Location> locations = new ArrayList<>();

		Direction diagonalW;
		Direction diagonalE;
		Direction straight;

		int startingPos;


		if (getColor() == Color.WHITE) {
			// can only go south
			diagonalW = Direction.SOUTHWEST;
			diagonalE = Direction.SOUTHEAST;
			straight = Direction.SOUTH;
			startingPos = 1;
		} else {
			// can only go north
			diagonalW = Direction.NORTHWEST;
			diagonalE = Direction.NORTHEAST;
			straight = Direction.NORTH;
			startingPos = 6;
		}

		Location w = getLocation().getRelative(diagonalW, 1);
		Location s = getLocation().getRelative(diagonalE, 1);
		Location e = getLocation().getRelative(straight, 1);
		Location s2 = getLocation().getRelative(straight, 2);

		if (w != null) {
			locations.add(w);
		}

		if (s != null) {
			locations.add(s);
		}

		if (e != null) {
			locations.add(e);
		}

		if (s2 != null && getLocation().getX() == startingPos) {
			// only add the jump 2 location if we're at the starting position
			locations.add(s2);
		}
		return locations;
	}

	@Override
	public ArrayList<Location> filterAvailableLocations(Board board) {
		System.out.println("Getting locations for " + this.getColor() + " " + this.getImage() + " at " + this.getLocation());
		ArrayList<Location> all = allMovableLocations(board);
		ArrayList<Location> filtered = new ArrayList<>();


		for (Location loc : all) {
			// add to filtered if diagonal and there are opponents to capture
			if (getLocation().getDirectionOf(loc).isDiagonal()) {
				Piece p = board.getPieceAt(loc);
				if (p != null && p.getColor() != this.getColor()) {
					filtered.add(loc);
				}
			} else { // otherwise add to filtered unless blocked
				if (!board.pieceIsBlocked(this, loc)) {
					filtered.add(loc);
				}
			}
		}

		return filtered;
	}

	@Override
	public Piece copy() {
		Piece pawn = new Pawn();
		pawn.setColor(this.getColor());
		pawn.setLocation(this.getLocation().copy());
		pawn.setImage(this.getImage());
		return pawn;
	}

	@Override
	public int getWorth() {
		return 1;
	}
}
