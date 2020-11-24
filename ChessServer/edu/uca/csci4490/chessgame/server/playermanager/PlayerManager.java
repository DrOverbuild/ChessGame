package edu.uca.csci4490.chessgame.server.playermanager;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;
import edu.uca.csci4490.chessgame.model.gamelogic.Color;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;
import edu.uca.csci4490.chessgame.server.ChessServer;
import edu.uca.csci4490.chessgame.server.database.Database;
import edu.uca.csci4490.chessgame.server.database.UserAlreadyExistsException;
import ocsf.server.ConnectionToClient;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerManager {
	// changed data types on all of these because we don't want duplicate entries
	private Set<Player> allLoggedInPlayers;
	private Set<Player> waitingRoom;
	private Map<Player, Set<Player>> challenges;

	private ChessServer server;
	private Database database;

	public PlayerManager(ChessServer server) {
		allLoggedInPlayers = new HashSet<>();
		waitingRoom = new HashSet<>();
		challenges = new HashMap<>();

		this.server = server;
		this.database = new Database();
	}

	/**
	 * Returns a copy of the waiting room list.
	 *
	 * @return
	 */
	public Set<Player> getWaitingRoom() {
		return new HashSet<>(waitingRoom);
	}

	/**
	 * Remember, when calling this, all players in the waiting room must be sent the update
	 */
	public void movePlayerToWaitingRoom(Player p) {
		waitingRoom.add(p);
	}


	/**
	 * Remember, when calling this, all players in the waiting room must be sent the update
	 */
	public void removePlayerFromWaitingRoom(Player p) {
		waitingRoom.remove(p);
		challenges.remove(p);

		for (Player challenger: challenges.keySet()) {
			challenges.get(challenger).remove(p);
		}
	}

	public Player playerByClient(ConnectionToClient client) {
		for (Player p : allLoggedInPlayers) {
			if (p.getClient() != null && p.getClient().equals(client)) {
				return p;
			}
		}

		return null;
	}

	public void playerChallenged(Player from, Player to) {
		if (challenges.containsKey(from)) {
			challenges.get(from).add(to);
		} else {
			Set<Player> players = new HashSet<>();
			players.add(to);
			challenges.put(from, players);
		}
	}

	/**
	 * If {to} accepted {from}'s request, both players are removed from the waiting room and placed in a new game.
	 * If the request was rejected, notify the other player {from}.
	 */
	public void playerResponded(Player from, Player to, boolean accepted) {
		if (!waitingRoom.contains(from) || !waitingRoom.contains(to) ||
				!playerHasChallengedAnotherPlayer(from, to)) {
			return; // this shouldn't happen
		}

		Set<Player> playersChallenges = challenges.get(from);
		if (playersChallenges != null) {
			playersChallenges.remove(to);

			if (accepted) {
				removePlayerFromWaitingRoom(from);
				removePlayerFromWaitingRoom(to);
				server.startGame(from, to);
			}
		}
	}

	public boolean playerHasChallengedAnotherPlayer(Player from, Player to) {
		Set<Player> playersChallenges = challenges.get(from);
		if (playersChallenges != null) {
			return playersChallenges.contains(to);
		}

		return false;
	}

	public Set<Player> getPlayersChallenges(Player from) {
		if (!challenges.containsKey(from)) {
			return new HashSet<>();
		}
		return new HashSet<>(challenges.get(from));
	}

	/**
	 *
	 * @param data
	 * @return true if account creation is successful. False if there is a problem.
	 */
	public boolean accountCreated(CreateAccountData data) {
		try {
			database.createAccount(data);
			return true;
		} catch (UserAlreadyExistsException | SQLException e) {
			return false;
		}
	}

	/**
	 *
	 * @param data
	 * @param client
	 * @return true if authentication was successful. False if there is a problem.
	 */
	public boolean clientLoggedIn(PlayerLoginData data, ConnectionToClient client) {
		Player p = database.authenticatePlayer(data, client);

		if (p == null) {
			return false;
		} else {
			allLoggedInPlayers.add(p);
			this.movePlayerToWaitingRoom(p);
			return true;
		}
	}

	public void clientDisconnected(ConnectionToClient client) {
		Player remove = playerByClient(client);

		allLoggedInPlayers.remove(remove);
		waitingRoom.remove(remove);

		for (Player p : challenges.keySet()) {
			challenges.get(p).remove(p);
		}

		Game game = server.gameOfPlayer(remove);
		if (game != null) {
			game.playerAbandonedGame(remove);
			server.endGame(game);
		}
	}

	public void updateStats(Game game) {
		Player winner = game.getWinner();
		Player loser = game.getLoser();
		Color winnerColor = Color.WHITE;
		Color loserColor = Color.BLACK;

		if (winner.equals(game.getBlack())) {
			winnerColor = Color.BLACK;
			loserColor = Color.WHITE;
		}

		winner.setWins(winner.getWins() + 1);
		loser.setLosses(loser.getLosses() + 1);

		// get xp for winner (100 for winning + however many pieces are left)

		// king: 0 // this piece will always be on the board so it doesnt count
		// queen: 10
		// bishop: 8
		// knight: 7
		// rook: 6
		// pawn: 1
		int winnerXP = winner.getXp() + 100;
		int loserXP = loser.getXp();
		for (Piece p: game.getBoard().allPieces(null)) {
			if (p.getColor().equals(winnerColor)) {
				winnerXP+=p.getWorth();
			} else {
				loserXP+=p.getWorth();
			}
		}

		winner.setXp(winnerXP);
		loser.setXp(loserXP);

		database.updatePlayerData(winner);
		database.updatePlayerData(loser);
	}
}