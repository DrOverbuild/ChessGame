package edu.uca.csci4490.chessgame.client.loginscreen;

import javax.swing.*;
import java.awt.*;


public class LoginView extends JPanel {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel errorText;

	private JButton signInBtn;
	private JButton createAccountBtn;

	// Getter for the text in the username field.
	public String getUsername() {
		return usernameField.getText();
	}

	// Getter for the text in the password field.
	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	// Setter for the error text.
	public void setError(String error) {
		errorText.setText(error);
	}

	// Constructor for the login panel.
	public LoginView(LoginScreenController lc) {
		// Create a panel for the labels at the top of the GUI.
		// create space for the error text
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		errorText = new JLabel("", JLabel.CENTER);
		errorText.setForeground(Color.RED);

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
		createAccountBtn = new JButton("Create Account");
		createAccountBtn.addActionListener(lc);
		createAccountBtn.setActionCommand("Create Account");
		signInBtn = new JButton("Sign In");
		signInBtn.addActionListener(lc);
		signInBtn.setActionCommand("Sign In");
		buttonPanel.add(signInBtn);
		buttonPanel.add(signInBtn);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(labelPanel);
		grid.add(loginPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}

	public void disableButtons() {
		createAccountBtn.setEnabled(false);
		signInBtn.setEnabled(false);
	}

	public void enableButtons() {
		createAccountBtn.setEnabled(true);
		signInBtn.setEnabled(true);
	}
}

