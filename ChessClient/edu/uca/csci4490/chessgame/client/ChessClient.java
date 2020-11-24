package edu.uca.csci4490.chessgame.client;

import edu.uca.csci4490.chessgame.client.clientLoginScreen.CreateAccountController;
import edu.uca.csci4490.chessgame.client.clientLoginScreen.CreateAccountView;
import edu.uca.csci4490.chessgame.client.clientLoginScreen.LoginScreenController;
import edu.uca.csci4490.chessgame.client.clientLoginScreen.LoginView;
import edu.uca.csci4490.chessgame.client.clientWaitingRoom.WaitingRoomController;
import edu.uca.csci4490.chessgame.client.clientWaitingRoom.WaitingRoomPanel;
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

		//Create the Controllers next
		//Next, create the Controllers
		lc = new LoginScreenController(container, client);
		cc = new CreateAccountController(container, client);
		wc = new WaitingRoomController(container, this, client);
		gc = new GameRoomControl(container, client);

		// Create the four views. (need the controller to register with the Panels
		JPanel view2 = new LoginView(lc);
		JPanel view3 = new CreateAccountView(cc);
		JPanel view4 = new WaitingRoomPanel(wc);
//		JPanel view5 = new GameRoomPanel(gc);

		// Add the views to the card layout container.
		container.add(view2, LOGIN_PANEL);
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

	public void transitionToWaitingRoom(Player loggedInPlayer, ArrayList<Player> players) {
		wc.setLoggedInPlayer(loggedInPlayer);
		wc.setPlayers(players);
		layout.show(container, WAITING_ROOM_PANEL);
	}

	public void transitionToGameScreen(Game game) {

	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args) {
		new ChessClient();
	}


}