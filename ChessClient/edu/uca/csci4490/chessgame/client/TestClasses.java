package edu.uca.csci4490.chessgame.client;

import ocsf.client.AbstractClient;

import java.io.IOException;

public class TestClasses extends AbstractClient {
	public TestClasses(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object o) {
		System.out.println("Received");
		System.out.println(o);
		System.out.println();
	}

	public void start() {
		try {
			this.openConnection();
			this.sendToServer("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
