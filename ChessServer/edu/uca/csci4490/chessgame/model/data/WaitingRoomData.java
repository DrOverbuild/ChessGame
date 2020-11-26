package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class WaitingRoomData implements Serializable {
	private ArrayList<Player> players;
	
	public WaitingRoomData(ArrayList<Player> players) {
		this.players = players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	@Override
	public String toString() {
		return "WaitingRoomData{" +
				"players=size(" + players.size() +
				")}";
	}
}
