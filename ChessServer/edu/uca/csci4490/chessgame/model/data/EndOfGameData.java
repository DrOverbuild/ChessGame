package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;

public class EndOfGameData implements Serializable {
	private Player winner;
	private Player loser;
	private boolean stalemate;

	public EndOfGameData(Player winner, Player loser, boolean stalemate) {
		this.winner = winner;
		this.loser = loser;
		this.stalemate = stalemate;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(Player loser) {
		this.loser = loser;
	}

	public boolean isStalemate() {
		return stalemate;
	}

	public void setStalemate(boolean stalemate) {
		this.stalemate = stalemate;
	}
}
