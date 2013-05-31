package alex.clientgui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {

	public Client(){
		Container contentPane = this.getContentPane();
		StartPanel sp = new StartPanel();
		sp.setBorder(BorderFactory.createEmptyBorder(90,90,90,90));
		//sp.setPreferredSize(new Dimension(100,100));
		//sp.setMaximumSize(new Dimension(100,100));
		contentPane.add(sp);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new Client();
		frame.setSize(550,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
