package edu.uca.csci4490.chessgame.client.clientLoginScreen;

import edu.uca.csci4490.chessgame.model.data.WaitingRoomData;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.data.ErrorData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginScreenController implements ActionListener {

	// Private data fields for the container and chat client.
	private JPanel container;
	private ChessClientCommunication client;

	// Constructor for the login controller.
	public LoginScreenController(JPanel container, ChessClientCommunication client) {
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Create account panel takes the user to the create account gui
		if (command == "Create Account") {
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "3");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Sign In") {
			// Get the username and password the user entered.
			LoginView loginView = (LoginView) container.getComponent(1);
			PlayerLoginData data = new PlayerLoginData(loginView.getUsername(), loginView.getPassword());

			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals("")) {
				displayError("You must enter a username and password.");
				return;
			}

			// Submit the login information to the server.
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				displayError("Error connecting to the server.");
			}
		}
	}

	// After the login is successful, set the User object and display the waiting room screen
	public void loginSuccess() {
		LoginView loginView = (LoginView) container.getComponent(1);

		CardLayout cardLayout = (CardLayout) container.getLayout();
		// this number will have to be changed based on the ClientGUI
		cardLayout.show(container, "4");
	}

	// Method that displays a message in the error label.
	public void displayError(String error) {
		LoginView loginPanel = (LoginView) container.getComponent(1);
		loginPanel.setError(error);
	}

	public void receiveError(ErrorData data) {
		displayError(data.getMsg());
	}

	public void receiveWaitingRoom(WaitingRoomData data) {

	}
}

