package edu.uca.csci4490.chessgame.client.clientWaitingRoom;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.client.clientLoginScreen.LoginView;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.PlayerChallengeData;
import edu.uca.csci4490.chessgame.model.data.PlayerChallengeResponseData;
import edu.uca.csci4490.chessgame.model.data.StartOfGameData;
import edu.uca.csci4490.chessgame.model.data.WaitingRoomData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaitingRoomController implements ActionListener, ListSelectionListener {

	private Player loggedInPlayer;

	private ArrayList<Player> waitingRoomPlayers = new ArrayList<>();

	// Private data fields for the container and chat client.
	private JPanel container;
	private ChessClient client;
	private ChessClientCommunication comms;

	private PlayerListPanel playerListPanel;
	private PlayerViewPanel playerViewPanel;

	// Constructor for the login controller.
	public WaitingRoomController(JPanel container, ChessClient client, ChessClientCommunication comms) {
		this.comms = comms;
		this.client = client;

		playerListPanel = new PlayerListPanel(this);
		playerViewPanel = new PlayerViewPanel(this);
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The log out button takes the user back to the login screen
		// the card layout number might need to be changed
		if (command == "Log Out") {
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Accept") {

			// not sure what specifically needs to happen here
		}
	}

	// After the login is successful, set the User object and display the contacts screen.
	public void logOutSuccess() {
		LoginView loginPanel = (LoginView) container.getComponent(1);

		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "4");
	}

	public PlayerListPanel getListPanel() {
		return playerListPanel;
	}

	public PlayerViewPanel getDetailPanel() {
		return playerViewPanel;
	}

	public void setLoggedInPlayer(Player player) {
		this.loggedInPlayer = player;
	}

	public void setPlayers(ArrayList<Player> waitingRoomPlayers) {
		this.waitingRoomPlayers = waitingRoomPlayers;
	}

	public ArrayList<Player> getWaitingRoomPlayers() {
		return waitingRoomPlayers;
	}

	private void setSelectedPlayer(Player p) {
		playerViewPanel.setPlayer(p);
	}

	public void receiveWaitingRoom(WaitingRoomData data) {
		this.waitingRoomPlayers = data.getPlayers();
		playerListPanel.updatePlayers();
	}

	public void receivePlayerChallenge(PlayerChallengeData data) {

	}

	public void receivePlayerChallengeResponse(PlayerChallengeResponseData data) {

	}

	public void receiveStartOfGame(StartOfGameData data) {
		this.client.transitionToGameScreen(data.getGame());
	}

	public void sendPlayerChallenge(Player to) {
		// TODO setup data object

//		client.
	}

	public void sendPlayerChallengeResponse(Player to, boolean accepted) {
		// TODO setup data object
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
