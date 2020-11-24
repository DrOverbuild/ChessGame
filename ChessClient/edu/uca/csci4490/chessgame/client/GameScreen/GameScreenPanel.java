package edu.uca.csci4490.chessgame.client.GameScreen;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uca.csci4490.chessgame.model.Player;
public class GameScreenPanel extends JPanel {
	
	public GameScreenPanel() {
		
	// Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("Chess Game with " + Player.getUsername() , JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);
    
    JPanel grid = new JPanel(new GridLayout(8,8));
    
    //import the board class and make the board??
    //put the pieces on the board??
    
    //Create the buttons in the south
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JPanel viewButtons = new JPanel();
    JButton viewMovesButton = new JButton("View Moves");
    JButton viewCapturedButton = new JButton("View Captured Pieces");
    viewButtons.add(viewMovesButton);
    viewButtons.add(viewCapturedButton);
    buttonsPanel.add(viewButtons, BorderLayout.NORTH);
    JButton abandonButton = new JButton("Abandon Game");
    JPanel logoutButtonBuffer = new JPanel();
    logoutButtonBuffer.add(abandonButton);
    buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
    this.add(buttonsPanel, BorderLayout.SOUTH);
    
    
    
    
    
    
    
	}
	
}
