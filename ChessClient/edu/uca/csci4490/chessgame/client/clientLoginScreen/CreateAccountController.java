package edu.uca.csci4490.chessgame.client.clientLoginScreen;

import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateAccountController implements ActionListener {
	// Private data fields for the container and chat client.
	private JPanel container;
	private ChessClientCommunication client;

	// Constructor for the create account controller.
	public CreateAccountController(JPanel container, ChessClientCommunication client) {
		this.container = container;
//	    this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// This might need to be changed
		// We want the back button to return the user to the login screen
		// we might need to change the card layout number
		if (command == "Back") {
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button creates a new account.
		else if (command == "Submit") {
			// Get the text the user entered in the three fields.
			CreateAccountView createAccountView = (CreateAccountView) container.getComponent(2);
			String username = createAccountView.getUsername();
			String password = createAccountView.getPassword();
			String passwordVerify = createAccountView.getPasswordVerify();

			// Check the validity of the information locally first.
			if (username.equals("") || password.equals("")) {
				displayError("You must enter a username and password.");
				return;
			} else if (!password.equals(passwordVerify)) {
				displayError("The two passwords did not match.");
				return;
			}
			if (password.length() < 6) {
				displayError("The password must be at least 6 characters.");
				return;
			}

			// Submit the new account information to the server.
			CreateAccountData data = new CreateAccountData(username, password);
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				displayError("Error connecting to the server.");
			}
		}
	}

	// uses card layout to show the waiting room panel
	public void createAccountSuccess() {
		CreateAccountView createAccountView = (CreateAccountView) container.getComponent(2);
//	    ClientGUI clientGUI = (ClientGUI)SwingUtilities.getWindowAncestor(createAccountPanel);
		//clientGUI.setUser(new User(createAccountPanel.getUsername(), createAccountPanel.getPassword()));
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "4");
	}

	// Method that displays a message in the error label.
	public void displayError(String error) {
		CreateAccountView createAccountView = (CreateAccountView) container.getComponent(2);
		createAccountView.setError(error);
	}
}
