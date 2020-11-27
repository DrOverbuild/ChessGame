package edu.uca.csci4490.chessgame.server.gamelogic;

import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {
	private byte x;
	private byte y;

	public Location(byte x, byte y) {
		this.x = x;
		this.y = y;
	}

	public LocationData data() {
		return new LocationData(x, y);
	}

	public byte getX() {
		return x;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public byte getY() {
		return y;
	}

	public void setY(byte y) {
		this.y = y;
	}

	public Location copy() {
		return new Location(this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return x == location.x &&
				y == location.y;
	}

	/**
	 * Returns the location in the given direction at the specified distance. If the direction is an L shape,
	 * the distance is ignored. In the diagonal directions, distance refers to the number of diagonal squares.
	 * The Pythagorean Theorem is not at play here.
	 * @param direction
	 * @param distance
	 * @return Null if distance exceeds the bounds of the board in the specified direction
	 */
	public Location getRelative(Direction direction, int distance) {
		int newY = getY();
		int newX = getX();

		// make new x/new y calculation
		switch (direction) {
			case NORTH:
				newY = getY() - distance;
				break;
			case SOUTH:
				newY = getY() + distance;
				break;
			case EAST:
				newX = getX() + distance;
				break;
			case WEST:
				newX = getX() - distance;
				break;
			case NORTHEAST:
				newX = getX() + distance;
				newY = getY() - distance;
				break;
			case SOUTHEAST:
				newX = getX() + distance;
				newY = getY() + distance;
				break;
			case NORTHWEST:
				newX = getX() - distance;
				newY = getY() - distance;
				break;
			case SOUTHWEST:
				newX = getX() - distance;
				newY = getY() + distance;
				break;
			case L_N2_E3:
				newX = getX() + 3;
				newY = getY() - 2;
				break;
			case L_N3_E2:
				newX = getX() + 2;
				newY = getY() - 3;
				break;
			case L_S2_E3:
				newX = getX() + 3;
				newY = getY() + 2;
				break;
			case L_S3_E2:
				newX = getX() + 2;
				newY = getY() + 3;
				break;
			case L_N2_W3:
				newX = getX() - 3;
				newY = getY() - 2;
				break;
			case L_N3_W2:
				newX = getX() - 2;
				newY = getY() - 3;
				break;
			case L_S2_W3:
				newX = getX() - 3;
				newY = getY() + 2;
				break;
			case L_S3_W2:
				newX = getX() - 2;
				newY = getY() + 3;
				break;
		}

		// check if new coords exceed board bounds
		if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
			return null;
		}

		return new Location((byte)newX, (byte)newY);
	}

	public ArrayList<Location> allLocationsInDirection(Direction direction) {
		int distance = 1;
		ArrayList<Location> locations = new ArrayList<>();
		Location loc;

		while  ((loc = this.getRelative(direction, distance)) != null) {
			locations.add(loc);
			distance++;
		}

		return locations;
	}

	public Direction getDirectionOf(Location destination) {
		if (destination == null) return null;

		int deltaX = destination.getX() - getX();
		int deltaY = destination.getY() - getY();

		if (deltaX == 2 && deltaY == 3) {
			return Direction.L_S3_E2;
		} else if (deltaX == 3 && deltaY == 2) {
			return Direction.L_S2_E3;
		} else if (deltaX == -2 && deltaY == 3)  {
			return Direction.L_S3_W2;
		} else if (deltaX == -3 && deltaY == 2)  {
			return Direction.L_S2_W3;
		} else if (deltaX == 2 && deltaY == -3) {
			return Direction.L_N3_E2;
		} else if (deltaX == 3 && deltaY == -2) {
			return Direction.L_N2_E3;
		} else if (deltaX == -2 && deltaY == -3)  {
			return Direction.L_N3_W2;
		} else if (deltaX == -3 && deltaY == -2)  {
			return Direction.L_N2_W3;
		} else if (deltaX == 0 && deltaY > 0) {
			return Direction.SOUTH;
		} else if (deltaX == 0 && deltaY < 0) {
			return Direction.NORTH;
		} else if (deltaX < 0 && deltaY == 0) {
			return Direction.WEST;
		} else if (deltaX > 0 && deltaY == 0) {
			return Direction.EAST;
		} else if (Math.abs(deltaX) == Math.abs(deltaY)) {
			if (deltaX > 0 && deltaY > 0) {
				return Direction.SOUTHEAST;
			} else if (deltaX < 0 && deltaY > 0) {
				return Direction.SOUTHWEST;
			} else if (deltaX > 0 && deltaY < 0) {
				return Direction.NORTHEAST;
			} else if (deltaX < 0 && deltaY < 0) {
				return Direction.NORTHWEST;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
