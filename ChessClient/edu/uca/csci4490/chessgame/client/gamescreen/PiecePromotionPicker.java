package edu.uca.csci4490.chessgame.client.gamescreen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PiecePromotionPicker extends JFrame implements ActionListener {
	private GameScreenController gc;

	public PiecePromotionPicker(GameScreenController gc, String color) throws HeadlessException {
		this.gc = gc;

		setTitle("Promote Piece");
		setLayout(new GridLayout(1,4));

		String[] pieceImgs = new String[] {
				"queen",
				"bishop",
				"knight",
				"rook"
		};

		for (String img: pieceImgs) {
			JButton button = new JButton();
			button.setOpaque(true);
			Border emptyBorder = BorderFactory.createEmptyBorder();
			button.setBorder(emptyBorder);
			button.setBackground(Color.GRAY);
			button.setMinimumSize(new Dimension(80,80));
			String path = "res" + System.getProperty("file.separator")
					+ img + "_" + color.toLowerCase() + ".png";
			// ex - pawn_black.png
			ImageIcon icon = scaleImage(new ImageIcon(path),
					50, 50);
			button.setIcon(icon);
			button.addActionListener(this);
			button.setActionCommand(img);
			add(button);
		}

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(320, 100));
		this.pack();
		this.setVisible(true);
	}

	public ImageIcon scaleImage(ImageIcon icon, int w, int h) {
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

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
	}

	public static void main(String[] args) {
		new PiecePromotionPicker(null,"white");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gc.piecePromoted(e.getActionCommand());
	}
}
