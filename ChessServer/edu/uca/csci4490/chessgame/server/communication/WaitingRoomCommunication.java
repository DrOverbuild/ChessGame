package edu.uca.csci4490.chessgame.server.communication;

import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.server.ChessServer;

public class WaitingRoomCommunication {
	
	private ChessServer server;
	
	public void sendWaitingRoom () {
		//WaitingRoomData data = new WaitingRoomData(PlayerManager.getWaitingRoom());
	}
	
	public void sendWaitingRoomToAll () {
		
	}
	
	public void sendPlayerChallenge () {
		PlayerChallengeData data = new PlayerChallengeData(Player from);
	}
	
	public void receivePlayerChallenge () {
		
	}
	
	public void receivePlayerAcceptChallenge () {
		
	}
	
}

