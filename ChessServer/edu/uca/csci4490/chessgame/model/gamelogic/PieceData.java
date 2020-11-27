package edu.uca.csci4490.chessgame.model.gamelogic;

import java.io.Serializable;

public class PieceData implements Serializable {
	private Color color = null;
	private LocationData location = null;
	private String image = null;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public LocationData getLocation() {
		return location;
	}

	public void setLocation(LocationData location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
