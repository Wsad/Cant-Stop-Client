package alex.clientgui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HighScoreFrame extends JFrame implements ActionListener {

	private JButton toMain;
	private Container contentPane;
	
	public HighScoreFrame(Container contentPaneIn){
		contentPane = contentPaneIn;
		contentPane.removeAll();
		toMain = new JButton("Back to Main Menu");
		contentPane.add(toMain);
		toMain.addActionListener(this);
		contentPane.validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		new StartPanel(contentPane);


	}

}
