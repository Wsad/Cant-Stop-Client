package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
//import java.awt.Image;
import javax.swing.*;
import java.util.HashSet;
import java.util.Scanner;

public class BoardFrame extends JFrame implements ActionListener, MouseListener {
	private JLayeredPane boardPane;
	private Column[] columns;
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	private ClientConnection connection;
	private JPanel buttonPanel;
	private JButton roll;
	private JButton stop;
	private int numPieces;
	private String move;
	private int moveNum;
	private int d1,d2,d3,d4;
	private JLabel dice1, dice2, dice3, dice4;
	private int moveChoice;
	
	public BoardFrame(Container contentPaneIn, ClientConnection connectionIn, int playerNum){
		connection = connectionIn;
		
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		contentPane.setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		
		JPanel centrePanel = new JPanel();
		
		JPanel diceAndButton = new JPanel();
		diceAndButton.setLayout(new BoxLayout(diceAndButton,BoxLayout.PAGE_AXIS));
		diceAndButton.setPreferredSize(new Dimension(200,360));
		diceAndButton.setMaximumSize(new Dimension(200,360));
		diceAndButton.setMinimumSize(new Dimension(200,360));
		diceAndButton.setBorder(BorderFactory.createEmptyBorder(35, 10, 35, 5));
		
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new GridLayout(2,2));
		dice1 = new JLabel(new ImageIcon("1.jpg"));
		dice2 = new JLabel(new ImageIcon("1.jpg"));
		dice3 = new JLabel(new ImageIcon("1.jpg"));
		dice4 = new JLabel(new ImageIcon("1.jpg"));
		dice1.setBorder(BorderFactory.createRaisedBevelBorder());
		dice2.setBorder(BorderFactory.createRaisedBevelBorder());
		dice3.setBorder(BorderFactory.createRaisedBevelBorder());
		dice4.setBorder(BorderFactory.createRaisedBevelBorder());
		dicePanel.add(dice1);
		dicePanel.add(dice2);
		dicePanel.add(dice3);
		dicePanel.add(dice4);
		dicePanel.setPreferredSize(new Dimension(120,120));
		dicePanel.setMaximumSize(new Dimension(120,120));
		dicePanel.setMinimumSize(new Dimension(120,120));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
		roll = new JButton("Roll");
		stop = new JButton("Stop");
		roll.addActionListener(this);
		stop.addActionListener(this);
		//stop.disable();
		//buttonPanel.add(Box.createRigidArea(new Dimension(15,7)));
		buttonPanel.add(roll);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,7)));
		buttonPanel.add(stop);
		
		diceAndButton.add(Box.createRigidArea(new Dimension(100,100)));
		diceAndButton.add(dicePanel);
		diceAndButton.add(Box.createRigidArea(new Dimension(50,50)));
		diceAndButton.add(buttonPanel);
		buttonPanel.setAlignmentX(Container.CENTER_ALIGNMENT);
		leftPanel.add(diceAndButton);
		leftPanel.setAlignmentY(Container.CENTER_ALIGNMENT);
		
		
		if (playerNum == 2){
			buttonPanel.setVisible(false);
			/*roll.setOpaque(false);
			stop.setOpaque(false);//turn off buttons when it's opponent's turn.*/
		}
		numPieces = 0;
		
		
		columns = new Column[12];
		for (int i=2;i<=12;i++){
			columns[i-2] = new Column(i);
		}
		
		Image scaled = backgroundIcon.getImage().getScaledInstance(691, 600,Image.SCALE_DEFAULT);
		backgroundIcon = new ImageIcon(scaled);
		JLabel background = new JLabel(backgroundIcon);
		
		background.setBounds(10, 10, 700, 600);
		boardPane = new JLayeredPane();
		boardPane.setPreferredSize(new Dimension(800, 600));
		
		boardPane.add(background,new Integer(1));
		
		for (int i=0;i<6;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(101+i*(47), 430-(i*50), 35, 120+(i*50));
			boardPane.add(temp, new Integer(3));
			
		}
		
		for (int i=6;i<11;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(101+i*(47), 180+((i-5)*50), 35, 370-((i-5)*50));
			boardPane.add(temp, new Integer(3));
		}
		
		centrePanel.add(boardPane);
		
		contentPane.add(leftPanel, BorderLayout.WEST);
		contentPane.add(centrePanel,BorderLayout.CENTER);
		contentPane.revalidate();
		
	}
	
	 public void mouseEntered(MouseEvent e) {
		 Object source = e.getSource();
	     if ((source.getClass()== Column.class) && (moveNum == 0)){
	    	 //get column selected
	    	 //remove highlight from columns that do not contain the selected column
	    	 Column col = (Column)source;
	       	 int colNum = col.getColNum();
	       	 clearHighlight();
	       	 int[] available = availableColumns(d1,d2,d3,d4,colNum);
	       	 for (int a : available){
	       		 highlightPosition(a);
	       	 }
	       	 contentPane.revalidate();
	     }else if ((source.getClass()== Column.class) && (moveNum == 1)){
	    	 Column col = (Column)source;
	    	 int colNum = col.getColNum();
	    	 clearHighlight();
	    	 int[] available = availableColumns(d1, d2, d3, d4, moveChoice, colNum);
	    	 for (int a : available) {
	    		 highlightPosition(a);
	    	 }
	    	 contentPane.revalidate();
	     }
	 }
	 
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
	     if ((source.getClass() == Column.class) && (moveNum == 0)) {
			clearHighlight();
			int[] available = availableColumns(d1, d2, d3, d4);
			for (int a : available) {
				highlightPosition(a);
			}
			contentPane.revalidate();
		}else if ((source.getClass()== Column.class) && (moveNum == 1)){
			clearHighlight();
			int[] available = availableColumns(d1, d2, d3, d4,moveChoice);
			for (int a : available) {
				highlightPosition(a);
			}
			contentPane.revalidate();
	    }
	}
	 
	public void backLight(int xPos, int yPos, int xSize, int ySize){
		JPanel light = new JPanel();
		light.setBounds(xPos -10, yPos - 10, xSize + 20, ySize + 20);
		light.setBackground(Color.yellow);
		boardPane.add(light, new Integer(2));
		
	}
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == roll){
			move = "";
			moveNum = 0;
			connection.print("roll");
			buttonPanel.setVisible(false);
			String roll = connection.read();
			Scanner rollScan = new Scanner(roll).useDelimiter(",");
			d1 = rollScan.nextInt();
			d2 = rollScan.nextInt();
			d3 = rollScan.nextInt();
			d4 = rollScan.nextInt();
			dice1.setIcon(new ImageIcon(d1+".jpg"));
			dice2.setIcon(new ImageIcon(d2+".jpg"));
			dice3.setIcon(new ImageIcon(d3+".jpg"));
			dice4.setIcon(new ImageIcon(d4+".jpg"));
			int[] availableCol = availableColumns(d1,d2,d3,d4);//add combinations of roll values into list (ignore duplicates) (maybe using sets) 1,2,3,4
			for (int colNum : availableCol)
				highlightPosition(colNum);
			//create list of all possible
			//if above list.length > the list length ignoring doubles
				//find duplicate columns
				//highlight doubles if exist.
		}
		else if (source == stop){
			connection.print("stop");
			String response = connection.read();
			//if response == "ack"
				//remove roll and stop buttons
				//while opponent turn{
					//response = read
					//if response == go
						//start turn again
					//else if response == you lost
						//show lost screen
					//else
						//get dice roll
						//change dice
						//get player move
						//show move on board
				//}
		}
		else if (source.getClass() == PositionButton.class){
			PositionButton button = (PositionButton)source;
			int height = button.getColHeight();
			int col = button.getColumn();
			//if col.hasTemp(user)s
				//gamepiece.move(col,height)
			//else
				//new gamepiece(col,height)
				//numPieces++;
			Point pt = button.getLocation();
			
			int x = pt.x;
			int y = pt.y;
			
			//JLabel piece = new JLabel("1");
			//piece.setBackground(new Color(0,0,0,0));
			//piece.setBounds(y,boardPane.getHeight()- x, 50,50);
			//boardPane.add(piece, new Integer(5));
			if (moveNum == 0){
				move = col+",";
				moveNum++;
				clearHighlight();
				moveChoice = col;
				int [] available = availableColumns(d1,d2,d3,d4,col);
				for (int column : available){
					highlightPosition(column);
				}
				//remove highlight from already chosen position.
				columns[col-2].getPosButton(height).removeActionListener(this);
				columns[col-2].getPosButton(height).setBackground(Color.white);
				columns[col-2].getPosButton(height).setRolloverEnabled(false);
				columns[col-2].removeMouseListener(this);
				
				contentPane.revalidate();
			}else if (moveNum == 1){
				clearHighlight();
				contentPane.revalidate();
				move +=""+col;
				connection.print(move);
				String response = connection.read();
				if(!response.equals("ack"))
					System.out.println("error sending move choice");
				moveNum++;
					
			}
			//clear choices
			//show button panel
			contentPane.revalidate();
			
		}
	}
	
	public void clearHighlight(){
		for (int i=2;i<=12;i++){
			Column col = columns[i-2];
			try {
				col.removeMouseListener(this);
			}
			catch(NullPointerException e){	
			}
			col.removeHighlight();
			PositionButton[] pos = col.getPositionArr();
			for (int j = 0; j<pos.length; j++){
				try{
					pos[j].removeActionListener(this);
				}
				catch (NullPointerException e){
					
				}
			}
			
		}
	}
	
	public void highlightPosition(int colNum){
		Column col = columns[colNum-2];
		if (!col.getConquered()){
			if (numPieces < 3){
				col.addMouseListener(this);
				if (col.hasTemp(Column.USER)){
					//highlight position above temp
					col.highLight(col.getTemp(Column.USER));
					col.getPosButton(col.getTemp(Column.USER)-1).addActionListener(this);
				}else if (col.hasFinal(Column.USER)){
					col.highLight(col.getFinal(Column.USER));
					col.getPosButton(col.getFinal(Column.USER)-1).addActionListener(this);
				}else
					col.highLight(1);
					col.getPosButton(1).addActionListener(this);
			}else if (col.hasTemp(Column.USER)){
				col.addMouseListener(this);
				col.highLight(col.getTemp(Column.USER));
				col.getPosButton(col.getTemp(Column.USER)-1).addActionListener(this);
			}
		}
	}
	
	/* Will return the available columns with the specified dice roll.
	 * Will take into account the remaining moves for a if a column is selected
	 * @param d1 Value of first die 
	 * @param d2 Value of second die
	 * @param d3 Value of third die
	 * @param d4 Value of fourth die
	 * @param selected The selected column
	 * @return An array of integers representing the remaining moves if the column is selected*/
	public int[] availableColumns(int d1, int d2, int d3, int d4, int selected){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		if ((split1 == selected) || (halfsplit1 == selected)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}
	
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		if ((split2 == selected) || (halfsplit2 == selected)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		if ((split3 == selected) || (halfsplit3 == selected)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}
		
		int[] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++]=col;
		}
		return output;
	}
	
	public int[] availableColumns(int d1, int d2, int d3, int d4){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int [] split = {d1 + d4, d2 + d3, d1 + d3, d2 + d4, d1 + d2, d3 + d4};
		for (int i : split)
			colSet.add(i);
		int [] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++] = col;
		}
		return output;
	}
	
	public int[] availableColumns(int d1, int d2, int d3, int d4, int selected, int next){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		if ((split1 == selected) && (halfsplit1 == next)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}else if ((split1 == next) && (halfsplit1 == selected)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}
	
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		if ((split2 == selected) && (halfsplit2 == next)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}else if ((split2 == next) && (halfsplit2 == selected)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		if ((split3 == selected) && (halfsplit3 == next)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}else if ((split3 == next) && (halfsplit3 == selected)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}
		int[] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++]=col;
		}
		return output;
	}
	
}
