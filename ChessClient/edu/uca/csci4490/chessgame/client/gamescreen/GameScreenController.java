package edu.uca.csci4490.chessgame.client.gamescreen;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameScreenController implements ActionListener {

	// Private data fields for the container and chat client.
	private ChessClientCommunication comms;
	private GameScreenPanel view;
	private ChessClient client;

	private GameData game;

	/**
	 * List of available moves, received from the server after selecting a piece.
	 */
	private ArrayList<LocationData> availableMoves = null;
	private PieceData selectedPiece = null;
	private Player thisPlayer;
	private Color thisColor;

	private boolean abandonedGame = false;

	public GameScreenController(ChessClient client, ChessClientCommunication comms) {
		this.client = client;
		this.comms = comms;

		view = new GameScreenPanel(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command.equals("Abandon Game")) {
			sendAbandonGame();
		} else if (command.equals("View Moves")) {

		} else if (command.equals("View Captured Pieces")) {

		} else if (command.equals("Waiting Room")) {
			client.transitionToWaitingRoom(thisPlayer, client.getWc().getWaitingRoomPlayers());
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

	public void setGame(GameData game) {
		this.game = game;
	}

	public void setThisPlayer(Player thisPlayer) {
		this.thisPlayer = thisPlayer;
	}

	public Player getThisPlayer() {
		return thisPlayer;
	}

	public GameScreenPanel getView() {
		return view;
	}

	public void initGame(GameData game, Player thisPlayer) {
		this.game = game;
		this.thisPlayer = thisPlayer;

		this.thisColor = game.getBlack().equals(thisPlayer) ? Color.BLACK : Color.WHITE;

		view.updateGame(game);
		view.enableButtons();
		view.setToAbandonGame();

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
		view.updateGame(game);
		view.updateAvailableMoves(selectedPiece, availableMoves);
	}

	public void receiveNextTurn(NextTurnData data) {
		if (game.getId() != data.getGame().getId()) {
			System.out.println("WARNING - Received NextTurnData with a mismatched game ID. Ignoring message.");
			return;
		}

		game = data.getGame();

		view.updateGame(game);

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

		if (abandonedGame) {
			// do nothing
		} else if (data.isStalemate()) {
			view.setStatus("Stalemate.");
		} else if (data.getWinner().equals(thisPlayer)) {
			view.setStatus("You win!");
		} else if (data.getLoser().equals(thisPlayer)) {
			view.setStatus(data.getWinner().getUsername() + " wins!");
		}

		this.game = data.getGame();
		view.updateGame(data.getGame());
		view.disableButtons();
		view.setToLeaveGame();

		client.getWc().setPlayers(data.getWaitingRoom());
	}

	public void sendPieceSelection(int x, int y) {
		PieceData piece = game.getBoard().getPieceAt(x, y);

		if (piece == null || !piece.getColor().equals(thisColor)) {
			return;
		}

		selectedPiece = piece;

		System.out.println("Sending piece " + selectedPiece.getColor() + " " + selectedPiece.getImage() + " at " + selectedPiece.getLocation());
		PieceSelectionData data = new PieceSelectionData(game.getId(), piece);
		comms.send(data);
	}

	public void sendPieceMove(int x, int y) {
		if (selectedPiece == null || availableMoves == null) {
			return;
		}

		LocationData to = new LocationData((byte)x, (byte)y);

		if (!availableMoves.contains(to)) {
			availableMoves = new ArrayList<>();
			// remove outlines and do nothing
			view.updateGame(game);

			PieceData selectedMove = game.getBoard().getPieceAt(to);

			if (selectedMove != null && selectedMove.getColor().equals(thisColor)) {
				sendPieceSelection(x, y);
			}

			return;
		}

		PieceMoveData data = new PieceMoveData(game.getId(), selectedPiece, to);
		comms.send(data);

		selectedPiece = null;
		availableMoves = null;
	}

	public void sendAbandonGame() {
		view.setStatus("You abandoned the game");
		view.disableButtons();
		abandonedGame = true;
		// TODO send abandon game
		AbandonGameData data = new AbandonGameData();
		data.setGameID(game.getId());
		data.setPlayer(thisPlayer);
		comms.send(data);
	}
}
