package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.GameData;

import java.io.Serializable;
import java.util.ArrayList;

public class EndOfGameData implements Serializable {
	private Player winner;
	private Player loser;
	private boolean stalemate;
	private GameData game;
	ArrayList<Player> waitingRoom;
	
	public EndOfGameData(Player winner, Player loser, boolean stalemate, GameData game, ArrayList<Player> waitingRoom) {
		this.winner = winner;
		this.loser = loser;
		this.stalemate = stalemate;
		this.game = game;
		this.waitingRoom = waitingRoom;
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

	public GameData getGame() {
		return game;
	}

	public void setGame(GameData game) {
		this.game = game;
	}

	public ArrayList<Player> getWaitingRoom(){
		return waitingRoom;
	}
	
	public void setWaitingRoom(ArrayList<Player> waitingRoom) {
		this.waitingRoom = waitingRoom;
	}

	@Override
	public String toString() {
		return "EndOfGameData{" +
				"winner=" + winner.getUsername() +
				", loser=" + loser.getUsername() +
				", stalemate=" + stalemate +
				", waitingRoom=size(" + waitingRoom.size() +
				")";
	}
}
