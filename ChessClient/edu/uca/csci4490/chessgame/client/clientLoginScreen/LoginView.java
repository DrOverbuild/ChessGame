package clientLoginScreen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginView extends JPanel{
	public LoginView() {
	}
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel errorText;
	
	 // Getter for the text in the username field.
	  public String getUsername()
	  {
	    return usernameField.getText();
	  }
	  
	  // Getter for the text in the password field.
	  public String getPassword()
	  {
	    return new String(passwordField.getPassword());
	  }
	  
	  // Setter for the error text.
	  public void setError(String error)
	  {
	    errorText.setText(error);
	  }

	// Constructor for the login panel.
	public void LoginView(LoginScreenController lc)
	{
		// Create the controller and set it in the chat client.
		//LoginControl controller = new LoginControl(container, client);
		//client.setLoginControl(controller);

		// Create a panel for the labels at the top of the GUI.
		// create space for the error text
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorText = new JLabel("", JLabel.CENTER);
		errorText.setForeground(Color.RED);
		
		// not included in original image drawn by jasper
//		JLabel instructionLabel = new JLabel("Enter your username and password to log in.", JLabel.CENTER);
//		labelPanel.add(errorText);
//		labelPanel.add(instructionLabel);

		// Create a panel for the login information form.
		JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
		usernameField = new JTextField(10);
		JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
		passwordField = new JPasswordField(10);
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);

		// Create a panel for the buttons.
		JPanel buttonPanel = new JPanel();
		JButton createAccountBtn = new JButton("Create Account");
		createAccountBtn.addActionListener(lc);
		JButton signInBtn = new JButton("Sign In");
		signInBtn.addActionListener(lc);    
		buttonPanel.add(signInBtn);
		buttonPanel.add(signInBtn);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(labelPanel);
		grid.add(loginPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}
}

