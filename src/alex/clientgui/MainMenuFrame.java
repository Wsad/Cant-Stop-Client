package alex.clientgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenuFrame extends JFrame implements ActionListener {
	private JButton newGame;
	private JButton highScore;
	private JButton logOut;
	private Container contentPane;
	private final ClientConnection connection;
	
	public MainMenuFrame(Container contentPaneIn, ClientConnection connectionIn){
		connection = connectionIn;
		
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		
		JPanel buttonPanel = new JPanel();
		JPanel buttonBoard = new JPanel();
		JPanel background = new JPanel();
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.PAGE_AXIS));
		//buttonBoard.setLayout(new BoxLayout(buttonBoard,BoxLayout.PAGE_AXIS));
		
		newGame = new JButton("New Game");
		highScore = new JButton("High Score");
		logOut = new JButton("Log Out");
		
		newGame.addActionListener(this);
		highScore.addActionListener(this);
		logOut.addActionListener(this);
		
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		highScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		logOut.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		newGame.setPreferredSize(new Dimension(200,50));
		newGame.setMaximumSize(new Dimension(200,50));
		newGame.setMinimumSize(new Dimension(200,50));
		
		highScore.setPreferredSize(new Dimension(200,50));
		highScore.setMaximumSize(new Dimension(200,50));
		highScore.setMinimumSize(new Dimension(200,50));
		
		logOut.setPreferredSize(new Dimension(200,50));
		logOut.setMaximumSize(new Dimension(200,50));
		logOut.setMinimumSize(new Dimension(200,50));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(0,7)));
		buttonPanel.add(newGame);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,10)));
		buttonPanel.add(highScore);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,10)));
		buttonPanel.add(logOut);
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		buttonPanel.setPreferredSize(new Dimension(220,190));
		buttonPanel.setMaximumSize(new Dimension(220,190));
		
		buttonBoard.add(buttonPanel);
		buttonBoard.setBorder(BorderFactory.createEtchedBorder());
		buttonBoard.setPreferredSize(new Dimension(250,220));
		buttonBoard.setMaximumSize(new Dimension(230,220));
		
		background.add(buttonBoard);
		background.setBorder(BorderFactory.createEmptyBorder(150,50,100,50));
		background.setPreferredSize(new Dimension(800,600));
		background.setMaximumSize(new Dimension(800,600));
		
		contentPane.add(background);
		contentPane.revalidate();		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == newGame){
			//LoadingFrame lf = new LoadingFrame(contentPane);
		}else if(source == highScore){
			HighScoreFrame hsf = new HighScoreFrame(contentPane, connection);
		}else if(source == logOut){
			//close connection
			StartPanel sp = new StartPanel(contentPane, connection);
		}

	}

}
