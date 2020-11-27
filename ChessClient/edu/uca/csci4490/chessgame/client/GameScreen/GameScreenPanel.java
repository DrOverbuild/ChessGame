package edu.uca.csci4490.chessgame.client.GameScreen;

import edu.uca.csci4490.chessgame.model.gamelogic.GameData;
import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;
import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreenPanel extends JPanel {

	private JLabel status;
	private JButton[][] pieceButtons = new JButton[8][8];
	private JButton abandonButton;

	public GameScreenPanel(GameScreenController controller) {

		// Use BorderLayout to lay out the components in this panel.
		this.setLayout(new BorderLayout());

		// Create the contacts label in the north.
		JLabel label = new JLabel("Chess Game", JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);

		// setup grid
		JPanel grid = new JPanel(new GridLayout(8, 8));
		boolean dark = false;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				JButton pieceButton = new JButton();
				pieceButton.setActionCommand(x + "," + y);
				pieceButton.addActionListener(controller);
				grid.add(pieceButton);
				pieceButtons[x][y] = pieceButton;

				if (dark) {
					pieceButton.setBackground(Color.DARK_GRAY);
				} else {
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

		abandonButton = new JButton("Abandon Game");
		abandonButton.setActionCommand("Abandon Game");
		abandonButton.addActionListener(controller);
		buttonsPanel.add(abandonButton, BorderLayout.EAST);

		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void updateAvailableMoves(PieceData piece, ArrayList<LocationData> availableMoves) {
        String path = "res" + System.getProperty("file.separator") +
                piece.getImage() + "_outline.png";
        ImageIcon icon = new ImageIcon(path);

        for (LocationData loc: availableMoves) {
            pieceButtons[loc.getX()][loc.getY()].setIcon(icon);
        }
	}

	public void updateGame(GameData game) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				updatePiece(x, y, game.getBoard().getPieceAt(x, y));
			}
		}
	}

	private void updatePiece(int x, int y, PieceData piece) {
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

	public void setToLeaveGame() {
		abandonButton.setText("Go to Waiting Room");
		abandonButton.setActionCommand("Waiting Room");
	}
}
