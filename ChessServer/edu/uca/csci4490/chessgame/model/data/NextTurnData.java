package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.Game;

import java.io.Serializable;

public class NextTurnData implements Serializable {
	private Game game;

	public NextTurnData(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "NextTurnData{" +
				"game=" + game.getId() +
				'}';
	}
}
