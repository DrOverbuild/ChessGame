package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

public class PlayerChallengeData {

	private Player to;
	private Player from;
	
	public PlayerChallengeData(Player to, Player from) {
		this.to = to;
		this.from = from;
	}
	
	public void setTo(Player to) {
		this.to = to;
	}
	
	public Player getTo() {
		return to;
	}
	
	public Player getFrom() {
		return from;
	}
	
	public void setFrom(Player from) {
		this.from = from;
	}

	
}
