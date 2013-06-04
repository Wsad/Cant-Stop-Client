package alex.clientgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class StartPanel extends JFrame implements ActionListener{
	private JButton createUser;
	private JButton logIn;
	private JLabel error;
	private JTextField usernameIn;
	private JTextField passwordIn;
	private Container contentPane;
	
	public StartPanel(Container contentPaneIn){
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		
		this.setLayout(new FlowLayout());
		JPanel all = new JPanel();		//holds all components
		JPanel container = new JPanel();
		JPanel UI = new JPanel();
		UI.setLayout(new BoxLayout(UI,BoxLayout.PAGE_AXIS));
		
		JPanel inputbox = new JPanel();
		JPanel passBox = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JLabel username = new JLabel("Username:");
		JLabel password = new JLabel("Password:");
		usernameIn = new JTextField();
		passwordIn = new JPasswordField();
		
		usernameIn.setPreferredSize(new Dimension(150,20));
		passwordIn.setPreferredSize(new Dimension(150,20));
		
		createUser = new JButton("Create New Account");
		logIn = new JButton("Log In");
		logIn.addActionListener(this);
		
		passBox.add(password);
		passBox.add(passwordIn);
		passBox.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		inputbox.add(username);
		inputbox.add(usernameIn);
		
		buttonPanel.add(createUser);
		buttonPanel.add(logIn);
		
		error = new JLabel("");
		UI.add(error);
		UI.add(inputbox);
		UI.add(passBox);
		UI.add(buttonPanel);
		UI.setBorder(BorderFactory.createEmptyBorder(15,15,15,25));
		
		
		UI.setPreferredSize(new Dimension(300,160));
		UI.setMaximumSize(new Dimension (300,160));
		
		container.add(UI);
		container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Welcome!", TitledBorder.TOP, TitledBorder.CENTER));
	
		all.add(container);
		all.setBorder(BorderFactory.createEmptyBorder(150,90,90,90));
		all.setPreferredSize(new Dimension(500,500));
		all.setMaximumSize(new Dimension (500,500));
		
		contentPaneIn.add(all);
		contentPaneIn.revalidate();
		
				
	}
	
	public void actionPerformed(ActionEvent e){
Object source = e.getSource();
		
		if (source == logIn){
			//send returning user name string to server
			//receive server response 
			//if response passes
				//send password to server
				//if password passes
					MainMenuFrame mmf = new MainMenuFrame(contentPane);
					this.setVisible(false);
					this.dispose();
				//else
					//update error message
					//increase login attempts
					//if attempts ==3
						//close connection
			//else 
				//User not found error
					
		}
		else if (source == createUser){
			//send new user name to server
			//receive user name from server
			//if user name passes
				//check password for illegal symbols eg ','
				//if password okay
					//send password to server
					//go to next page(main menu)
				//else
					//update error message("Invalid character in password. Try again.")
			//else
				//update error message ("User name is in use. Try again.")
		}
	}
	
	public JButton getLogin(){
		return logIn;
	}
	
	public JButton getCreateUser(){
		return createUser;
	}
	
	public String getUsernameText(){
		return usernameIn.getText();
	}
	
	public String getPassword(){
		return passwordIn.getText();
	}

}
