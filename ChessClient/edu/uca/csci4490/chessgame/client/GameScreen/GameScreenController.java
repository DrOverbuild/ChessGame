package edu.uca.csci4490.chessgame.client.GameScreen;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import edu.uca.csci4490.chessgame.client.ChessClient;
import edu.uca.csci4490.chessgame.model.data.AvailableMovesData;
import edu.uca.csci4490.chessgame.model.data.EndOfGameData;
import edu.uca.csci4490.chessgame.model.data.NextTurnData;
import edu.uca.csci4490.chessgame.model.data.PIeceSelectionData;
import edu.uca.csci4490.chessgame.model.data.PieceMoveData;

public class GameScreenController implements ActionListener {
	
	// Private data fields for the container and chat client.
	  private JPanel container;
	  private ChessClient client;
	  
    public void GameScreenControl(JPanel container, ChessClient client)
	  {
	    this.container = container;
	    this.client = client;
	  }

	public void receiveAvailableMoves(AvailableMovesData data){
		
	}
	
	public void receiveNextTurn(NextTurnData data) {
		
	}
	
	public void receiveEndOfGame(EndOfGameData data) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get the name of the button clicked.
	    String command = ae.getActionCommand();
	    
		if (command == "Abandon Game") {
			 {
			      CardLayout cardLayout = (CardLayout)container.getLayout();
			      cardLayout.show(container, "1");
			 }
		}
		if (command == "View Moves") {
			
		}
		if (command == "View Captured Pieces") {
			
		}
	}
	
	public void sendPieceSelection(PIeceSelectionData data) {
		
	}
	
	public void sendPieceMove(PieceMoveData data) {
		
	}

	
	
}
