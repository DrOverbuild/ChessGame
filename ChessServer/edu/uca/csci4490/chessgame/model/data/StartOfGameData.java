package edu.uca.csci4490.chessgame.model.data;

import edu.uca.csci4490.chessgame.model.gamelogic.GameData;

import java.io.Serializable;

public class StartOfGameData implements Serializable {
	private GameData game;

	public StartOfGameData(GameData game) {
		this.game = game;
	}

	public GameData getGame() {
		return game;
	}

	public void setGame(GameData game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "StartOfGameData{" +
				"game=" + game +
				'}';
	}
}
