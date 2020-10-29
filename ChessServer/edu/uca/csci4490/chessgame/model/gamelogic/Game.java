package edu.uca.csci4490.chessgame.model.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;
import edu.uca.csci4490.chessgame.server.communication.GameCommunication;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
	transient private GameCommunication communication;

	private int id;
	private Piece[][] board;
	private Player white;
	private Player black;
	private Color turn;
	private ArrayList<Move> moves;
	private ArrayList<Piece> capturedPieces;

	public Game(int id, Player white, Player black) {
		this.id = id;
		this.white = white;
		this.black = black;
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
}
