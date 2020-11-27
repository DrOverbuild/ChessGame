package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;

import java.io.Serializable;
import java.util.ArrayList;

public class AvailableMovesData implements Serializable {
	private int gameID;
	private ArrayList<LocationData> moves;

	public AvailableMovesData(int gameID, ArrayList<LocationData> moves) {
		this.gameID = gameID;
		this.moves = moves;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public ArrayList<LocationData> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<LocationData> moves) {
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
