import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.server.gamelogic.Board;
import edu.uca.csci4490.chessgame.server.gamelogic.Direction;
import edu.uca.csci4490.chessgame.server.gamelogic.Game;
import edu.uca.csci4490.chessgame.server.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.LookupOp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Game Integration test...
 * Tests the entire gamelogic package, including Board, Direction, Game, Location, and Move
 *
 * Simulating full games in these integration tests will in turn test major methods in classes
 * that are contained within Game.
 */
public class GameIntegration {
	Game game;
	@Before
	public void setup() {
		Player white = new Player(null, 0, "whiteTest", 0, 0, 0);
		Player black = new Player(null, 1, "blackTest", 0, 0, 0);
		game = new Game(0, white, black);
		game.getBoard().setupBoard();
	}

	/**
	 * Test a full game using the shortest known combination of moves that will end in checkmate.
	 *
	 */
	@Test
	public void foolsMate() {
		assertEquals(game.getWhoseTurn(), game.getWhite());

		// select white pawn (first mistake)
		Piece piece = game.getBoard().getPieceAt(2, 1);
		ArrayList<Location> moves = game.pieceSelected(piece);
		assertEquals(moves.size(), 2); // make sure pawn only has two moves: forward by 1, forward by 2
		Location toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		assertEquals(game.getWhoseTurn(), game.getBlack()); // check for turn swap

		// select black pawn and move to first location
		piece = game.getBoard().getPieceAt(3, 6);
		moves = game.pieceSelected(piece);
		assertEquals(moves.size(), 2); // make sure pawn only has two moves: forward by 1, forward by 2
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		// select another white pawn
		piece = game.getBoard().getPieceAt(1, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// select black queen
		piece = game.getBoard().getPieceAt(4, 7);
		moves = game.pieceSelected(piece);
		toMove = moves.get(3);
		game.movePiece(piece, toMove, null);

		assertTrue(game.isCheckmate());
		assertEquals(game.getBlack(), game.getWinner());
		assertEquals(game.getMoves().size(), 4); // this game should only have four moves
		assertEquals(game.getCapturedPieces().size(), 0); // this game shouldn't have any captured pieces
	}

	@Test
	public void testPiecePromotion() {
		Piece piece = game.getBoard().getPieceAt(1, 0);
		ArrayList<Location> moves = game.pieceSelected(piece);
		Location toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(2, 6);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(1, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(2, 4);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(2, 2);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(1, 3);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(4, 3);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(1, 2);
		moves = game.pieceSelected(piece);
		toMove = moves.get(2);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(0, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(1, 1);
		moves = game.pieceSelected(piece);
		toMove = moves.get(2);
		game.movePiece(piece, toMove, Queen.class);

		piece = game.getBoard().getPieceAt(6, 2);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		piece = game.getBoard().getPieceAt(1,0);
		assertEquals(piece.getClass(), Queen.class); // check if indeed the pawn has been changed to a queen
		assertTrue(game.pieceSelected(piece).size() > 1); // piece should have more than 1 move
	}

	@Test
	public void testCaptureAndCheck() {
		// move knight
		Piece piece = game.getBoard().getPieceAt(1, 0);
		ArrayList<Location> moves = game.pieceSelected(piece);
		Location toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		// move black pawn
		piece = game.getBoard().getPieceAt(3, 6);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// move white knight again
		piece = game.getBoard().getPieceAt(2, 2);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// move black queen
		piece = game.getBoard().getPieceAt(4, 7);
		moves = game.pieceSelected(piece);
		toMove = moves.get(3);
		game.movePiece(piece, toMove, null);

		// move white knight again
		piece = game.getBoard().getPieceAt(4, 3);
		moves = game.pieceSelected(piece);
		toMove = moves.get(1);
		game.movePiece(piece, toMove, null);

		// capture pawn with black queen
		piece = game.getBoard().getPieceAt(0, 3);
		moves = game.pieceSelected(piece);
		toMove = moves.get(12);
		game.movePiece(piece, toMove, null);

		assertTrue(game.isInCheck()); // white king should be in check at this point

		// white king captures black qeuen
		piece = game.getBoard().getPieceAt(3, 0);
		moves = game.pieceSelected(piece);
		toMove = moves.get(0);
		game.movePiece(piece, toMove, null);

		assertEquals(game.getCapturedPieces().size(), 2);
	}

	/**
	 * Test Board.pieceIsEndangered()
	 */
	@Test
	public void boardPieceIsEndangered() {
		Board board = new Board();
		Piece whiteQueen = new Queen(Color.WHITE);
		Piece blackQueen = new Queen(Color.BLACK);

		// set queens on opposite corners of the board
		board.setPieceOnBoard((byte)0, (byte)0, blackQueen);
		board.setPieceOnBoard((byte)7, (byte)7, whiteQueen);

		assertTrue(board.pieceIsEndangered(whiteQueen));
		assertTrue(board.pieceIsEndangered(blackQueen));
	}

	@Test
	public void boardPieceIsBlocked() {
		Board board = new Board();
		Piece whiteQueen = new Queen(Color.WHITE);
		Piece blackQueen = new Queen(Color.BLACK);
		Piece blackPawn = new Pawn(Color.BLACK);

		// set queens on opposite corners of the board
		board.setPieceOnBoard((byte)0, (byte)0, blackQueen);
		board.setPieceOnBoard((byte)7, (byte)7, whiteQueen);

		// put pawn in the way from getting to the other queen
		board.setPieceOnBoard((byte)2,(byte)2, blackPawn);

		// black queen should be blocked from white queen
		assertTrue(board.pieceIsBlocked(blackQueen, whiteQueen.getLocation()));
	}

	/**
	 * Get a new board if a move is made to check hypothetical
	 */
	@Test
	public void boardGetHypothetical() {
		Board board = new Board();
		Piece blackQueen = new Queen(Color.BLACK);
		board.setPieceOnBoard((byte)0, (byte)0, blackQueen);

		Board hypothetical = board.getHypothetical(blackQueen, new Location((byte)7, (byte)0));

		assertNotNull(hypothetical.getPieceAt(7,0));
		assertNull(hypothetical.getPieceAt(0,0));
	}

	/**
	 * Testing Location.getRelative(Direction, int)
	 */
	@Test
	public void locationGetRelative() {
		Location loc = new Location((byte)3, (byte)3);

		Location farthestEast = loc.getRelative(Direction.EAST, 4);
		assertEquals(farthestEast, new Location((byte)7,(byte)3));

		Location offTheBoard = loc.getRelative(Direction.EAST, 5);
		assertNull(offTheBoard);

		ArrayList<Location> allLocations = loc.allLocationsInDirection(Direction.EAST);
		assertEquals(allLocations.size(),4);
	}

	/**
	 * This game starts with white having dominated the board, with black only having the queen left.
	 * The board starts out looking like this:
	 *
	 *     0   1   2   3   4   5   6   7
	 *  0  --  --  --  WK  --  --  WR  --
	 *  1  WP  --  --  --  --  --  --  --
	 *  2  --  --  --  --  --  --  --  --
	 *  3  --  WR  --  --  WP  --  --  --
	 *  4  --  --  --  --  --  --  --  --
	 *  5  --  --  --  WQ  --  --  --  --
	 *  6  WP  --  --  --  --  --  --  WP
	 *  7  --  --  --  BK  --  --  --  --
	 *
	 *  After promoting the pawn aat the
	 */
	@Test
	public void testStalemate() {
		Player white = new Player(null, 0, "whiteTest", 0, 0, 0);
		Player black = new Player(null, 1, "blackTest", 0, 0, 0);
		game = new Game(0, white, black);
		Board board = game.getBoard();
		board.setPieceOnBoard((byte)3, (byte)0, new King(Color.WHITE));
		board.setPieceOnBoard((byte)6, (byte)0, new Rook(Color.WHITE));
		board.setPieceOnBoard((byte)0, (byte)1, new Pawn(Color.WHITE));
		board.setPieceOnBoard((byte)1, (byte)3, new Rook(Color.WHITE));
		board.setPieceOnBoard((byte)4, (byte)3, new Pawn(Color.WHITE));
		board.setPieceOnBoard((byte)3, (byte)5, new Queen(Color.WHITE));
		board.setPieceOnBoard((byte)0, (byte)6, new Pawn(Color.WHITE));
		board.setPieceOnBoard((byte)7, (byte)6, new Pawn(Color.WHITE));

		board.setPieceOnBoard((byte)3, (byte)7, new King(Color.BLACK));

		game.setInCheck(true);
		game.setTurn(Color.BLACK);

		// move black king out of way of white queen
		Piece blackKing = game.getBoard().getPieceAt(3,7);
		List<Location> moves = game.pieceSelected(blackKing);
		game.movePiece(blackKing, moves.get(0), null);

		// move rook to row 6
		Piece whiteRook = game.getBoard().getPieceAt(1,3);
		moves = game.pieceSelected(whiteRook);
		game.movePiece(whiteRook, moves.get(5), null);

		// at this point the king has nowhere to run, with the rook at (6,0)
		// blocking the king from going any further east, and the queen at
		// (3,5) blocking the king from going any further west. This should
		// end in a stalemate. King is not in check, but there's nowhere to
		// go where he'll be safe.

		assertTrue(game.isStalemate());
		assertFalse(game.isInCheck());
		assertFalse(game.isCheckmate());

		moves = game.pieceSelected(blackKing);

		assertEquals(moves.size(), 0);
	}
}
