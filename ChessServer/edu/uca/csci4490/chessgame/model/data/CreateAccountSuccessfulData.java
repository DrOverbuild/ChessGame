package edu.uca.csci4490.chessgame.model.data;

import ocsf.server.ConnectionToClient;

public class CreateAccountSuccessfulData {
	
	private ConnectionToClient client;
	
	public CreateAccountSuccessfulData(ConnectionToClient client) {
		this.setClient(client);
	}
	
	public ConnectionToClient getClient() {
		return client;
	}

	public void setClient(ConnectionToClient client) {
		this.client = client;
	}
	
}
