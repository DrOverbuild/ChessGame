package clientWaitingRoom;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class PlayerViewPanel extends JPanel {


	private JButton logoutBtn;
	private JButton acceptBtn;
	private JLabel firstPlaceImg;
	private JLabel secondPlaceImg;
	private JLabel thirdPlaceImg;
	
	import PlayerListPanel;
	
	
	public PlayerViewPanel(WaitingRoomController wrc) throws IOException {
		
		JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
		
		
		JPanel playerInfo = new JPanel(new GridLayout(3,1));
		JPanel playerName = new JPanel();
		JLabel playerUser = new JLabel("Player Name");
		playerName.add(playerUser);
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
		JLabel playerXPD = new JLabel("XP");
		JLabel playerWinsD = new JLabel("Wins");
		JLabel playerLossD = new JLabel("Losses");
		playerData.add(playerXPD);
		playerData.add(playerWinsD);
		playerData.add(playerLossD);
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
}
