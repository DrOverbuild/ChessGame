package edu.uca.csci4490.chessgame.server.communication;

import java.util.ArrayList;
import java.util.Set;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.server.ChessServer;

public class WaitingRoomCommunication {
	
	private ChessServer server;
	private ChessServerCommunication comms;

	public WaitingRoomCommunication(ChessServer server, ChessServerCommunication comms) {
		this.server = server;
		this.comms = comms;
	}

	public void sendWaitingRoomToAll () {
		Set<Player> players = server.getPlayerManager().getWaitingRoom();

		ArrayList <Player> playerlist = new ArrayList<>(players);
		WaitingRoomData data = new WaitingRoomData(playerlist);
		for(Player p: players) {
			comms.sendToPlayer(p, data);
		}
	}
	
	public void sendPlayerChallenge (PlayerChallengeData data) {
		comms.sendToPlayer(data.getTo(), data);
	}
	
	public void sendPlayerChallengeResponse(PlayerChallengeResponseData challengedata) {
		server.getPlayerManager().playerResponded(challengedata.getFrom(), challengedata.getTo(), challengedata.getAccepted());
	}
	
	public void receivePlayerChallenge (PlayerChallengeData data) {
		server.getPlayerManager().playerChallenged(data.getFrom(),data.getTo());
		sendPlayerChallenge(data);
	}
	
	public void receivePlayerChallengeResponse (PlayerChallengeResponseData data) {
		server.getPlayerManager().playerResponded(data.getFrom(), data.getTo(), data.getAccepted());
		
		if (!data.getAccepted()) {
			sendPlayerChallengeResponse(data);
		}
	}
	
}

