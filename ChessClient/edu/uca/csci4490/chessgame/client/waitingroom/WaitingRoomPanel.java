package edu.uca.csci4490.chessgame.client.waitingroom;

import javax.swing.*;
import java.awt.*;

public class WaitingRoomPanel extends JPanel {
	public WaitingRoomPanel(WaitingRoomController controller) {
		this.setLayout(new BorderLayout());

		this.add(controller.getListPanel(), BorderLayout.CENTER);
		this.add(controller.getDetailPanel(), BorderLayout.EAST);
	}
}
