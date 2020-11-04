package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;

import java.util.ArrayList;

public class Pawn extends Piece {
	public Pawn() { }

	public Pawn(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}
}
