package edu.uca.csci4490.chessgame.client.communication;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.model.data.*;
import ocsf.client.AbstractClient;

import java.io.IOException;

public class ChessClientCommunication extends AbstractClient {

	private ChessClient client;

	public ChessClientCommunication(ChessClient client) {
		super("localhost", 8300);

		this.client = client;
	}

	@Override
	protected void handleMessageFromServer(Object o) {
		try {
			if (o instanceof ErrorData) {
				if (client.getCurrentPanel().equals(ChessClient.LOGIN_PANEL)) {
					client.getLc().receiveError((ErrorData) o);
				} else if (client.getCurrentPanel().equals(ChessClient.CREATE_ACCOUNT_PANEL)) {
					client.getCc().receiveCreateAccountUnsuccessful();
				}
			} else if (o instanceof WaitingRoomData) {
				if (client.getCurrentPanel().equals(ChessClient.LOGIN_PANEL)) {
					client.getLc().receiveWaitingRoom((WaitingRoomData) o);
				} else if (client.getCurrentPanel().equals(ChessClient.WAITING_ROOM_PANEL)) {
					client.getWc().receiveWaitingRoom((WaitingRoomData) o);
				}
			} else if (o instanceof CreateAccountSuccessfulData) {
				client.getCc().receiveCreateAccountSuccess();
			} else if (o instanceof PlayerChallengeData) {
				client.getWc().receivePlayerChallenge((PlayerChallengeData) o);
			} else if (o instanceof PlayerChallengeResponseData) {
				client.getWc().receivePlayerChallengeResponse((PlayerChallengeResponseData) o);
			} else if (o instanceof StartOfGameData) {
				client.getWc().receiveStartOfGame((StartOfGameData) o);
			} else if (o instanceof AvailableMovesData) {
				client.getGc().receiveAvailableMoves((AvailableMovesData) o);
			} else if (o instanceof NextTurnData) {
				client.getGc().receiveNextTurn((NextTurnData) o);
			} else if (o instanceof EndOfGameData) {
				client.getGc().receiveEndOfGame((EndOfGameData) o);
			}
		} catch (Exception e) {
			System.out.println("Error processing received data");
			e.printStackTrace();
		}
	}

	public void connectionException(Throwable exception) {
		System.out.println("Connection Exception Occured");
		System.out.println("\n " + exception.getMessage());
		System.out.println("\n " + exception);
	}

	public void connectionEstablished() {
		System.out.println("Client Connected");
	}

	public void send(Object msg) {
		try {
			this.sendToServer(msg);
		} catch (IOException e) {
			System.out.println("FATAL - Lost connection to server attempting to send " + msg.getClass().getName());
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
