package edu.uca.csci4490.chessgame.server;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.server.communication.ChessServerCommunication;
import edu.uca.csci4490.chessgame.server.playermanager.PlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessServer {
	private static int gameId = 0;

	List<Game> runningGames = new ArrayList<>();

	ChessServerCommunication comms;
	PlayerManager playerManager;

	public Game getGameByID(int id) {
		for (Game game : runningGames) {
			if (game.getId() == id) {
				return game;
			}
		}

		return null;
	}

	public ChessServer() {
	}

	public void start(int port) throws IOException {
		playerManager = new PlayerManager(this);
		comms = new ChessServerCommunication(port, this);
		comms.listen();
	}

	public ChessServerCommunication getComms() {
		return comms;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public static void main(String[] args) {
		int port = 8300;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		try {
			new ChessServer().start(port);
		} catch (IOException e) {
			System.out.println("FATAL - failed to set up server");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void startGame(Player from, Player to) {
		Game newGame = new Game(gameId++, from, to);
		newGame.getBoard().setupBoard();
		runningGames.add(newGame);
		comms.getGameCommunication().sendStartOfGame(newGame);
	}

	public void endGame(Game game) {
		runningGames.remove(game);
		playerManager.movePlayerToWaitingRoom(game.getBlack());
		playerManager.movePlayerToWaitingRoom(game.getWhite());
		getComms().getWaitingRoomCommunication().sendWaitingRoomToAll();

		playerManager.updateStats(game);
	}

	public Game gameOfPlayer(Player p) {
		for (Game g : runningGames) {
			if (g.getWhite().equals(p) || g.getBlack().equals(p)) {
				return g;
			}
		}

		return null;
	}
}
