package edu.uca.csci4490.chessgame.client.clientWaitingRoom;

import edu.uca.csci4490.chessgame.model.Player;

import javax.swing.table.AbstractTableModel;

public class PlayerTableModel extends AbstractTableModel {
	private WaitingRoomController controller;

	public PlayerTableModel(WaitingRoomController controller) {
		this.controller = controller;
	}

	@Override
	public int getRowCount() {
		return controller.getWaitingRoomPlayers().size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player player = controller.getWaitingRoomPlayers().get(rowIndex);

		if (columnIndex == 0) {
			return player.getUsername();
		}

		if (columnIndex == 1) {
			return player.getXp();
		}

		if (columnIndex == 2) {
			return player.getWins();
		}

		if (columnIndex == 3) {
			return player.getLosses();
		}

		return "";
	}

	@Override
	public String getColumnName(int i) {
		switch (i) {
			case 0:
				return "Player";
			case 1:
				return "XP";
			case 2:
				return "Wins";
			case 3:
				return "Losses";
			default:
				return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
