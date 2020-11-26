package edu.uca.csci4490.chessgame.server.communication;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;
import edu.uca.csci4490.chessgame.server.ChessServer;
import ocsf.server.ConnectionToClient;

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
		StartOfGameData data = new StartOfGameData(game);

		comms.sendToPlayer(black, data);
		comms.sendToPlayer(white, data);
	}

	public void sendAvailableMoves(Game game, ArrayList<Location> moves) {
		AvailableMovesData data = new AvailableMovesData(game.getId(),moves);
		comms.sendToPlayer(game.getWhoseTurn(), data);
	}

	public void sendNextTurn(Game game) {
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
		EndOfGameData data = new EndOfGameData(winner, loser, stalemate, players);

		comms.sendToPlayer(winner, data);
		comms.sendToPlayer(loser, data);
	}

	public void receivePieceSelection(PieceSelectionData data, ConnectionToClient client) {
		int id = data.getGameID();
		Piece piece = data.getPiece();

		Game game = server.getGameByID(id);

		if (game == null) {
			System.out.println("Warning - received PieceSelectionData for game " + id + " but could not find any games by that ID.");
			return;
		}

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

		game.movePiece(data.getPiece(), data.getMoveTo(), data.getPromoteTo());

		if (game.isCheckmate() || game.isStalemate()) {
			server.endGame(game);
			sendEndOfGameData(game);
		} else {
			sendNextTurn(game);
		}
	}
	
	public void receiveGameAbandoned(AbandonGameData data) {
		// get game by id
		int id = data.getGameID();
		
		// call game.playerAbandonedGame
		Game game = server.getGameByID(id);
		game.playerAbandonedGame(data.getPlayer());
		
		// send EndOfGameData to both players
		sendEndOfGameData(game);
	}
}
