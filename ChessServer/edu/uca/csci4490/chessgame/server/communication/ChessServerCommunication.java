package edu.uca.csci4490.chessgame.server.communication;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServerCommunication extends AbstractServer {
	public ChessServerCommunication(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient connectionToClient) {

	}
}
