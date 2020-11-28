package edu.uca.csci4490.chessgame.client.gamescreen;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import edu.uca.csci4490.chessgame.model.gamelogic.*;
import javax.swing.*;
import javax.swing.border.Border;

public class capturedPiecesPanel extends JFrame {
	private static final Color LIGHT_GREY = Color.LIGHT_GRAY;
	private static final Color DARK_GREY = Color.GRAY;
	private GameScreenController gc; 
	private JLabel status;
	private JLabel[][] pieceLabels = new JLabel[4][8];
	
	public capturedPiecesPanel(GameScreenController controller) {
		
		
		JPanel panel = new JPanel();
		this.add(panel);
		this.setLayout(new BorderLayout());
		
		JPanel gridstatus = new JPanel(new GridLayout(1, 2));
		add(gridstatus, BorderLayout.NORTH);
		
		JLabel yourCaptures = new JLabel("Your captures", JLabel.CENTER);
		gridstatus.add(yourCaptures);
		JLabel theirCaptures = new JLabel("Their captures", JLabel.CENTER);
		gridstatus.add(theirCaptures);
		
		JPanel grid = new JPanel(new GridLayout(8, 4));
		add(grid, BorderLayout.CENTER);
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 4; x++) {
				pieceLabels[x][y] = new JLabel();
				grid.add(pieceLabels[x][y]);
			}
		}
		
		
		
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 800));
		this.pack();
		this.setVisible(true);
	}
	
	public void updateCaptured(
			edu.uca.csci4490.chessgame.model.gamelogic.Color thisColor,
			GameData game) {
	
		ArrayList<PieceData> capturedPieces = game.getCapturedPieces();
		Queue<PieceData> thisPlayersPieces = new LinkedList<PieceData>();
		Queue<PieceData> opponentsPieces = new LinkedList<PieceData>();

		for (PieceData i : capturedPieces) {
			if(i.getColor().equals(thisColor)) {
				opponentsPieces.add(i);
			}
			else {
				thisPlayersPieces.add(i);
			}
		}
		
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 2; x++) {
				PieceData piece = thisPlayersPieces.poll();
				JLabel label = pieceLabels[x][y];
				
				if (piece == null) {
					label.setIcon(null);
				} else {
					String path = "res" + System.getProperty("file.separator") +
							piece.getImage() + "_" +
							piece.getColor().name().toLowerCase() + ".png";
					ImageIcon icon = scaleImage(new ImageIcon(path),
							label.getBounds().width - 20, label.getBounds().height - 20);
					label.setIcon(icon);
				}
			}
		}
		
		for (int y = 0; y < 8; y++) {
			for (int x = 2; x < 4; x++) {
				PieceData piece = opponentsPieces.poll();
				JLabel labell = pieceLabels[x][y];
				
				if (piece == null) {
					labell.setIcon(null);
				} else {
					String path = "res" + System.getProperty("file.separator") +
							piece.getImage() + "_" +
							piece.getColor().name().toLowerCase() + ".png";
					ImageIcon icon = scaleImage(new ImageIcon(path),
							labell.getBounds().width - 10, labell.getBounds().height - 10);
					labell.setIcon(icon);
				}
			}
		}
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
	
	public static void main(String[] args) {
		new capturedPiecesPanel(null);
	}
	
}
