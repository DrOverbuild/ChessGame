package edu.uca.csci4490.chessgame.model.data;

import java.io.Serializable;

public class CreateAccountData implements Serializable {
	private String username;
	private String password;

	public CreateAccountData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}