package edu.uca.csci4490.chessgame.model;

import ocsf.server.ConnectionToClient;

import java.io.Serializable;

public class Player implements Serializable {
	private int id;
	private String username;
	private int xp;
	private int wins;
	private int losses;

	public Player(int id, String username, int xp, int wins, int losses) {
		this.id = id;
		this.username = username;
		this.xp = xp;
		this.wins = wins;
		this.losses = losses;
	}

	public Player() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	@Override
	public String toString() {
		return "Player{" +
				"id=" + id +
				", username='" + username + '\'' +
				", xp=" + xp +
				", wins=" + wins +
				", losses=" + losses +
				'}';
	}
}
