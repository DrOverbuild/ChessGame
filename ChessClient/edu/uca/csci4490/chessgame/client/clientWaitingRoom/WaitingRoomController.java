package clientWaitingRoom;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class WaitingRoomController implements ActionListener {

	// Private data fields for the container and chat client.
	private JPanel container;
	private ChatClient client;

	// Constructor for the login controller.
	public WaitingRoomController(JPanel container, ChatClient client)
	{
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The log out button takes the user back to the login screen
		// the card layout number might need to be changed
		if (command == "Log Out")
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Accept")
		{
		
			// not sure what specifically needs to happen here
		}

	// After the login is successful, set the User object and display the contacts screen.
	public void logOutSuccess()
	{
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);

		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "4");
	}

	// Method that displays a message in the error label.
	public void displayError(String error)
	{
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.setError(error);
	}
}

}
