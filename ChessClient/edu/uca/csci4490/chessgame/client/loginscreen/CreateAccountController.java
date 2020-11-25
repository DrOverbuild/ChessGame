package edu.uca.csci4490.chessgame.client.loginscreen;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountController implements ActionListener {

	private ChessClientCommunication comms;
	private ChessClient client;
	private CreateAccountView view;

	// Constructor for the create account controller.
	public CreateAccountController(ChessClient client, ChessClientCommunication comms) {
		this.comms = comms;
		this.client = client;

		view = new CreateAccountView(this);
	}

	public CreateAccountView getView() {
		return view;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command.equals("Back")) {
			client.transitionToLoginScreen();
		}

		// The Submit button creates a new account.
		else if (command.equals("Submit")) {
			// Get the text the user entered in the three fields.
			String username = view.getUsername();
			String password = view.getPassword();
			String passwordVerify = view.getPasswordVerify();

			if (!validateFields(username, password, passwordVerify)) {
				return;
			}

			view.disableButtons();
			sendCreateAccount(username, password);
		}
	}

	public boolean validateFields(String username, String password, String passwordVerify) {
		// Check the validity of the information locally first.
		if (username.equals("") || password.equals("")) {
			view.displayError("You must enter a username and password.");
			return false;
		}

		if (!password.equals(passwordVerify)) {
			view.displayError("The two passwords did not match.");
			return false;
		}

		if (password.length() < 6) {
			view.displayError("The password must be at least 6 characters.");
			return false;
		}

		return true;
	}

	public void receiveCreateAccountSuccess() {
		view.displaySuccess("Create account successful. Click Back to log in.");
		view.enableButtons();
	}

	public void receiveCreateAccountUnsuccessful() {
		view.displayError("Username already exists.");
		view.enableButtons();
	}

	public void sendCreateAccount(String username, String password) {
		CreateAccountData data = new CreateAccountData(username, password);
		comms.send(data);
	}
}
