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
	private Piece[][] board;
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
		this.board = new Piece[8][8];
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

	public Piece[][] getBoard() {
		return board;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
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

	public Piece getPieceAt(Location loc) {
		if (loc == null) return null;
		return this.board[loc.getX()][loc.getY()];
	}

	/**
	 * Checks if piece is blocked by another piece or by an ally on the destination piece
	 * @param piece
	 * @param destination
	 * @return
	 */
	public boolean pieceIsBlocked(Piece piece, Location destination) {
		if (piece == null || destination == null) return true;

		// check if ally piece is at destination
		if (getPieceAt(destination) != null &&
				getPieceAt(destination).getColor() == piece.getColor()) {
			return true;
		}

		Location original = piece.getLocation();
		Direction dir = original.getDirectionOf(destination);

		if (dir == null) return true;

		// return false if L shaped
		if (dir.isLShaped()) {
			return false; // note that we already checked for ally piece
		}

		// check line between locations for piece
		int distance = 1;
		Location checkedLoc = original.getRelative(dir, distance);
		while (checkedLoc != null && !checkedLoc.equals(destination)) {
			Piece checkedPiece = getPieceAt(checkedLoc);

			if (checkedPiece != null) {
				return checkedPiece.getColor() == piece.getColor();
			}

			distance++;
		}

		return false;
	}

	public void setPieceOnBoard(byte x, byte y, Piece piece) {
		this.board[x][y] = piece;

		if (piece != null) {
			piece.setLocation(new Location(x, y));
		}
	}

	public void setPieceOnBoard(Location loc, Piece piece) {
		setPieceOnBoard(loc.getX(), loc.getY(), piece);
	}

	public void setupBoard() {
		// white ranked pieces
		setPieceOnBoard((byte)0,(byte)0, new Rook(Color.WHITE));
		setPieceOnBoard((byte)1,(byte)0, new Knight(Color.WHITE));
		setPieceOnBoard((byte)2,(byte)0, new Bishop(Color.WHITE));
		setPieceOnBoard((byte)5,(byte)0, new King(Color.WHITE));
		setPieceOnBoard((byte)5,(byte)0, new Queen(Color.WHITE));
		setPieceOnBoard((byte)5,(byte)0, new Bishop(Color.WHITE));
		setPieceOnBoard((byte)6,(byte)0, new Knight(Color.WHITE));
		setPieceOnBoard((byte)7,(byte)0, new Rook(Color.WHITE));

		// white pawns
		for (byte i = 0; i < 8; i++) {
			setPieceOnBoard(i, (byte)1, new Pawn(Color.WHITE));
		}

		// black ranked pieces
		setPieceOnBoard((byte)0,(byte)7, new Rook(Color.BLACK));
		setPieceOnBoard((byte)1,(byte)7, new Knight(Color.BLACK));
		setPieceOnBoard((byte)2,(byte)7, new Bishop(Color.BLACK));
		setPieceOnBoard((byte)5,(byte)7, new King(Color.BLACK));
		setPieceOnBoard((byte)5,(byte)7, new Queen(Color.BLACK));
		setPieceOnBoard((byte)5,(byte)7, new Bishop(Color.BLACK));
		setPieceOnBoard((byte)6,(byte)7, new Knight(Color.BLACK));
		setPieceOnBoard((byte)7,(byte)7, new Rook(Color.BLACK));

		// white pawns
		for (byte i = 0; i < 8; i++) {
			setPieceOnBoard(i, (byte)6, new Pawn(Color.BLACK));
		}
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
			setPieceOnBoard(piece.getLocation(), promoted);
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
		return piece.filterAvailableLocations(getBoard());
	}

	public void movePiece(Piece piece, Location to, Class<? extends Piece> promoteTo) {
		if (piece.filterAvailableLocations(getBoard()).contains(to)) {
			Piece destinationPiece = getPieceAt(to);

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

			setPieceOnBoard(piece.getLocation(), null);
			setPieceOnBoard(to, piece);

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
