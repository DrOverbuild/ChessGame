package edu.uca.csci4490.chessgame.server.communication;

import java.io.IOException;
import java.sql.SQLException;

import edu.uca.csci4490.chessgame.server.ChessServer;
import edu.uca.csci4490.chessgame.server.playermanager.PlayerManager;
import edu.uca.csci4490.chessgame.model.data.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServerCommunication extends AbstractServer {
	
	//Data fields for Chess Server
	private PlayerLoginCommunication playerLoginCommunication;
	private WaitingRoomCommunication waitingRoomCommunication;
	private GameCommunication gameCommunication;
	private ChessServer server;
	
	public ChessServerCommunication(int port, ChessServer server) {
		super(port);
		// initialize private data fields
		// etc 
		this.server = server;
		playerLoginCommunication = new PlayerLoginCommunication(server, server.getPlayerManager(), this);
		waitingRoomCommunication = new WaitingRoomCommunication();
		gameCommunication = new GameCommunication(server);
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient client) {
		{
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
	
	
	
}









//.