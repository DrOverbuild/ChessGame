package edu.uca.csci4490.chessgame.client.gamescreen;

import edu.uca.csci4490.chessgame.model.gamelogic.GameData;
import edu.uca.csci4490.chessgame.model.gamelogic.LocationData;
import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class GameScreenPanel extends JPanel {
	private static final Color LIGHT_GREY = Color.LIGHT_GRAY;
	private static final Color DARK_GREY = Color.GRAY;
	private static final Color RED = new Color(255, 152, 150);

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

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				JButton pieceButton = new JButton();
				pieceButton.setActionCommand(x + "," + y);
				pieceButton.addActionListener(controller);
				pieceButton.setOpaque(true);
				Border emptyBorder = BorderFactory.createEmptyBorder();
				pieceButton.setBorder(emptyBorder);
				grid.add(pieceButton);
				pieceButtons[x][y] = pieceButton;

				if (dark) {
					pieceButton.setBackground(GameScreenPanel.LIGHT_GREY);
				} else {
				    pieceButton.setBackground(GameScreenPanel.DARK_GREY);
                }

				dark = !dark;
			}
			dark = !dark;
		}

		add(grid, BorderLayout.CENTER);

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
        	JButton button = pieceButtons[loc.getX()][loc.getY()];
        	if (button.getIcon() != null) {
        		button.setBackground(GameScreenPanel.RED);
			} else {
				button.setIcon(scaleImage(icon, button.getBounds().width, button.getBounds().height));
			}
        }
	}

	public void updateGame(GameData game) {
		boolean dark = true;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Color color = dark ? GameScreenPanel.DARK_GREY : GameScreenPanel.LIGHT_GREY;
				updatePiece(x, y, game.getBoard().getPieceAt(x, y), color);
				dark = !dark;
			}
			dark = !dark;
		}
	}

	private void updatePiece(int x, int y, PieceData piece, Color color) {
		JButton pieceButton = pieceButtons[x][y];
		pieceButton.setBackground(color);

		if (piece == null) {
			pieceButton.setIcon(null);
		} else {
			String path = "res" + System.getProperty("file.separator") +
					piece.getImage() + "_" +
					piece.getColor().name().toLowerCase() + ".png";
			// ex - pawn_black.png
			ImageIcon icon = scaleImage(new ImageIcon(path),
					pieceButton.getBounds().width, pieceButton.getBounds().height);
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

	public void enableButtons() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				pieceButtons[x][y].setEnabled(true);
			}
		}
	}

	public void setToLeaveGame() {
		abandonButton.setText("Go to Waiting Room");
		abandonButton.setActionCommand("Waiting Room");
	}

	public void setToAbandonGame() {
		abandonButton.setText("Abandon Game");
		abandonButton.setActionCommand("Abandon Game");
	}

	public ImageIcon scaleImage(ImageIcon icon, int w, int h)
	{
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if(icon.getIconWidth() > w)
		{
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if(nh > h)
		{
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	}
}
