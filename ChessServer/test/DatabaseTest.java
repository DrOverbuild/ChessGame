import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.data.CreateAccountData;
import edu.uca.csci4490.chessgame.model.data.PlayerLoginData;
import edu.uca.csci4490.chessgame.server.database.Database;
import edu.uca.csci4490.chessgame.server.database.UserAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class DatabaseTest {
	Database db;

	String username = "testUser2";
	String password = "testPass2";

	@org.junit.Before
	public void setUp() throws Exception {
		db = new Database();
	}

	@org.junit.Test
	public void createAccount() {
		CreateAccountData data = new CreateAccountData(username, password);
		try {
			db.createAccount(data);
		} catch (UserAlreadyExistsException | SQLException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void authenticatePlayer() {
		CreateAccountData data = new CreateAccountData(username + "auth", password);
		try {
			db.createAccount(data);
		} catch (UserAlreadyExistsException | SQLException e) {
			// do nothing, this is just to make sure the account is created first
		}

		PlayerLoginData pldata = new PlayerLoginData(username + "auth", password);

		Player p = db.authenticatePlayer(pldata, null);

		assertNotNull(p);
	}

	@org.junit.Test
	public void updatePlayerData() {
		Random r = new Random();
		int wins = r.nextInt(100);
		int losses = r.nextInt(100);
		int xp = r.nextInt(4000);

		CreateAccountData data = new CreateAccountData(username + "up", password);
		try {
			db.createAccount(data);
		} catch (UserAlreadyExistsException | SQLException e) {
			// do nothing, this is just to make sure the account is created first
		}

		PlayerLoginData pldata = new PlayerLoginData(username + "up", password);

		Player p = db.authenticatePlayer(pldata, null);
		p.setLosses(losses);
		p.setWins(wins);
		p.setXp(xp);

		db.updatePlayerData(p);

		Player newPlayer = db.authenticatePlayer(pldata, null);

		assertEquals(newPlayer.getLosses(), losses);
		assertEquals(newPlayer.getWins(), wins);
		assertEquals(newPlayer.getXp(), xp);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void createPlayerAgain() throws UserAlreadyExistsException, SQLException {
		CreateAccountData data = new CreateAccountData(username + "a", password);
		db.createAccount(data);
		db.createAccount(data);
	}
}