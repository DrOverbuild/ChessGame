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
		Player to = server.getPlayerManager().playerById(data.getTo().getId());
		comms.sendToPlayer(to, data);
	}
	
	public void sendPlayerChallengeResponse(PlayerChallengeResponseData challengedata) {
		comms.sendToPlayer(challengedata.getFrom(), challengedata);
	}
	
	public void receivePlayerChallenge(PlayerChallengeData data) {
		Player to = server.getPlayerManager().playerById(data.getTo().getId());
		Player from = server.getPlayerManager().playerById(data.getFrom().getId());
		server.getPlayerManager().playerChallenged(from,to);
		sendPlayerChallenge(data);
	}
	
	public void receivePlayerChallengeResponse(PlayerChallengeResponseData data) {
		Player to = server.getPlayerManager().playerById(data.getTo().getId());
		Player from = server.getPlayerManager().playerById(data.getFrom().getId());
		server.getPlayerManager().playerResponded(from, to, data.getAccepted());
		
		if (!data.getAccepted()) {
			sendPlayerChallengeResponse(data);
		}
	}
	
}

