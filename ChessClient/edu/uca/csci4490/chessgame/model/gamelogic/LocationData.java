package edu.uca.csci4490.chessgame.model.gamelogic;


import java.io.Serializable;

public class LocationData implements Serializable {
	private byte x;
	private byte y;

	public LocationData(byte x, byte y) {
		this.x = x;
		this.y = y;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LocationData location = (LocationData) o;
		return x == location.x &&
				y == location.y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
