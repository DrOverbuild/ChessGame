package edu.uca.csci4490.chessgame.server.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.*;
import edu.uca.csci4490.chessgame.server.ChessServer;
import edu.uca.csci4490.chessgame.server.playermanager.PlayerManager;

public class WaitingRoomCommunication {
	
	private ChessServer server;
	
	public void sendWaitingRoomToAll () {
		Set<Player> players = server.getPlayerManager().getWaitingRoom();
		
		players.forEach(System.out::println);
		ArrayList <Player> playerlist = new ArrayList<>(players);
		WaitingRoomData data = new WaitingRoomData(playerlist);
		for(Player p: players) {
			try {
				p.getClient().sendToClient(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendPlayerChallenge (PlayerChallengeData data) {
		try {
			data.getTo().getClient().sendToClient(data);
		} catch (IOException e) {
			System.out.println("WARNING - Failed to send challenge to player");
			e.printStackTrace();
		}
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

