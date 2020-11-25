package edu.uca.csci4490.chessgame.client.communication;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.model.data.ErrorData;
import edu.uca.csci4490.chessgame.model.data.WaitingRoomData;
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
		if (o instanceof ErrorData) {
			client.getLc().receiveError((ErrorData)o);
		}

		if (o instanceof WaitingRoomData) {
			if (client.getCurrentPanel().equals(ChessClient.LOGIN_PANEL)) {
				client.getLc().receiveWaitingRoom((WaitingRoomData)o);
			} else if (client.getCurrentPanel().equals(ChessClient.WAITING_ROOM_PANEL)) {
				client.getWc().receiveWaitingRoom((WaitingRoomData)o);
			}
		}

		if (o instanceof CreateAccountSuccessData) {
			client.getCc().receiveCreateAccountSuccess();
		}

		if (o instanceof CreateAccountUnsuccessfulData) {
			client.getCc().receiveCreateAccountUnsuccessful();
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

	// public void setLoginControl(LoginControl lc) {
//	this.lc = lc;
//}
//
//public void setCreateAccountControl(CreateAccountControl cc) {
//	this.cc = cc;
//}
//	public void setWaitingRoomControl(WaitingRoomControl wc) {
//		this.wc = wc;
//	}
//	public void setGameRoomControl(GameRoomControl gc) {
//		this.gc = gc;
//	}

}
