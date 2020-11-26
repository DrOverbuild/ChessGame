package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Board;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Piece implements Serializable {
	private Color color = null;
	private Location location = null;
	private String image = null;

	public Piece() {
	}

	public Piece(Color color, Location location, String image) {
		this.color = color;
		this.location = location;
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public abstract Piece copy();

	public abstract ArrayList<Location> allMovableLocations(Board board);

	public abstract ArrayList<Location> filterAvailableLocations(Board board);

	public abstract int getWorth();

	@Override
	public String toString() {
		return "Piece." + getImage();
	}
}
