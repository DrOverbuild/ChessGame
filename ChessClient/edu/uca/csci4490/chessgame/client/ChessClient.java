package edu.uca.csci4490.chessgame.client;

import edu.uca.csci4490.chessgame.client.loginscreen.CreateAccountController;
import edu.uca.csci4490.chessgame.client.loginscreen.LoginScreenController;
import edu.uca.csci4490.chessgame.client.waitingroom.WaitingRoomController;
import edu.uca.csci4490.chessgame.client.waitingroom.WaitingRoomPanel;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class ChessClient extends JFrame {
	private static final String LOGIN_PANEL = "login";
	private static final String CREATE_ACCOUNT_PANEL = "create";
	private static final String WAITING_ROOM_PANEL = "waiting";
	private static final String GAME_SCREEN_PANEL = "game";


	private LoginScreenController lc;
	private CreateAccountController cc;
	private WaitingRoomController wc;
	private GameRoomControl gc;

	private CardLayout layout;
	private JPanel container;

	// Constructor that creates the client GUI.
	public ChessClient() {
		// Set up the chat client.
		ChessClientCommunication client = new ChessClientCommunication();
		client.setHost("localhost");
		client.setPort(8300);
		try {
			client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}


		// Set the title and default close operation.
		this.setTitle("Chat Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		layout = new CardLayout();
		container = new JPanel(layout);

		// Create the Controllers next
		// these controllers will also build the views.
		lc = new LoginScreenController(this, client);
		cc = new CreateAccountController(this, client);
		wc = new WaitingRoomController(this, client);
		gc = new GameRoomControl(container, client);

		// Retreive/create the views. Most controllers have already built the views.
		JPanel loginView = lc.getView();
		JPanel view3 = cc.getView();
		JPanel view4 = new WaitingRoomPanel(wc);
//		JPanel view5 = new GameRoomPanel(gc);

		// Add the views to the card layout container.
		container.add(loginView, LOGIN_PANEL);
		container.add(view3, CREATE_ACCOUNT_PANEL);
		container.add(view4, WAITING_ROOM_PANEL);
		container.add(view5, GAME_SCREEN_PANEL);

		// Show the initial view in the card layout.
		layout.show(container, LOGIN_PANEL);

		// Add the card layout container to the JFrame.
		// GridBagLayout makes the container stay centered in the window.
		this.setLayout(new GridBagLayout());
		this.add(container);

		// Show the JFrame.
		this.setSize(550, 350);
		this.setVisible(true);
	}

	public void transitionToLoginScreen() {
		layout.show(container, LOGIN_PANEL);
	}

	public void transitionToCreateAccountScreen() {
		layout.show(container, CREATE_ACCOUNT_PANEL);
	}

	public void transitionToWaitingRoom(Player loggedInPlayer, ArrayList<Player> players) {
		wc.setThisPlayer(loggedInPlayer);
		wc.setPlayers(players);
		layout.show(container, WAITING_ROOM_PANEL);
	}

	public void transitionToGameScreen(Game game) {

	}

	public LoginScreenController getLc() {
		return lc;
	}

	public CreateAccountController getCc() {
		return cc;
	}

	public WaitingRoomController getWc() {
		return wc;
	}

	public GameRoomControl getGc() {
		return gc;
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args) {
		new ChessClient();
	}
}