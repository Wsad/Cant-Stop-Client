package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
//import java.awt.Image;
import javax.swing.*;

public class BoardFrame extends JFrame implements ActionListener, MouseMotionListener {
	private JLayeredPane boardPane;
	private JPanel col;
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	
	public BoardFrame(Container contentPaneIn){
		contentPane = contentPaneIn;
		contentPane.removeAll();
		contentPane.revalidate();
		
		Image scaled = backgroundIcon.getImage().getScaledInstance(691, 600,Image.SCALE_DEFAULT);
		backgroundIcon = new ImageIcon(scaled);
		JLabel background = new JLabel(backgroundIcon);
		
		background.setBounds(10, 10, 800, 600);
		boardPane = new JLayeredPane();
		boardPane.setPreferredSize(new Dimension(800, 600));
		
		boardPane.add(background,new Integer(1));
		
		/*/Example column
		col = new JPanel();
		col.setBounds(150, 430, 45, 125);
		col.setBackground(Color.white);
		col.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		col.addMouseMotionListener(this);*/
		
		for (int i=0;i<11;i++){
			JPanel temp = new JPanel();
			temp.setBackground(Color.white);
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.addMouseMotionListener(this);
			temp.setBounds(151+i*(47), 430, 35, 125);
			boardPane.add(temp, new Integer(2));
		}
		
		//boardPane.add(col,new Integer(2));
		contentPane.add(boardPane);
		
	}
	
	 public void mouseMoved(MouseEvent e) {
	       Object source = e.getSource();
	       
	}
	
	public void mouseDragged(MouseEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
