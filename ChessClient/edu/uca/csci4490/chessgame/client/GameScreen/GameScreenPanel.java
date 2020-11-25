package edu.uca.csci4490.chessgame.client.GameScreen;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import edu.uca.csci4490.chessgame.model.gamelogic.Game;
import edu.uca.csci4490.chessgame.model.gamelogic.Location;
import edu.uca.csci4490.chessgame.model.gamelogic.piece.Piece;

public class GameScreenPanel extends JPanel {

    private JLabel status;
    private JButton[][] pieceButtons;

	public GameScreenPanel(GameScreenController controller) {
		
	// Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("Chess Game", JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);

    // setup grid
    JPanel grid = new JPanel(new GridLayout(8,8));
    boolean dark = false;

    for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
            JButton pieceButton = new JButton();
            pieceButton.setActionCommand(x + "," + y);
            pieceButton.addActionListener(controller);
            grid.add(pieceButton);
            pieceButtons[x][y] = pieceButton;

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
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                updatePiece(x, y, game.getBoard().getPieceAt(x, y));
            }
        }
    }

    private void updatePiece(int x, int y, Piece piece) {
        JButton pieceButton = pieceButtons[x][y];

        if (piece == null) {
	        pieceButton.setIcon(null);
        } else {
            String path = "res" + System.getProperty("file.separator") +
                    piece.getImage() + "_" +
                    piece.getColor().name().toLowerCase() + ".png";
            // ex - pawn_black.png
            ImageIcon icon = new ImageIcon(path);
            pieceButton.setIcon(icon);
        }
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void disableButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                pieceButtons[x][y].setEnabled(false);
            }
        }
    }
}
