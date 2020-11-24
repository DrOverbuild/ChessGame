package edu.uca.csci4490.chessgame.server.communication;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServerCommunication extends AbstractServer {
	
	//Data fields for Chess Server
	//private PlayerLoginCommunication;
	//private WaitingRoomCommuniciation;
	//private GameCommunication;
	
	public ChessServerCommunication(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient connectionToClient) {

	}
	
	public void listeningException(Throwable exception) {
		
	}
	
	public void serverStarted(){
		
	}
	
	public void serverStopped(){
		
	}
	
	public void serverClosed(){
		
	}
	
	public void clientConnected(){
		
	}
	
	public void setPlayerLoginCommunication(){
		
	}
	
	public void getPlayerLoginCommunication(){
		
	}
	public void setWaitingRoomCommunication(){
		
	}
	
	public void getWaitingRoomCommunication(){
	
	}
	
	public void setGameCommunication() {
		
	}
	
	public void getGameCommunication() {
		
	}
	
	
	
}









//.