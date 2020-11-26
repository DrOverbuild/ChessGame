package edu.uca.csci4490.chessgame.server.communication;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.server.ChessServer;
import edu.uca.csci4490.chessgame.model.data.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServerCommunication extends AbstractServer {
	
	//Data fields for Chess Server
	private PlayerLoginCommunication playerLoginCommunication;
	private WaitingRoomCommunication waitingRoomCommunication;
	private GameCommunication gameCommunication;
	private ChessServer server;

	private Pinger pinger;
	
	public ChessServerCommunication(int port, ChessServer server) {
		super(port);
		// initialize private data fields
		// etc 
		this.server = server;
		playerLoginCommunication = new PlayerLoginCommunication(server, server.getPlayerManager(), this);
		waitingRoomCommunication = new WaitingRoomCommunication(server, this);
		gameCommunication = new GameCommunication(server, this);

		pinger = new Pinger(server, this);
		new Timer().scheduleAtFixedRate(pinger, 1000, 1000);
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient client) {
		{
			System.out.println("Incoming message from client " + client.getId());
			System.out.println(o);
			System.out.println();
			// If we received LoginData, verify the account information.
			if (o instanceof PlayerLoginData)
			{
				// Try to Login the player
				playerLoginCommunication.receivePlayerLogin((PlayerLoginData)o, client);
			}

			// If we received CreateAccountData, create a new account.
			else if (o instanceof CreateAccountData)
			{
				// Try to create the account.
				playerLoginCommunication.receiveCreateAccount((CreateAccountData)o, client);
			}
			else if (o instanceof PlayerLogoutData) {
				// log the player out and update the other players
				playerLoginCommunication.receivePlayerLogout((PlayerLogoutData) o);
			}
			else if (o instanceof PlayerChallengeData)
			{
				// Receive a challenge data
				waitingRoomCommunication.receivePlayerChallenge((PlayerChallengeData)o);
			}
			else if (o instanceof PlayerChallengeResponseData)
			{
				// Receive a challenge response data
				waitingRoomCommunication.receivePlayerChallengeResponse((PlayerChallengeResponseData)o);
			}
			else if (o instanceof PieceSelectionData)
			{
				// Receive data about the piece selection
				gameCommunication.receivePieceSelection((PieceSelectionData)o, client);
			}
			else if (o instanceof PieceMoveData)
			{
				// Receive Piece Move Data
				gameCommunication.receivePieceMove((PieceMoveData)o, client);
			}
			else if (o instanceof AbandonGameData)
			{
				// Receive game Abandoned
				gameCommunication.receiveGameAbandoned((AbandonGameData)o);
			}

			
		}	
	}
	
	public void listeningException(Throwable exception) {
		System.out.println("Listening exception: " + exception.getMessage() + "\n");
	}
	
	public void serverStarted(){
		System.out.println("Server Started\n");
	}
	
	public void serverStopped(){
		System.out.println("Server Stopped\n");
	}
	
	public void serverClosed(){
		System.out.println("Server Closed\n");
	}
	
	public void clientConnected(ConnectionToClient client){
		System.out.println("Client " + client.getId() + " connected\n");
	}
	
	public void setPlayerLoginCommunication(PlayerLoginCommunication playerLoginCommunication){
		this.playerLoginCommunication = playerLoginCommunication;
	}
	
	public PlayerLoginCommunication getPlayerLoginCommunication(){
		return playerLoginCommunication;
	}

	public void setWaitingRoomCommunication(WaitingRoomCommunication waitingRoomCommunication){
		this.waitingRoomCommunication = waitingRoomCommunication;
	}
	
	public WaitingRoomCommunication getWaitingRoomCommunication(){
		return waitingRoomCommunication;
	}
	
	public void setGameCommunication(GameCommunication gameCommunication) {
		this.gameCommunication = gameCommunication;
	}
	
	public GameCommunication getGameCommunication() {
		return gameCommunication;
	}

	/**
	 * Sends data object to given player. If an issue occurs, we need to remove the player from the server, whether
	 * they are in the waiting room or in a game.
	 * @param p
	 * @param data
	 */
	public void sendToPlayer(Player p, Object data) {
		try {
			if (!data.equals("Ping")) {
				System.out.println("Outgoing message to " + p.getUsername());
				System.out.println(data);
				System.out.println();
			}

			p.getClient().sendToClient(data);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();

			System.out.println("WARNING - Lost connection to " + p.getUsername() + ". Considering them disconnected.");
			System.out.println("Attempted to send: " + data);
			System.out.println();

			server.getPlayerManager().playerDisconnected(p);
			getWaitingRoomCommunication().sendWaitingRoomToAll();
		}
	}
}

/**
 * Simple class that sends a string message to each logged in player once a second to ensure the socket still exists.
 * If the message fails to send, ChessServerCommunication takes care of the fact that the player doesn't seem to be
 * connected. We have this class because the server doesn't know a client is disconnected until we try to send it a
 * message.
 */
class Pinger extends TimerTask {
	private ChessServer server;
	private ChessServerCommunication comms;

	public Pinger(ChessServer server, ChessServerCommunication comms) {
		this.server = server;
		this.comms = comms;
	}

	public ChessServer getServer() {
		return server;
	}

	public void setServer(ChessServer server) {
		this.server = server;
	}

	public ChessServerCommunication getComms() {
		return comms;
	}

	public void setComms(ChessServerCommunication comms) {
		this.comms = comms;
	}

	@Override
	public void run() {
		for (Player p: server.getPlayerManager().getAllLoggedInPlayers()) {
			comms.sendToPlayer(p, "Ping");
		}
	}
}