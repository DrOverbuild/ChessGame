package edu.uca.csci4490.chessgame.server.gamelogic;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.Piece;

import java.util.ArrayList;

public class GameTest {
	public static void main(String[] args) {
		try {
			foolsMateTest();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void basicGameTest() {
		Player black = new Player(null, 1, "testBlack", 0, 0, 0);
		Player white = new Player(null, 1, "testWhite", 0, 0, 0);

		Game game = new Game(0, white, black);
		game.getBoard().setupBoard();

		// select white knight and move to first location
		Piece piece = game.getBoard().getPieceAt(1, 0);
		ArrayList<Location> moves = game.pieceSelected(piece);
		Location toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		// select black pawn and move to first location
		piece = game.getBoard().getPieceAt(2, 6);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		// select white pawn and move to second location
		piece = game.getBoard().getPieceAt(1, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// select black pawn and move to second location
		piece = game.getBoard().getPieceAt(3, 6);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// select white pawn and move forward
		piece = game.getBoard().getPieceAt(4, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// select black biship
		piece = game.getBoard().getPieceAt(2, 7);
		moves = game.pieceSelected(piece);
		toMove = moves.get(3);
		game.movePiece(piece, toMove, null);

		// select white queen, move to block bishop
		piece = game.getBoard().getPieceAt(4, 0);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		// select black bishop, capture queen
		piece = game.getBoard().getPieceAt(6, 3);
		moves = game.pieceSelected(piece);
		toMove = moves.get(3);
		game.movePiece(piece, toMove, null);

		System.out.println(game.getBoard());
		System.out.println();
	}

	public static void foolsMateTest() throws InterruptedException {
		Player black = new Player(null, 1, "testBlack", 0, 0, 0);
		Player white = new Player(null, 1, "testWhite", 0, 0, 0);

		Game game = new Game(0, white, black);
		game.getBoard().setupBoard();

		System.out.println(game.getBoard());
		System.out.println();
		Thread.sleep(1500);

		// select white pawn (first mistake)
		Piece piece = game.getBoard().getPieceAt(2, 1);
		ArrayList<Location> moves = game.pieceSelected(piece);
		Location toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		System.out.println(game.getBoard());
		System.out.println();
		Thread.sleep(1500);

		// select black pawn and move to first location
		piece = game.getBoard().getPieceAt(3, 6);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		System.out.println(game.getBoard());
		System.out.println();
		Thread.sleep(1500);

		// select another white pawn
		piece = game.getBoard().getPieceAt(1, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		System.out.println(game.getBoard());
		System.out.println();
		Thread.sleep(1500);

		// select black queen
		piece = game.getBoard().getPieceAt(4, 7);
		moves = game.pieceSelected(piece);
		toMove = moves.get(3);
		game.movePiece(piece, toMove, null);

		System.out.println(game.getBoard());
		Thread.sleep(5000);


	}
}
