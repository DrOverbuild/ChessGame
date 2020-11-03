package edu.uca.csci4490.chessgame.server.communication;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;
import edu.uca.csci4490.chessgame.server.ChessServer;
import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

public class GameCommunication {
	private ChessServer server;

	public GameCommunication(ChessServer server) {
		this.server = server;
	}

	public void sendStartOfGame(Game game) {
		Player black = game.getBlack();
		Player white = game.getWhite();
		StartOfGameData data = new StartOfGameData(game);

		try {
			black.getClient().sendToClient(data);
			white.getClient().sendToClient(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendAvailableMoves(Game game, ArrayList<Location> moves) {
		AvailableMovesData data = new AvailableMovesData(game.getId(),moves);
		try {
			game.getWhoseTurn().getClient().sendToClient(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendNextTurn(Game game) {
		Player black = game.getBlack();
		Player white = game.getWhite();
		NextTurnData data = new NextTurnData(game);

		try {
			black.getClient().sendToClient(data);
			white.getClient().sendToClient(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		EndOfGameData data = new EndOfGameData(winner, loser, stalemate);

		try {
			winner.getClient().sendToClient(data);
			loser.getClient().sendToClient(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receivePieceSelection(PIeceSelectionData data, ConnectionToClient client) {
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
			sendEndOfGameData(game);
		} else {
			sendNextTurn(game);
		}
	}
}
