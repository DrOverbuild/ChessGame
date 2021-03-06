package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;

public class PlayerChallengeData implements Serializable {

	private Player to;
	private Player from;
	
	public PlayerChallengeData(Player from, Player to) {
		this.from = from;
		this.to = to;
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

	@Override
	public String toString() {
		return "PlayerChallengeData{" +
				"to=" + to.getUsername() +
				", from=" + from.getUsername() +
				'}';
	}
}
