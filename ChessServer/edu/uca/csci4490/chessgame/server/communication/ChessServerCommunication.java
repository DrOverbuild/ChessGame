package edu.uca.csci4490.chessgame.server.communication;

import java.io.IOException;
import java.sql.SQLException;

import lab7out.CreateAccountData;
import lab7out.Error;
import lab7out.LoginData;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ChessServerCommunication extends AbstractServer {
	
	//Data fields for Chess Server
	//private PlayerLoginCommunication;
	//private WaitingRoomCommuniciation;
	//private GameCommunication;
	
	public ChessServerCommunication(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object o, ConnectionToClient connectionToClient) {
		{
			// If we received LoginData, verify the account information.
			if (o instanceof LoginData)
			{
				// Check the username and password with the database.
				LoginData data = (LoginData)o;
				Object result = "result";
				try {
					if (database.verifyAccount(data.getUsername(), data.getPassword()))
					{
						result = "LoginSuccessful";
						log.append("Client " + connectionToClient.getId() + " successfully logged in as " + data.getUsername() + "\n");
					}
					else
					{
						result = new Error("The username and password are incorrect.", "Login");
						log.append("Client " + connectionToClient.getId() + " failed to log in\n");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Send the result to the client.
				try
				{
					connectionToClient.sendToClient(result);
				}
				catch (IOException e)
				{
					return;
				}
			}

			// If we received CreateAccountData, create a new account.
			else if (o instanceof CreateAccountData)
			{
				// Try to create the account.
				CreateAccountData data = (CreateAccountData)o;
				Object result = null;
				try {
					if (database.createNewAccount(data.getUsername(), data.getPassword()))
					{
						result = "CreateAccountSuccessful";
						log.append("Client " + connectionToClient.getId() + " created a new account called " + data.getUsername() + "\n");
					}
					else
					{
						result = new Error("The username is already in use.", "CreateAccount");
						log.append("Client " + connectionToClient.getId() + " failed to create a new account\n");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Send the result to the client.
				try
				{
					connectionToClient.sendToClient(result);
				}
				catch (IOException e)
				{
					return;
				}  	
			}
	
	public void listeningException(Throwable exception) {
		System.out.println("Listening exception: " + exception.getMessage() + "\n");
	}
	
	public void serverStarted(){
		System.out.println("Server Started\n");
	}
	
	public void serverStopped(){
		System.out.println("Server Stopped\n");
	}
	
	public void serverClosed(){
		System.out.println("Server Closed\n");
	}
	
	public void clientConnected(ConnectionToClient client){
		System.out.println("Client " + client.getId() + " connected\n");
	}
	
	public void setPlayerLoginCommunication(){
		
	}
	
	public void getPlayerLoginCommunication(){
		
	}
	public void setWaitingRoomCommunication(){
		
	}
	
	public void getWaitingRoomCommunication(){
	
	}
	
	public void setGameCommunication() {
		
	}
	
	public void getGameCommunication() {
		
	}
	
	
	
}









//.