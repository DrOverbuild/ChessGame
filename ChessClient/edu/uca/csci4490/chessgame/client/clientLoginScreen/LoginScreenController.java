package edu.uca.csci4490.chessgame.client.clientLoginScreen;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.WaitingRoomData;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.data.ErrorData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenController implements ActionListener {

	// Private data fields for the container and chat client.
	private LoginView view;
	private ChessClientCommunication comms;
	private ChessClient client;

	/**
	 * Username with which user attempts to sign in for. Set to null unless we're waiting for login results.
	 * If we get a waiting room in response, then login was successful. Find the player in the waiting room
	 * with the same username as  attemptingUsername, and that's the logged in player.
	 */
	private String attemptingUsername = null;

	// Constructor for the login controller.
	public LoginScreenController(ChessClient client, ChessClientCommunication comms) {
		this.comms = comms;
		this.client = client;

		view = new LoginView(this);
	}

	public LoginView getView() {
		return view;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Create account panel takes the user to the create account gui
		if (command.equals("Create Account")) {
			client.transitionToCreateAccountScreen();
		}

		// The Submit button submits the login information to the server.
		else if (command.equals("Sign In")) {
			// Get the username and password the user entered.
			String username = view.getUsername();
			String password = view.getPassword();

			// Check the validity of the information locally first.
			if (username.equals("") || password.equals("")) {
				displayError("You must enter a username and password.");
				return;
			}

			view.disableButtons();

			// Submit the login information to the server.
			sendLogin(username, password);
		}
	}

	// Method that displays a message in the error label.
	public void displayError(String error) {
		view.setError(error);
	}

	public void receiveError(ErrorData data) {
		attemptingUsername = null;
		view.enableButtons();
		displayError(data.getMsg());
	}

	public void receiveWaitingRoom(WaitingRoomData data) {
		if (attemptingUsername == null) {
			System.out.println("WARNING - attemptingUsername set to null... the login screen receieved WaitingRoomData " +
					"but was not waiting for log in results.");
			return;
		}

		Player loggedIn = null;

		for (Player p: data.getPlayers()) {
			if (p.getUsername().equals(attemptingUsername)) {
				loggedIn = p;
			}
		}

		if (loggedIn == null) {
			System.out.println("WARNING - not in waiting room... the login screen received WaitingRoomData but " +
					"could not find " + attemptingUsername + " in waiting room. Ignoring the message.");
			return;
		}

		// reset login ui for logging out and returning to beginning screen
		attemptingUsername = null;
		view.enableButtons();

		client.transitionToWaitingRoom(loggedIn, data.getPlayers());
	}

	public void sendLogin(String username, String password) {
		attemptingUsername = username;

		PlayerLoginData data = new PlayerLoginData(username, password);
		comms.send(data);
	}
}

