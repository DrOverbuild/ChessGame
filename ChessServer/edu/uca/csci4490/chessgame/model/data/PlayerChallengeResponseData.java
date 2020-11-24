package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

public class PlayerChallengeResponseData {

	private Player to;
	private Player from;
	
	public boolean accepted = false;
	
	public void PlayerAcceptChallengeData(Player to, Player from) {
		this.to = to;
		this.from = from;
		this.accepted = accepted;
	}
	
	public Player getFrom() {
		return from;
	}
	
	public void setFrom(Player from) {
		this.from = from;
	}
	
	public Player getTo() {
		return to;
	}
	
	public void setTo(Player to) {
		this.to = to;
	}
	
	public boolean getAccepted() {
		return accepted;
	}
	
	public void setAccepted(Boolean accepted) {
		this.accepted= accepted;
	}
}
