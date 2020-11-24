package edu.uca.csci4490.chessgame.client.clientWaitingRoom;

import edu.uca.csci4490.chessgame.model.Player;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class PlayerViewPanel extends JPanel {


	private JButton logoutBtn;
	private JButton acceptBtn;
	private JLabel firstPlaceImg;
	private JLabel secondPlaceImg;
	private JLabel thirdPlaceImg;


	private JLabel usernameLabel;
	private JLabel xpDataLabel;
	private JLabel winsDataLabel;
	private JLabel lossesDataLabel;

	public PlayerViewPanel(WaitingRoomController wrc) {
		
		JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
		
		
		JPanel playerInfo = new JPanel(new GridLayout(3,1));
		JPanel playerName = new JPanel();
		usernameLabel = new JLabel("Player Name");
		playerName.add(usernameLabel);
		playerInfo.add(playerName);
		
		JPanel playerStats = new JPanel(new GridLayout(1,3));
		JLabel playerXPL = new JLabel("XP");
		JLabel playerWinsL = new JLabel("Wins");
		JLabel playerLossL = new JLabel("Losses");
		playerStats.add(playerXPL);
		playerStats.add(playerWinsL);
		playerStats.add(playerLossL);
		playerInfo.add(playerStats);
		
		// panel for player data
		JPanel playerData = new JPanel(new GridLayout(1,3));
		xpDataLabel = new JLabel("XP");
		winsDataLabel = new JLabel("Wins");
		lossesDataLabel = new JLabel("Losses");
		playerData.add(xpDataLabel);
		playerData.add(winsDataLabel);
		playerData.add(lossesDataLabel);
		playerInfo.add(playerData);
		
		panel.add(playerInfo);
		
		

		
		JPanel winnerArea = new JPanel(new GridLayout(2, 1));
		JPanel winnerPanel = new JPanel();
		JLabel winnerText = new JLabel("Top Players (Wins)");
		winnerPanel.add(winnerText);
		winnerArea.add(winnerPanel);
		panel.add(winnerArea);
		
		JPanel imagePanel = new JPanel(new GridLayout(1, 3));
		JLabel firstplaceImg = new JLabel("First Place");
		JLabel secondplaceImg = new JLabel("Second Place");
		JLabel thirdplaceImg = new JLabel("Third Place");
		imagePanel.add(firstplaceImg);
		imagePanel.add(secondplaceImg);
		imagePanel.add(thirdplaceImg);
		winnerArea.add(imagePanel);
		panel.add(winnerArea);
		
		
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton logoutBtn = new JButton("Log Out");
		logoutBtn.setSize(10,10);
	    logoutBtn.addActionListener(wrc);
	    JButton acceptBtn = new JButton("Accept");
	    acceptBtn.addActionListener(wrc);    
	    buttonPanel.add(logoutBtn);
	    buttonPanel.add(acceptBtn);
	    panel.add(buttonPanel);
		
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setRightComponent(panel);
		add(splitPane);
	}

	public void setPlayer(Player player) {
		usernameLabel.setText(player.getUsername());
		xpDataLabel.setText(player.getXp() + "");
		winsDataLabel.setText(player.getWins() + "");
		lossesDataLabel.setText(player.getLosses() + "");
	}
}
