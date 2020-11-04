package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;

import java.util.ArrayList;

public class Queen extends Piece {
	public Queen() { }

	public Queen(Color color) {
		super();
		this.setColor(color);
		this.setImage("queen");
	}
}
