package alex.clientgui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientFrame extends JFrame {
		Container contentPane;
		StartPanel sp;
		JButton logIn;
		JButton createUser;
		CardLayout cl;
		
	public ClientFrame(){
		contentPane = this.getContentPane();
		sp = new StartPanel(contentPane);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new ClientFrame();
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
