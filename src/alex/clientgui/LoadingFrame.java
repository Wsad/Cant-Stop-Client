package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.Timer;

public class LoadingFrame extends JFrame implements ActionListener {
	private Container contentPane;
	private JPanel mainPanel;
	private final ClientConnection connection;
	private JButton ready;
	private int playerNum;
	private Timer timer;
	private Timer animation;
	
	
	public LoadingFrame(Container contentPaneIn, ClientConnection connectionIn){
		connection = connectionIn;
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		JPanel border = new JPanel();
		border.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connecting to other players."));
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));
		
		JTextField rules = new JTextField();
		rules.setEditable(false);
		rules.setText("Here Are the Rules: etc....");//need a way to make text wrap. or could use a picture;
		ready = new JButton("Click Here When Ready");
		ready.setVisible(false);
		ready.setMaximumSize(new Dimension(200,50));
		ready.setMinimumSize(new Dimension(200,50));
		ready.setPreferredSize(new Dimension(200,50));
		ready.setAlignmentX(Container.CENTER_ALIGNMENT);
		ready.addActionListener(this);
		
		innerPanel.add(rules);
		innerPanel.add(ready);
		innerPanel.setPreferredSize(new Dimension(400,400));
		innerPanel.setMaximumSize(new Dimension(400,400));
		innerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		
		mainPanel.add(innerPanel);
		mainPanel.setPreferredSize(new Dimension(450,450));
		
		mainPanel.setMaximumSize(new Dimension(450,450));
		
		border.add(mainPanel);
		timer= new Timer(500,this);
		timer.start();
		
		animation = new Timer(20,this);
		animation.start();
		
		contentPane.add(border);
		contentPane.revalidate();
	}
	
	public void ready(){
		String response = connection.read();
		playerNum = Integer.parseInt(response);
		ready.setVisible(true);
		//playerNum = playerNumIn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == timer){
			timer.stop();
			ready();
		}
		if (source == animation){
			//rotate image here
		}
		else if (source == ready){
			BoardFrame bf = new BoardFrame(contentPane,connection,playerNum);
		}

	}

}
