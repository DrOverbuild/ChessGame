package edu.uca.csci4490.chessgame.client.GameScreen;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameScreenController implements ActionListener {

	// Private data fields for the container and chat client.
	private ChessClientCommunication comms;
	private ChessClient client;
	private GameScreenPanel view;

	private Game game;

	/**
	 * List of available moves, received from the server after selecting a piece.
	 */
	private ArrayList<Location> availableMoves = null;
	private Piece selectedPiece = null;
	private Player thisPlayer;
	private Color thisColor;

	public GameScreenController(ChessClient client, ChessClientCommunication comms) {
		this.client = client;
		this.comms = comms;

		view = new GameScreenPanel();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command.equals("Abandon Game")) {

		} else if (command.equals("View Moves")) {

		} else if (command.equals("View Captured Pieces")) {

		} else if (command.contains(",")) {
			// 0,1
			String[] components = command.split(",");
			int x = Integer.parseInt(components[0]);
			int y = Integer.parseInt(components[1]);

			if (selectedPiece == null) {
				sendPieceSelection(x, y);
			} else {
				sendPieceMove(x, y);
			}
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setThisPlayer(Player thisPlayer) {
		this.thisPlayer = thisPlayer;
	}

	public Player getThisPlayer() {
		return thisPlayer;
	}

	public void initGame(Game game, Player thisPlayer, Color thisColor) {
		this.game = game;
		this.thisColor = thisColor;
		this.thisPlayer = thisPlayer;

		view.updateGame(game);

		if (game.getWhoseTurn().equals(thisPlayer)) {
			view.setStatus("Your turn");
		} else {
			view.setStatus("Waiting for " + game.getWhoseTurn().getUsername() + "...");
		}
	}

	public void receiveAvailableMoves(AvailableMovesData data) {
		if (selectedPiece == null) {
			System.out.println("WARNING - Received AvailableMovesData but we aren't waiting for it. Ignoring message.");
			return;
		}

		if (game.getId() != data.getGameID()) {
			System.out.println("WARNING - Received AvailableMovesData with a mismatched game ID. Ignoring message.");
			return;
		}

		availableMoves = data.getMoves();
		view.updateAvailableMoves(availableMoves);
	}

	public void receiveNextTurn(NextTurnData data) {
		if (game.getId() != data.getGame().getId()) {
			System.out.println("WARNING - Received NextTurnData with a mismatched game ID. Ignoring message.");
			return;
		}

		game = data.getGame();

		view.updateGame(data.getGame());

		if (game.getWhoseTurn().equals(thisPlayer)) {
			view.setStatus("Your turn");
		} else {
			view.setStatus("Waiting for " + game.getWhoseTurn().getUsername() + "...");
		}
	}

	public void receiveEndOfGame(EndOfGameData data) {
		if (!data.getLoser().equals(thisPlayer) && !data.getWinner().equals(thisPlayer)) {
			System.out.println("WARNING - Received EndOfGameData but players don't match up. Ignoring message.");
			return;
		}

		if (data.isStalemate()) {
			view.setStatus("Stalemate.");
		} else if (data.getWinner().equals(thisPlayer)) {
			view.setStatus("You win!");
		} else if (data.getLoser().equals(thisPlayer)) {
			view.setStatus(data.getWinner().getUsername() + " wins!");
		}

		view.disableButtons();
	}

	public void sendPieceSelection(int x, int y) {
		Piece piece = game.getBoard().getPieceAt(x, y);

		if (piece == null || !piece.getColor().equals(thisColor)) {
			return;
		}

		PieceSelectionData data = new PieceSelectionData(game.getId(), piece);
		comms.send(data);
	}

	public void sendPieceMove(int x, int y) {
		if (selectedPiece == null) {
			return;
		}

		Location to = new Location((byte)x, (byte)y);
		PieceMoveData data = new PieceMoveData(game.getId(), selectedPiece, to);
		comms.send(data);
	}

}
