package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class AvailableMovesData implements Serializable {
	private int gameID;
	private ArrayList<Location> moves;

	public AvailableMovesData(int gameID, ArrayList<Location> moves) {
		this.gameID = gameID;
		this.moves = moves;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public ArrayList<Location> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<Location> moves) {
		this.moves = moves;
	}

	@Override
	public String toString() {
		return "AvailableMovesData{" +
				"gameID=" + gameID +
				", moves: size(" + moves.size() +
				")}";
	}
}
