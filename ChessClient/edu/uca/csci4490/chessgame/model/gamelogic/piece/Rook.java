package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;

import java.util.ArrayList;

public class Rook extends Piece {
	public Rook() { }

	public Rook(Color color) {
		super();
		this.setColor(color);
		this.setImage("rook");
	}
}
