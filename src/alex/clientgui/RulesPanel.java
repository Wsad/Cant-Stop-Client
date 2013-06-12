package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;


public class RulesPanel extends JFrame implements ActionListener {
	private Container contentPane;
	private JPanel mainPanel;
	private JButton mainMenu;
	private ClientConnection connection;
	



	public RulesPanel(Container contentPaneIn, ClientConnection connectionIn){
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		connection = connectionIn;
		JPanel border = new JPanel();
		border.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Rules."));

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));

		JTextPane rules = new JTextPane();
		rules.setContentType("text/html");
		rules.setEditable(false);
		rules.setText("<html>GameRules.txt<br>newline</html>");
		
		mainMenu = new JButton("Return to main");
		mainMenu.setVisible(true);
		mainMenu.setMaximumSize(new Dimension(200,50));
		mainMenu.setMinimumSize(new Dimension(200,50));
		mainMenu.setPreferredSize(new Dimension(200,50));
		mainMenu.setAlignmentX(Container.CENTER_ALIGNMENT);
		mainMenu.addActionListener(this);

		innerPanel.add(rules);
		innerPanel.add(mainMenu);
		innerPanel.setPreferredSize(new Dimension(400,400));
		innerPanel.setMaximumSize(new Dimension(400,400));
		innerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

		mainPanel.add(innerPanel);
		mainPanel.setPreferredSize(new Dimension(450,450));

		mainPanel.setMaximumSize(new Dimension(450,450));

		border.add(mainPanel);

		contentPane.add(border);
		contentPane.revalidate();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mainMenu){
			MainMenuFrame m = new MainMenuFrame(contentPane,connection);
		}
			
			
		}
	
	}

