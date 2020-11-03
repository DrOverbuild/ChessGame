package edu.uca.csci4490.chessgame.server;

import edu.uca.csci4490.chessgame.model.gamelogic.Game;

import java.util.ArrayList;
import java.util.List;

public class ChessServer {
	List<Game> runningGames = new ArrayList<>();

	public Game getGameByID(int id) {
		for (Game game : runningGames) {
			if (game.getId() == id) {
				return game;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
