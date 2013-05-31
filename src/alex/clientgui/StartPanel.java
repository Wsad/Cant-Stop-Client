package alex.clientgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class StartPanel extends JPanel implements ActionListener {
	private JButton createUser;
	private JButton logIn;
	private JLabel error;
	private JTextField usernameIn;
	private JTextField passwordIn;
	
	public StartPanel(){
		this.setLayout(new FlowLayout());
		JPanel container = new JPanel(); //holds all components
		JPanel UI = new JPanel();
		UI.setLayout(new BoxLayout(UI,BoxLayout.PAGE_AXIS));
		//UI.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel inputbox = new JPanel();
		//inputbox.setLayout(new BoxLayout(inputbox, BoxLayout.PAGE_AXIS));
		
		
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
		
		//passBox.setLayout(new GridLayout(1,2));
		passBox.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		//inputbox.setLayout(new GridLayout(1,2));
		
		passBox.add(password);
		passBox.add(passwordIn);
		
		//inputbox.add(Box.createRigidArea(new Dimension(0,5)));
		//passBox.add(Box.createRigidArea(new Dimension(0,5)));
		
		inputbox.add(username);
		//inputbox.add()
		inputbox.add(usernameIn);
		
		buttonPanel.add(createUser);
		buttonPanel.add(logIn);
		
		UI.add(inputbox);
		UI.add(passBox);
		UI.add(buttonPanel);
		/*UI.add(passwordIn);
		UI.add(createUser);
		UI.add(logIn);*/
		UI.setBorder(BorderFactory.createEmptyBorder(15,15,15,25));
		
		UI.setPreferredSize(new Dimension(300,160));
		UI.setMaximumSize(new Dimension (300,160));
		
		container.add(UI);
		container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Welcome!", TitledBorder.TOP, TitledBorder.CENTER));
		
		add(container);
				
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
