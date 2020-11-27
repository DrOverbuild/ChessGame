package edu.uca.csci4490.chessgame.client.waitingroom;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.model.gamelogic.GameData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaitingRoomController implements ActionListener, ListSelectionListener {

	private Player thisPlayer;

	private Player selectedPlayer = null;

	/**
	 * List of players that have challenged the logged in player to a game
	 */
	private ArrayList<Player> challengers = new ArrayList<>();

	/**
	 * List of player that the logged in player has challenged;
	 */
	private ArrayList<Player> challengees = new ArrayList<>();

	private ArrayList<Player> waitingRoomPlayers = new ArrayList<>();

	private ChessClient client;
	private ChessClientCommunication comms;

	private WaitingRoomPanel view;
	private PlayerListPanel playerListPanel;
	private PlayerViewPanel playerViewPanel;

	// Constructor for the login controller.
	public WaitingRoomController(ChessClient client, ChessClientCommunication comms) {
		this.comms = comms;
		this.client = client;

		playerListPanel = new PlayerListPanel(this);
		playerViewPanel = new PlayerViewPanel(this);
		view = new WaitingRoomPanel(this);

	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The log out button takes the user back to the login screen
		// the card layout number might need to be changed
		if (command.equals("Log out")) {
			sendPlayerLogout();
		}

		else if (command.equals("Challenge")) {
			sendPlayerChallenge(selectedPlayer);
		}

		// The Submit button submits the login information to the server.
		else if (command.equals("Accept")) {
			sendPlayerChallengeResponse(selectedPlayer, true);
		}
	}

	public WaitingRoomPanel getView() {
		return view;
	}

	public void setView(WaitingRoomPanel view) {
		this.view = view;
	}

	public PlayerListPanel getListPanel() {
		return playerListPanel;
	}

	public PlayerViewPanel getDetailPanel() {
		return playerViewPanel;
	}

	public ArrayList<Player> getChallengers() {
		return challengers;
	}

	public ArrayList<Player> getChallengees() {
		return challengees;
	}

	public Player getThisPlayer() {
		return thisPlayer;
	}

	public void setThisPlayer(Player player) {
		this.thisPlayer = player;
	}

	public void setPlayers(ArrayList<Player> waitingRoomPlayers) {
		this.waitingRoomPlayers = waitingRoomPlayers;
		this.getListPanel().updatePlayers();
	}

	public ArrayList<Player> getWaitingRoomPlayers() {
		return waitingRoomPlayers;
	}

	private void setSelectedPlayer(Player p) {
		this.selectedPlayer = p;
		playerViewPanel.setPlayer(p);
	}

	public Player playerById(int id) {
		for (Player p: waitingRoomPlayers) {
			if (p.getId() == id) {
				return p;
			}
		}

		return null;
	}

	public void emptyChallenges() {
		challengees = new ArrayList<>();
		challengers = new ArrayList<>();
	}

	public void receiveWaitingRoom(WaitingRoomData data) {
		this.waitingRoomPlayers = data.getPlayers();
		playerListPanel.updatePlayers();
	}

	public void receivePlayerChallenge(PlayerChallengeData data) {
		if (thisPlayer.equals(data.getTo())) {
			challengers.add(data.getFrom());
			playerListPanel.updatePlayers();

			// refresh challenge button if the challenge was from the currently selected player
			if (selectedPlayer != null && selectedPlayer.equals(data.getFrom())) {
				setSelectedPlayer(selectedPlayer);
			}

			comms.send(thisPlayer.getUsername() + " received player challenge");
		} else {
			System.out.println("WARNING - received a challenge but currently logged in player is not to whom" +
					" this challenge was directed.");
		}
	}

	public void receivePlayerChallengeResponse(PlayerChallengeResponseData data) {
		if (data.getFrom().equals(thisPlayer)) {
			challengers.remove(data.getFrom());
			playerListPanel.updatePlayers();
		} else {
			System.out.println("WARNING - received a challenge response but currently logged in player is not to whom" +
					" this response was directed.");
		}
	}

	public void receiveStartOfGame(StartOfGameData data) {
		GameData game = data.getGame();
		challengers.remove(game.getBlack());
		challengers.remove(game.getWhite());

		this.client.transitionToGameScreen(game, thisPlayer);
	}

	public void sendPlayerChallenge(Player to) {
		challengees.add(to);
		setSelectedPlayer(selectedPlayer);
		playerListPanel.updatePlayers();

		PlayerChallengeData data = new PlayerChallengeData(thisPlayer, to);
		comms.send(data);
	}

	public void sendPlayerChallengeResponse(Player from, boolean accepted) {
		PlayerChallengeResponseData data = new PlayerChallengeResponseData(from, thisPlayer, accepted);
		comms.send(data);
	}

	public void sendPlayerLogout() {
		PlayerLogoutData data = new PlayerLogoutData();
		data.setPlayer(thisPlayer);

		comms.send(data);

		thisPlayer = null;

		client.transitionToLoginScreen();
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// ensure the selection has stopped changing
		if (!e.getValueIsAdjusting()) {
			// ensure the source is a ListSelectionModel object
			if (e.getSource() instanceof ListSelectionModel) {
				// cast it
				ListSelectionModel model = ((ListSelectionModel) e.getSource());
				// ensure there is something selected
				if (!model.isSelectionEmpty()) {
					// get the index of the selection
					int index = model.getMaxSelectionIndex();

					// get the patient at index and load it in the editor
					Player p = this.getWaitingRoomPlayers().get(index);
					this.setSelectedPlayer(p);
				}
			}
		}
	}
}
