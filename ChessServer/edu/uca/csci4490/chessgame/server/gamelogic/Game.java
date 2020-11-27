package edu.uca.csci4490.chessgame.server.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.GameData;
import edu.uca.csci4490.chessgame.model.gamelogic.MoveData;
import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.*;
import edu.uca.csci4490.chessgame.server.communication.GameCommunication;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Game {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Game)) return false;
		Game game = (Game) o;
		return id == game.getId();
	}

	public GameData data() {
		GameData data = new GameData();
		data.setId(id);
		data.setBoard(board.data());
		data.setWhite(white);
		data.setBlack(black);
		data.setTurn(turn);
		data.setInCheck(inCheck);
		data.setCheckmate(checkmate);
		data.setStalemate(stalemate);

		ArrayList<MoveData> moveData = new ArrayList<>();
		for (Move move : moves) {
			moveData.add(move.data());
		}
		data.setMoves(moveData);

		ArrayList<PieceData> pieceData = new ArrayList<>();
		for (Piece piece: capturedPieces) {
			pieceData.add(piece.data());
		}
		data.setCapturedPieces(pieceData);

		return data;
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

	public void playerAbandonedGame(Player p) {
		// I don't feel good about this hacky method to force a winner when the other player
		// abandons the game but this is how this class was designed and I'm not about to
		// refactor this thing.
		if (getWhite().equals(p)) {
			checkmate = true;
			turn = Color.BLACK;
		} else if (getBlack().equals(p)) {
			checkmate = true;
			turn = Color.WHITE;
		}
	}

	public ArrayList<Location> filterMovesThatPutKingInCheck(ArrayList<Location> availableLocations, Piece piece) {
		ArrayList<Location> updatedList = new ArrayList<>(availableLocations);
		for (Location location: availableLocations) {
			Board hypothetical = board.getHypothetical(piece, location);
			if (hypothetical.pieceIsEndangered(hypothetical.getKing(turn))) {
				updatedList.remove(location);
			}
		}

		return updatedList;
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
		if (stalemate || checkmate) {
			return new ArrayList<>();
		}

		ArrayList<Location> availableLocations = piece.filterAvailableLocations(getBoard());

		// find locations from array that would put the king in check
		// we do this outside of the filterAvailableLocations because doing so would cause
		// unlimited recursion... and we only want to look at the next possible game state

		return filterMovesThatPutKingInCheck(availableLocations, piece);
	}

	public void movePiece(Piece piece, Location to, Class<? extends Piece> promoteTo) {
		if (piece.filterAvailableLocations(getBoard()).contains(to) || stalemate || checkmate) {
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

			Location original = piece.getLocation();

			board.movePiece(piece, to);

			moves.add(new Move(piece, original, to));

			// switch turn
			turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE;

			checkForCheck();
			checkForCheckmateAndStalemate();
		} else {
			System.out.println("Warning - tried to make a move that was not allowed");
		}
	}

	public void checkForCheck() {
		inCheck = board.pieceIsEndangered(board.getKing(turn));
	}

	public void checkForCheckmateAndStalemate() {
		boolean movesLeft = false;
		for (Piece p:board.allPieces(turn)) {
			ArrayList<Location> moves = p.filterAvailableLocations(board);
			moves = filterMovesThatPutKingInCheck(moves, p);

			if (moves.size() != 0) {
				movesLeft = true;
				break;
			}
		}

		if (!movesLeft) {
			if (inCheck) {
				checkmate = true;
				return;
			} else {
				stalemate = true;
			}
		}

		if (!stalemate) {
			// todo check for three identical moves in a row
		}
	}
}
