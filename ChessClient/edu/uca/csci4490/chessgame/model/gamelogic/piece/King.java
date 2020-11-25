package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;

public class King extends Piece {
	public King() { }

	public King(Color color) {
		super();
		this.setColor(color);
		this.setImage("king");
	}
}
