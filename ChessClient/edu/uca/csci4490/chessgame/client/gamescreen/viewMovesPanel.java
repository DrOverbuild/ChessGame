 package edu.uca.csci4490.chessgame.client.gamescreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import edu.uca.csci4490.chessgame.model.gamelogic.MoveData;
import edu.uca.csci4490.chessgame.model.gamelogic.PieceData;

public class viewMovesPanel extends JFrame{
	
	JTextArea textarea;
	
	public viewMovesPanel(GameScreenController controller) {
		
		
	JPanel panel = new JPanel();
//	this.add(panel);
	this.setLayout(new BorderLayout());
	
	JPanel gridstatus = new JPanel(new GridLayout(1,1));
//	add(gridstatus, BorderLayout.NORTH);
	
	JLabel yourCaptures = new JLabel("Moves", JLabel.CENTER);
	add(yourCaptures, BorderLayout.NORTH);
	
//	JPanel list = new JPanel(new GridLayout(8, 4));
//	add(list, BorderLayout.CENTER);
	
	textarea = new JTextArea();
	
	add(new JScrollPane(textarea), BorderLayout.CENTER);
	
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.setMinimumSize(new Dimension(400, 800));
	this.pack();
	this.setVisible(true);
	
	}
	
	public void updateMoves(ArrayList<MoveData> moveData) {
		textarea.setText("");
		for (MoveData i : moveData) {
			//String who = i.getPiece();
			int fromx = (int)i.getFrom().getX();
			int fromy = (int)i.getFrom().getY();
			int tox = (int)i.getTo().getX();
			int toy = (int)i.getTo().getY();
			
			textarea.setText(textarea.getText() + xcoor(fromx) + ycoor(fromy) 
			+ " moved to " + xcoor(tox) + ycoor(toy) + "\n");
		}
	}
	
	public String xcoor(int i) {
		
		String x = ("");
		if (i == 0) {
			x = "A";
		}
		else if (i == 1) {
			x = "B";
		}
		else if (i == 2) {
			x = "C";
		}
		else if (i == 3) {
			x = "D";
		}
		else if (i == 4) {
			x = "E";
		}
		else if (i == 5) {
			x = "F";
		}
		else if (i == 6) {
			x = "G";
		}
		else if (i == 7) {
			x = "H";
		}
		
		return x;
	}
	
public String ycoor(int i) {
		
		String x = ("");
		if (i == 0) {
			x = "8";
		}
		else if (i == 1) {
			x = "7";
		}
		else if (i == 2) {
			x = "6";
		}
		else if (i == 3) {
			x = "5";
		}
		else if (i == 4) {
			x = "4";
		}
		else if (i == 5) {
			x = "3";
		}
		else if (i == 6) {
			x = "2";
		}
		else if (i == 7) {
			x = "1";
		}
		
		return x;
	}
	
	public static void main(String[] args) {
		viewMovesPanel panel = new viewMovesPanel(null);
		panel.updateMoves(null);
	}
}
