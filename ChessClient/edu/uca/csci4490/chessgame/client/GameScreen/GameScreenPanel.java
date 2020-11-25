package edu.uca.csci4490.chessgame.client.GameScreen;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uca.csci4490.chessgame.model.Player;
import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;

public class GameScreenPanel extends JPanel {

    private JLabel status;

	public GameScreenPanel(GameScreenController controller) {
		
	// Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("Chess Game", JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);
    
    JPanel grid = new JPanel(new GridLayout(8,8));
    boolean dark = false;

    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; i++) {
            JButton pieceButton = new JButton();
            pieceButton.setActionCommand(i + "," + j);
            pieceButton.addActionListener(controller);
            grid.add(pieceButton);

            if (dark) {
                pieceButton.setBackground(Color.LIGHT_GRAY);
            }

            dark = !dark;
        }
    }

    //import the board class and make the board??
    //put the pieces on the board??
    
    //Create the buttons in the south
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JPanel viewButtons = new JPanel();

    JButton viewMovesButton = new JButton("View Moves");
    viewMovesButton.setActionCommand("View Moves");
    viewMovesButton.addActionListener(controller);

    JButton viewCapturedButton = new JButton("View Captured Pieces");
    viewCapturedButton.setActionCommand("View Captured Pieces");
    viewCapturedButton.addActionListener(controller);

    viewButtons.add(viewMovesButton);
    viewButtons.add(viewCapturedButton);
    buttonsPanel.add(viewButtons, BorderLayout.WEST);

    status = new JLabel("status", JLabel.CENTER);
    buttonsPanel.add(status, BorderLayout.CENTER);

    JButton abandonButton = new JButton("Abandon Game");
    abandonButton.setActionCommand("Abandon Game");
    abandonButton.addActionListener(controller);
    buttonsPanel.add(abandonButton, BorderLayout.EAST);

    this.add(buttonsPanel, BorderLayout.SOUTH);
	}

    public void updateAvailableMoves(ArrayList<Location> availableMoves) {
        // TODO Implement
    }

    public void updateGame(Game game) {
        // TODO implement
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void disableButtons() {
        // TODO implement
    }
}
