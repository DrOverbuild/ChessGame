package edu.uca.csci4490.chessgame.server.playermanager;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.server.ChessServer;
import edu.uca.csci4490.chessgame.server.communication.ChessServerCommunication;
import edu.uca.csci4490.chessgame.server.database.Database;
import edu.uca.csci4490.chessgame.server.database.UserAlreadyExistsException;
import ocsf.server.ConnectionToClient;

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
	private ChessServerCommunication comms;

	public PlayerManager(ChessServer server, ChessServerCommunication comms) {
		allLoggedInPlayers = new HashSet<>();
		waitingRoom = new HashSet<>();
		challenges = new HashMap<>();

		this.server = server;
		this.comms = comms;
	}

	/**
	 * Returns a copy of the waiting room list.
	 *
	 * @return
	 */
	public Set<Player> getWaitingRoom() {
		return new HashSet<>(waitingRoom);
	}

	public void movePlayerToWaitingRoom(Player p) {
		waitingRoom.add(p);
		comms.getWaitingRoomCommunication().sendWaitingRoomToAll(getWaitingRoom());
	}

	public void removePlayerFromWaitingRoom(Player p) {
		waitingRoom.remove(p);
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
		if (accepted) {
			if (waitingRoom.contains(from) && waitingRoom.contains(to) &&
					playerHasChallengedAnotherPlayer(from, to)) {
				server.startGame(from, to);
			} else {
				// todo notify responder of problem
			}
		} else {
			Set<Player> playersChallenges = challenges.get(from);
			if (playersChallenges != null) {
				playersChallenges.remove(to);
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

		// TODO check if player is in game and end game if so
	}

	public void updateStats(Game game) {

	}
}
