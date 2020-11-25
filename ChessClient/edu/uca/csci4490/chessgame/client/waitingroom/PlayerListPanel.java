package edu.uca.csci4490.chessgame.client.waitingroom;

import javax.swing.*;
import java.awt.*;

public class PlayerListPanel extends JPanel{
	private JTable mainTable;
	private PlayerTableModel model;

	public PlayerListPanel(WaitingRoomController controller) {
		setLayout(new BorderLayout(0,0));

		JScrollPane scrollPane = buildTable(controller);

		// add to panel
		add(scrollPane, BorderLayout.CENTER);
	}

	public JScrollPane buildTable(WaitingRoomController controller) {
		// set up main table
		model = new PlayerTableModel(controller);
		mainTable = new JTable(model);

		// row selection behavior
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.getSelectionModel().addListSelectionListener(controller); // calls valueChanged() when a row is selected

		return new JScrollPane(mainTable);
	}

	public void updatePlayers() {
		model.fireTableDataChanged();
	}
}
