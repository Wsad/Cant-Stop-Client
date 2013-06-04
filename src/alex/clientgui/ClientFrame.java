package alex.clientgui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientFrame extends JFrame {
		private Container contentPane;
		private StartPanel sp;
		private JButton logIn;
		private JButton createUser;
		//private ClientConnection connection; //commented out for testing.
		//private final int port;
		//private final String host;
		
	public ClientFrame(String hostIn, int portIn){
		//host = hostIn;
		//port = portIn;
		//connection = new ClientConnection(host, port);
		contentPane = this.getContentPane();
		sp = new StartPanel(contentPane);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new ClientFrame(args[0], Integer.parseInt(args[1]));
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
