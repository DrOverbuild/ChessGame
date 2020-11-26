package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;

public class AbandonGameData implements Serializable {
	private int gameID;
	private Player player;
	
	public AbandonGameData() {
		setGameID(0);
		player = new Player();
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
