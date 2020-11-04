package edu.uca.csci4490.chessgame.model.gamelogic.piece;

import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import java.util.ArrayList;

public class Bishop extends Piece {
	public Bishop() { }

	public Bishop(Color color) {
		super();
		this.setColor(color);
		this.setImage("pawn");
	}
}
