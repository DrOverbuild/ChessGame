package edu.uca.csci4490.chessgame.client;

import edu.uca.csci4490.chessgame.client.GameScreen.GameScreenController;
import edu.uca.csci4490.chessgame.client.loginscreen.CreateAccountController;
import edu.uca.csci4490.chessgame.client.loginscreen.LoginScreenController;
import edu.uca.csci4490.chessgame.client.waitingroom.WaitingRoomController;
import edu.uca.csci4490.chessgame.client.communication.ChessClientCommunication;
import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class ChessClient extends JFrame {
	public static final String LOGIN_PANEL = "login";
	public static final String CREATE_ACCOUNT_PANEL = "create";
	public static final String WAITING_ROOM_PANEL = "waiting";
	public static final String GAME_SCREEN_PANEL = "game";

	private String currentPanel = LOGIN_PANEL;

	private LoginScreenController lc;
	private CreateAccountController cc;
	private WaitingRoomController wc;
	private GameScreenController gc;

	private CardLayout layout;
	private JPanel container;

	// Constructor that creates the client GUI.
	public ChessClient(String ip, int port) {
		// Set up the chat client.
		ChessClientCommunication client = new ChessClientCommunication(this);
		client.setHost(ip);
		client.setPort(port);

		try {
			client.openConnection();
		} catch (IOException e) {
			System.out.println("FATAL - Could not establish connection");
			e.printStackTrace();
			System.exit(-1);
		}


		// Set the title and default close operation.
		this.setTitle("Chess Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		layout = new CardLayout();
		container = new JPanel(layout);

		// Create the Controllers next
		// these controllers will also build the views.
		lc = new LoginScreenController(this, client);
		cc = new CreateAccountController(this, client);
		wc = new WaitingRoomController(this, client);
		gc = new GameScreenController(this, client);

		// Retreive/create the views. Most controllers have already built the views.
		JPanel loginView = lc.getView();
		JPanel createAccountView = cc.getView();
		JPanel waitingRoomView = wc.getView();
		JPanel gameScreenView = gc.getView();

		// Add the views to the card layout container.
		container.add(loginView, LOGIN_PANEL);
		container.add(createAccountView, CREATE_ACCOUNT_PANEL);
		container.add(waitingRoomView, WAITING_ROOM_PANEL);
		container.add(gameScreenView, GAME_SCREEN_PANEL);

		// Show the initial view in the card layout.
		layout.show(container, LOGIN_PANEL);

		// Add the card layout container to the JFrame.
		// GridBagLayout makes the container stay centered in the window.
		this.setLayout(new BorderLayout());
		this.add(container, BorderLayout.CENTER);

		// Show the JFrame.
		this.setSize(550, 350);
		this.setVisible(true);
	}

	private void transition() {
		layout.show(container, currentPanel);
	}

	public void transitionToLoginScreen() {
		currentPanel = LOGIN_PANEL;
		transition();
	}

	public void transitionToCreateAccountScreen() {
		currentPanel = CREATE_ACCOUNT_PANEL;
		transition();
	}

	public void transitionToWaitingRoom(Player loggedInPlayer, ArrayList<Player> players) {
		wc.setThisPlayer(loggedInPlayer);
		wc.setPlayers(players);
		wc.emptyChallenges();
		currentPanel = WAITING_ROOM_PANEL;
		transition();
	}

	public void transitionToGameScreen(Game game, Player player) {
		gc.initGame(game, player);
		currentPanel = GAME_SCREEN_PANEL;
		transition();
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

	public GameScreenController getGc() {
		return gc;
	}

	public String getCurrentPanel() {
		return currentPanel;
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args) {
		String ip = "localhost";
		String port = "8300";
		String[] exp;
		if (args.length == 0) {
			exp = JOptionPane.showInputDialog("Enter IP Address: ").split(":");
		} else {
			exp = args[0].split(":");
		}

		if (exp.length >= 1) {
			ip = exp[0];
		}

		if (exp.length >= 2 ) {
			port = exp[1];
		}

		int portInt = Integer.parseInt(port);

		new ChessClient(ip, portInt);
	}
}