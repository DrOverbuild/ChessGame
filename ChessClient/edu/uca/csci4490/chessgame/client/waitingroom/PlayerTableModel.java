package edu.uca.csci4490.chessgame.client.waitingroom;

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
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player player = controller.getWaitingRoomPlayers().get(rowIndex);


		if (columnIndex == 0) {
			return player.getUsername();
		}

		if (columnIndex == 1) {
			boolean isChallenger = controller.getChallengers().contains(player);
			boolean isChallengee = controller.getChallengees().contains(player);

			if (isChallenger) {
				return "Challenged You";
			} else if (isChallengee) {
				return "Challenged";
			} else if (controller.getThisPlayer().equals(player)) {
				return "You";
			}
		}

		return "";
	}

	@Override
	public String getColumnName(int i) {
		if (i == 0) {
			return "Player";
		}

		return "";
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
