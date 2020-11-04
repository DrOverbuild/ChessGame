package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;

public class Knight extends Piece {
	public Knight() {}

	public Knight(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}
}
