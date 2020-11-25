package edu.uca.csci4490.chessgame.client.communication;

import ocsf.client.AbstractClient;

import java.io.IOException;

public class ChessClientCommunication extends AbstractClient {

	//private LoginControl lc;
	//private CreateAccountController cc;
	//private CreateAccountData cd;
	//private WaitingRoomController wc;
	//private GameRoomController gc;

	public ChessClientCommunication() {
		super("localhost", 8300);

	}

	@Override
	protected void handleMessageFromServer(Object arg0) {


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
