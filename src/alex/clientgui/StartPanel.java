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
	private static int invalidAttempts=0;
	private final ClientConnection connection;
	
	public StartPanel(Container contentPaneIn, ClientConnection connectionIn){
		connection = connectionIn;
		
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
		
		/* Create RollOver Effect!!
		*
		*
		ImageIcon errorIcon = (ImageIcon) UIManager.getIcon("OptionPane.errorIcon");
		Icon warnIcon =  UIManager.getIcon("OptionPane.warningIcon");
		Icon splat = UIManager.getIcon("OptionPane.informationIcon");
		createUser.setBorderPainted(false);
		createUser.setBorder(null);
		createUser.setFocusable(false);
		createUser.setMargin(new Insets(0, 0, 0, 0));
		createUser.setContentAreaFilled(false);
		createUser.setIcon(errorIcon);
		createUser.setRolloverIcon(splat);
		createUser.setPressedIcon(warnIcon);
		createUser.setDisabledIcon(warnIcon);
		createUser.setRolloverEnabled(true);*/
		
		
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
		
		if ((source == logIn) && (invalidAttempts < 3)){
			String userName = usernameIn.getText();
			if (userName.contains(",")){
				error.setText("Username contains the illegal character ','");
			}else{
				connection.print("R," + userName);//send returning user name string to server
				String response = connection.read();
				if (response.equals("ack")){	//if server recognizes user name
					String password = passwordIn.getText();
					System.out.println(password);
					connection.print(password);	//send password to server
					response = connection.read();
					if (response.equals("ack")){//if valid password, go to next screen.
						BoardFrame mmf = new BoardFrame(contentPane, connection);
					}else{
						error.setText(response.substring(4));
						invalidAttempts++;
					}
				} else{
					System.out.println(response);
					error.setText(response.substring(4));
				}
			}
		}
		else if (source == createUser){
			String userName = usernameIn.getText();
			if (userName.contains(",")){
				error.setText("Username contains the illegal character ','");
			}else{
				connection.print("N," + userName);
				String response = connection.read();
				if (response.equals("ack")){
					String password = passwordIn.getText();
					connection.print(password);
					response = connection.read();
					if (response.equals("ack")){
						error.setText("New Account Created");
						MainMenuFrame mmf = new MainMenuFrame(contentPane, connection);
					}else{
						error.setText(response.substring(4));
						invalidAttempts++;
					}
				} else{
					error.setText(response.substring(4));
				}
			}
		}
		else if (((source == logIn) || (source == createUser)) && (invalidAttempts >= 3)){
			error.setText("Too many invalid log in attempts: closing connection");
			connection.close();
			//this.setVisible(false);
			//this.dispose();
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
