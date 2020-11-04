package edu.uca.csci4490.chessgame.model.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.*;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Game implements Serializable {
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
}
