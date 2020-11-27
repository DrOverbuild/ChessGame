package edu.uca.csci4490.chessgame.model.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
	private int id;
	private BoardData board;
	private Player white;
	private Player black;
	private Color turn;
	private boolean inCheck = false;
	private boolean checkmate = false;
	private boolean stalemate = false;
	private ArrayList<MoveData> moves;
	private ArrayList<PieceData> capturedPieces;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BoardData getBoard() {
		return board;
	}

	public void setBoard(BoardData board) {
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

	public ArrayList<MoveData> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<MoveData> moves) {
		this.moves = moves;
	}

	public ArrayList<PieceData> getCapturedPieces() {
		return capturedPieces;
	}

	public void setCapturedPieces(ArrayList<PieceData> capturedPieces) {
		this.capturedPieces = capturedPieces;
	}

	public Player getWhoseTurn() {
		return turn == Color.WHITE ? white : black;
	}
}
