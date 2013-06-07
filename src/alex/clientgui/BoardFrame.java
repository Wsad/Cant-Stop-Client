package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
//import java.awt.Image;
import javax.swing.*;

public class BoardFrame extends JFrame implements ActionListener, MouseMotionListener {
	private JLayeredPane boardPane;
	private Column[] columns;
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	private ClientConnection connection;
	
	public BoardFrame(Container contentPaneIn, ClientConnection connectionIn){
		
		
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		
		columns = new Column[12];
		for (int i=2;i<=12;i++){
			columns[i-2] = new Column(i);
		}
		
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
		
		for (int i=0;i<6;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(151+i*(47), 430-(i*50), 35, 120+(i*50));
			boardPane.add(temp, new Integer(2));
		}
		
		for (int i=6;i<11;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(151+i*(47), 180+((i-5)*50), 35, 370-((i-5)*50));
			boardPane.add(temp, new Integer(2));
		}
		
		//boardPane.add(col,new Integer(2));
		contentPane.add(boardPane);
		contentPane.revalidate();
		
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
