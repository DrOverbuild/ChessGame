import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.server.gamelogic.Game;
import edu.uca.csci4490.chessgame.server.gamelogic.Location;
import edu.uca.csci4490.chessgame.server.gamelogic.piece.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
	}
}
