package edu.uca.csci4490.chessgame.server.database;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;
import ocsf.server.ConnectionToClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {
	public static final String filename = "db.properties";

	private Connection connection;
	private String encryptionKey;

	public Database() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(filename));
			String url = props.getProperty("url");
			encryptionKey = props.getProperty("enc_key");
			connection = DriverManager.getConnection(url, props);
		} catch (IOException e) {
			System.out.println("Fatal: unable to read db.properties.");
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("Fatal: unable to establish MySQL connection");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void createAccount(CreateAccountData data) throws UserAlreadyExistsException, SQLException {
		// Check if exists
		PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM players WHERE username = ?;");
		stmt.setString(1, data.getUsername());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		if (count > 0) {
			throw new UserAlreadyExistsException();
		}

		// now that we know user doesn't already exist, let's add the user
		stmt = connection.prepareStatement("INSERT INTO players (username, password) VALUES (?, aes_encrypt(?,?));");
		stmt.setString(1, data.getUsername());
		stmt.setString(2, data.getPassword());
		stmt.setString(3, this.encryptionKey);
		stmt.executeUpdate();
	}

	public Player authenticatePlayer(PlayerLoginData data, ConnectionToClient client) {
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT id,xp,wins,losses FROM players WHERE username = ? AND password = aes_encrypt(?,?);");
			stmt.setString(1, data.getUsername());
			stmt.setString(2, data.getPassword());
			stmt.setString(3, this.encryptionKey);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) { // if no player with username/password combo exists, this will be false
				Player p = new Player();
				p.setUsername(data.getUsername());
				p.setId(rs.getInt(1));
				p.setXp(rs.getInt(2));
				p.setWins(rs.getInt(3));
				p.setLosses(rs.getInt(4));
				p.setClient(client);
				return p;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return null;
	}

	public void updatePlayerData(Player player) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE players SET xp = ?, wins = ?, losses = ? WHERE id = ?;");
			stmt.setInt(1, player.getXp());
			stmt.setInt(2, player.getWins());
			stmt.setInt(3, player.getLosses());
			stmt.setInt(4, player.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Implement
	}

	// quick test
	public static void main(String[] args) {
		Database db = new Database();

		try {
			db.createAccount(new CreateAccountData("thisisausername","yoooooo1"));
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		try {
			db.createAccount(new CreateAccountData("thisisausername","yoo111"));
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		Player p = db.authenticatePlayer(new PlayerLoginData("thisisausername", "yoooooo1"), null);

		System.out.println(p);

		p.setWins(1000);

		db.updatePlayerData(p);
	}
}
