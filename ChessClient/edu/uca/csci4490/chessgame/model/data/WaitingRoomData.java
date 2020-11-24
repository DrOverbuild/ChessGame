package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class WaitingRoomData implements Serializable {
	ArrayList<Player> players;

	public WaitingRoomData(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
