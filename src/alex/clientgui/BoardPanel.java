package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel implements ActionListener {
	private JLayeredPane boardPane;
	
	public BoardPanel(){
		JLabel background = new JLabel(new ImageIcon("BoardBackground.png"));
		//JPanel background = new JPanel();
		//background.setBackground(Color.black);
		background.setBounds(10, 10, 877, 761);
		
		boardPane = new JLayeredPane();
		boardPane.setPreferredSize(new Dimension(877, 761));
		
		boardPane.add(background,new Integer(1));
		
		JPanel col = new JPanel();
		col.setBounds(15, 15, 100, 100);
		col.setBackground(Color.cyan);
		
		boardPane.add(col,new Integer(2));
		add(boardPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setSize(877,761);
		JComponent board = new BoardPanel();
        board.setOpaque(true); //content panes must be opaque
        frame.setContentPane(board);
 
        frame.pack();
		frame.setVisible(true);
	}

}
