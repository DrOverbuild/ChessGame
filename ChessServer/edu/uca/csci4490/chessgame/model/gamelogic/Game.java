package edu.uca.csci4490.chessgame.model.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.*;
import edu.uca.csci4490.chessgame.server.communication.GameCommunication;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Game implements Serializable {
	transient private GameCommunication communication;

	private int id;

	/**
	 * Represents the board. Null if no piece is at that location. Coordinate system runs from top
	 * down and from left to right with white on top. So (0,0) would be a white rook and (7,7)
	 * would be a black rook. x = 0 are the white ranked pieces, x = 1 are the white pawns, x = 6
	 * are the black pawns, and x = 7 are the black ranked pieces.
	 */

	private Board board;
	private Player white;
	private Player black;
	private Color turn;
	private boolean inCheck = false;
	private boolean checkmate = false;
	private boolean stalemate = false;
	private ArrayList<Move> moves;
	private ArrayList<Piece> capturedPieces;

	public Game(int id, Player white, Player black) {
		this.id = id;
		this.white = white;
		this.black = black;
		this.board = new Board();
		this.turn = Color.WHITE;
		this.moves = new ArrayList<>();
		this.capturedPieces = new ArrayList<>();
	}

	public GameCommunication getCommunication() {
		return communication;
	}

	public void setCommunication(GameCommunication communication) {
		this.communication = communication;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public Player getWhite() {
		return white;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public Player getBlack() {
		return black;
	}

	public void setBlack(Player black) {
		this.black = black;
	}

	public Color getTurn() {
		return turn;
	}

	public void setTurn(Color turn) {
		this.turn = turn;
	}

	public boolean isInCheck() {
		return inCheck;
	}

	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}

	public boolean isStalemate() {
		return stalemate;
	}

	public void setStalemate(boolean stalemate) {
		this.stalemate = stalemate;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	public ArrayList<Piece> getCapturedPieces() {
		return capturedPieces;
	}

	public void setCapturedPieces(ArrayList<Piece> capturedPieces) {
		this.capturedPieces = capturedPieces;
	}

	public Player getWhoseTurn() {
		return turn == Color.WHITE ? white : black;
	}

	public Player getWinner() {
		if (isStalemate()) {
			return null;
		}

		if (isCheckmate()) {
			return getWhoseTurn();
		}

		return null;
	}

	public Player getLoser() {
		if (isStalemate()) {
			return null;
		}

		if (isCheckmate()) {
			return turn == Color.WHITE ? black : white;
		}

		return null;
	}

	public void capturePiece(Piece piece) {
		piece.setLocation(null);
		capturedPieces.add(piece);
	}

	public void promotePiece(Piece piece, Class<? extends Piece> to) {
		try {
			Piece promoted = to.getConstructor().newInstance();
			promoted.setColor(piece.getColor());
			promoted.setImage(piece.getImage());
			board.setPieceOnBoard(piece.getLocation(), promoted);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			System.out.println("Fatal - tried to promote piece, failed to create new instance of " + to.getName());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Called by the GameServerCommunication to send back the player's available moves for that piece
	 * @param piece
	 * @return
	 */
	public ArrayList<Location> pieceSelected(Piece piece) {
		ArrayList<Location> availableLocations = piece.filterAvailableLocations(getBoard());

		// find locations from array that would put the king in check
		// we do this outside of the filterAvailableLocations because doing so would cause
		// unlimited recursion... and we only want to look at the next possible game state
		ArrayList<Location> toRemove = new ArrayList<>();
		for (Location location: availableLocations) {
			Board hypothetical = board.getHypothetical(piece, location);
			if (hypothetical.pieceIsEndangered(hypothetical.getKing(turn))) {
				toRemove.add(location);
			}
		}
		availableLocations.removeAll(toRemove);

		return availableLocations;
	}

	public void movePiece(Piece piece, Location to, Class<? extends Piece> promoteTo) {
		if (piece.filterAvailableLocations(getBoard()).contains(to)) {
			Piece destinationPiece = board.getPieceAt(to);

			if (destinationPiece != null) {
				capturePiece(destinationPiece);
			}

			if (piece instanceof Pawn) {
				Pawn pawn = (Pawn)piece;
				if (pawn.hasReachedTheOtherEnd() && to.getY() == ((Pawn) piece).getOtherEnd()) {
					pawn.setHasReachedTheOtherEnd(true);

					if (promoteTo != null) {
						promotePiece(pawn, promoteTo);
					}
				}
			}

			board.movePiece(piece, to);

			checkForCheck();
			checkForCheckmate();
			checkForStalemate();

			// switch turn
			turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE;
		} else {
			System.out.println("Warning - tried to make a move that was not allowed");
		}
	}

	public void checkForCheck() {
		// todo implement
	}

	public void checkForCheckmate() {
		// todo implement
	}

	public void checkForStalemate() {
		// todo implement
	}
}
