package edu.uca.csci4490.chessgame.server.communication;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.GameData;
import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;
import edu.uca.csci4490.chessgame.server.gamelogic.Game;
import edu.uca.csci4490.chessgame.server.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.Piece;
import edu.uca.csci4490.chessgame.server.ChessServer;
import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class GameCommunication {
	private ChessServer server;
	private ChessServerCommunication comms;

	public GameCommunication(ChessServer server, ChessServerCommunication comms) {
		this.server = server;
		this.comms = comms;
	}

	public void sendStartOfGame(Game game) {
		Player black = game.getBlack();
		Player white = game.getWhite();
		StartOfGameData data = new StartOfGameData(game.data());

		Thread th = new Thread(() -> {
			try {
				black.getClient().sendToClient(data);
				white.getClient().sendToClient(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		th.start();
	}

	public void sendAvailableMoves(Game game, ArrayList<Location> moves) {
		ArrayList<LocationData> locations = new ArrayList<>();
		for (Location l: moves) {
			locations.add(l.data());
		}

		AvailableMovesData data = new AvailableMovesData(game.getId(),locations);
		comms.sendToPlayer(game.getWhoseTurn(), data);
	}

	public void sendNextTurn(GameData game) {
		Player black = game.getBlack();
		Player white = game.getWhite();
		NextTurnData data = new NextTurnData(game);

		comms.sendToPlayer(black, data);
		comms.sendToPlayer(white, data);
	}

	public void sendEndOfGameData(Game game) {
		Player winner = game.getWinner();
		Player loser = game.getLoser();
		boolean stalemate = false;

		if (winner == null || loser == null) {
			winner = game.getWhite();
			loser = game.getBlack();
			stalemate = true;
		}
		Set<Player> set = server.getPlayerManager().getWaitingRoom();
		ArrayList<Player> players = new ArrayList<>(set);
		EndOfGameData data = new EndOfGameData(winner, loser, stalemate, game.data(), players);

		comms.sendToPlayer(winner, data);
		comms.sendToPlayer(loser, data);
	}

	public void receivePieceSelection(PieceSelectionData data, ConnectionToClient client) {
		int id = data.getGameID();
		Game game = server.getGameByID(id);

		if (game == null) {
			System.out.println("Warning - received PieceSelectionData for game " + id + " but could not find any games by that ID.");
			return;
		}

		LocationData location = data.getPiece().getLocation();
		Piece piece = game.getBoard().getPieceAt(location.getX(), location.getY());


		if (client.getId() != game.getWhoseTurn().getClient().getId()) {
			return;
		}

		ArrayList<Location> moves = game.pieceSelected(piece);
		sendAvailableMoves(game, moves);
	}

	public void receivePieceMove(PieceMoveData data, ConnectionToClient client) {
		int id = data.getGameID();
		Game game = server.getGameByID(id);

		if (game == null) {
			System.out.println("Warning - received PieceMoveData for game " + id + " but could not find any games by that ID.");
			return;
		}

		if (client.getId() != game.getWhoseTurn().getClient().getId()) {
			return;
		}

		LocationData location = data.getPiece().getLocation();
		Piece piece = game.getBoard().getPieceAt(location.getX(), location.getY());
		Class<? extends Piece> promoteTo = null;
		if (data.getPromoteTo() != null) {
			String firstChar = data.getPromoteTo().substring(0,1);
			String className = data.getPromoteTo().replaceFirst(firstChar, firstChar.toUpperCase());
			try {
				promoteTo = (Class<? extends Piece>) Class.forName("edu.uca.csci4490.chessgame.server.gamelogic.piece." + className);
			} catch (Exception e) {
				System.out.println("WARNING - Failed to get promote to class");
				e.printStackTrace();
			}
		}

		LocationData toData = data.getMoveTo();
		Location to = new Location(toData.getX(), toData.getY());

		game.movePiece(piece, to, promoteTo);

		if (game.isCheckmate() || game.isStalemate()) {
			server.endGame(game);
			sendEndOfGameData(game);
		} else {
			sendNextTurn(game.data());
		}
	}
	
	public void receiveGameAbandoned(AbandonGameData data) {
		// get game by id
		int id = data.getGameID();
		
		// call game.playerAbandonedGame
		Game game = server.getGameByID(id);
		game.playerAbandonedGame(data.getPlayer());
		server.endGame(game);
		
		// send EndOfGameData to both players
		sendEndOfGameData(game);
	}
}
